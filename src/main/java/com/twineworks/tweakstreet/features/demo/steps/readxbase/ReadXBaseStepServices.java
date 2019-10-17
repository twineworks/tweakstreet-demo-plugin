package com.twineworks.tweakstreet.features.demo.steps.readxbase;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.twineworks.tweakflow.lang.types.Type;
import com.twineworks.tweakflow.lang.types.Types;
import com.twineworks.tweakflow.lang.values.ValueProvider;
import com.twineworks.tweakstreet.api.desc.mappings.StringMappingDesc;
import com.twineworks.tweakstreet.api.desc.readfields.ReadFieldDesc;
import com.twineworks.tweakstreet.api.desc.settings.SettingDesc;
import com.twineworks.tweakstreet.api.fs.FileSystemConnection;
import com.twineworks.tweakstreet.api.services.BaseStepService;
import com.twineworks.tweakstreet.api.services.FetchReadFieldsService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReadXBaseStepServices extends BaseStepService implements FetchReadFieldsService {

  private ReadXBaseStepSettings s;

  // See reference: https://github.com/albfernandez/javadbf
  private Type typeForField(DBFField f){
    switch (f.getType().getCharCode()){
      case 'C':
      case 'V':
        return Types.STRING;
      case 'N':
      case 'F':
      case 'Y':
        return Types.DECIMAL;
      case 'L':
        return Types.BOOLEAN;
      case 'D':
      case 'T':
      case '@':
        return Types.DATETIME;
      case 'l':
      case '+':
        return Types.LONG;
      case 'W':
      case 'G':
      case 'P':
      case 'Q':
        return Types.BINARY;
      case 'O':
        return Types.DOUBLE;
      case 'B':
      case 'M':
        return Types.ANY; // mixed possibilities
    }

    return Types.ANY;

  }

  @Override
  public List<ReadFieldDesc> fetchReadFields() {

    s.update();

    try(FileSystemConnection fs = context.fileSystemConnection(s.fs);
        InputStream is = fs.newInputStream(
          fs.relNorm(context.getFlowInfo().getFlowPath(),s.file), 8192);
        DBFReader dbf = new DBFReader(is, s.charset, Boolean.FALSE)){


      int fieldCount = dbf.getFieldCount();
      ArrayList<ReadFieldDesc> ret = new ArrayList<>(fieldCount);

      for (int i=0;i<fieldCount;i++) {
        DBFField field = dbf.getField(i);
        String name = field.getName();
        Type type = typeForField(field);
        ReadFieldDesc desc = new ReadFieldDesc(type, name, new StringMappingDesc(name), null);
        ret.add(desc);
      }

      return ret;

    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public List<SettingDesc> getDeclaredSettings() {
    return ReadXBaseStep.declaredSettings;
  }

  @Override
  public void setSettingProviders(Map<String, ValueProvider> settings) {
    this.s = new ReadXBaseStepSettings();
    this.s.init(settings);
  }


}
