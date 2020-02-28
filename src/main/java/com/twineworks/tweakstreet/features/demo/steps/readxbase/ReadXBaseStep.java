package com.twineworks.tweakstreet.features.demo.steps.readxbase;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.twineworks.tweakflow.lang.types.Types;
import com.twineworks.tweakflow.lang.values.*;
import com.twineworks.tweakstreet.api.fs.FileSystemConnection;
import com.twineworks.tweakstreet.api.steps.BaseReadFieldsStep;
import com.twineworks.tweakstreet.api.steps.ReadFieldsStep;
import com.twineworks.tweakstreet.api.desc.mappings.Mapping;
import com.twineworks.tweakstreet.api.desc.results.ResultDesc;
import com.twineworks.tweakstreet.api.desc.settings.SettingDesc;

import java.io.IOException;
import java.util.*;

public final class ReadXBaseStep extends BaseReadFieldsStep implements ReadFieldsStep {

  private FileSystemConnection fs;

  private DBFReader dbf;

  private ReadXBaseStepSettings s = new ReadXBaseStepSettings();
  private List<DBFField> fields;
  private HashMap<String, Integer> fieldsByName;
  private Value fieldsValue = Values.NIL;

  private int[] mappedIndexes;
  private Value[] rowValues;

  static final List<SettingDesc> declaredSettings = Arrays.asList(
    new SettingDesc("fs"),
    new SettingDesc("file"),
    new SettingDesc("charset")
  );

  @Override
  public List<SettingDesc> getDeclaredSettings() {
    return declaredSettings;
  }

  @Override
  public List<ResultDesc> getDeclaredResults() {
    return Arrays.asList(
      new ResultDesc(Types.LIST, "fields"),
      new ResultDesc(Types.LIST, "values"),
      new ResultDesc(Types.DICT, "record")
    );
  }

  @Override
  public Map<String, ValueProvider> getResultProviders(Set<String> names) {
    HashMap<String, ValueProvider> providers = new HashMap<>();

    if (names.contains("fields")) {
      providers.put("fields", () -> fieldsValue);
    }

    if (names.contains("values")) {
      providers.put("values", () -> Values.makeList(rowValues));
    }

    if (names.contains("record")) {
      providers.put("record", this::getCurrentRecord);
    }
    return providers;
  }

  private Value getCurrentRecord() {
    TransientDictValue t = new TransientDictValue();
    for (int i = 0; i < fields.size(); i++) {
      DBFField field = fields.get(i);
      t.put(field.getName(), rowValues[i]);
    }
    return Values.make(t.persistent());
  }

  @Override
  public void open(List<Mapping> mappings) {
    try {

      boolean fsHasChanged = s.fsSetting.hasChanged();
      boolean fileHasChanged = s.fileSetting.hasChanged();

      s.update();

      if (fsHasChanged) {
        closeFile();
        closeFs();
        fs = context.fileSystemConnection(s.fs);
      }

      if (fsHasChanged || fileHasChanged) {
        closeFile();
        String filePath = fs.relNorm(context.getFlowInfo().getFlowPath(), s.file);
        int bufferSize = 64 * 1024;

        dbf = new DBFReader(fs.newInputStream(filePath, bufferSize), s.charset, Boolean.FALSE);
        int fieldCount = dbf.getFieldCount();

        fields = new ArrayList<>();
        fieldsByName = new HashMap<>();

        // header info

        ListValue fieldValues = new ListValue();
        for (int i=0;i<fieldCount;i++) {
          DBFField field = dbf.getField(i);
          fields.add(field);
          fieldsByName.put(field.getName(), fields.size()-1);
          fieldValues = fieldValues.append(Values.makeDict(
            "name", field.getName(),
            "type_code", field.getType().getCharCode()
          ));
        }

        fieldsValue = Values.make(fieldValues);

        // support named and positional mappings
        mappedIndexes = new int[mappings.size()];

        for (int i = 0; i < mappings.size(); i++) {
          Mapping m = mappings.get(i);
          if (m.mapping.isString()) {
            Integer idx = fieldsByName.get(m.mapping.string());
            mappedIndexes[i] = idx != null ? idx : -1;
          } else if (m.mapping.isLongNum()) {
            long fieldNum = m.mapping.longNum();
            if (fieldNum >= 0 && fieldNum < fields.size()) {
              mappedIndexes[i] = (int) fieldNum;
            } else {
              mappedIndexes[i] = -1;
            }
          } else if (m.mapping.isNil()) {
            mappedIndexes[i] = -1;
          } else {
            throw new RuntimeException("Mapping for field " + m.name + " is of invalid type: " + m.mapping.type().name() + ". Expected string key or long index.");
          }
        }
      }

    } catch (IOException e) {
      closeFile();
      closeFs();
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean readNextRow() {
    try {

      Object[] record = dbf.nextRecord();

      if (record == null) return false;

      int size = fields.size();
      if (rowValues == null || rowValues.length < size) {
        rowValues = new Value[size];
      }

      for (int i = 0; i < size; i++) {
        rowValues[i] = Values.make(record[i]);
      }

      return true;

    } catch (Exception e) {
      closeFile();
      closeFs();
      throw e;
    }


  }

  @Override
  public void getRowFields(Value[] fields) {

    for (int i = 0; i < mappedIndexes.length; i++) {
      int mappedIndex = mappedIndexes[i];

      // non-findables
      if (mappedIndex < 0 || mappedIndex >= rowValues.length){
        fields[i] = Values.NIL;
      }
      else{
        fields[i] = rowValues[mappedIndex];
      }

    }

  }

  @Override
  public void setSettingProviders(Map<String, ValueProvider> settings) {
    this.s.init(settings);
  }

  private void closeFile() {

    try {

      if (dbf != null) {
        dbf.close();
      }

    } finally {
      dbf = null;
    }

  }

  private void closeFs() {
    if (fs != null) {
      try {
        fs.close();
      } catch (IOException ignored) {
      } finally {
        fs = null;
      }
    }
  }

  @Override
  public void shutdown() {
    closeFile();
    closeFs();
    super.shutdown();
  }
}
