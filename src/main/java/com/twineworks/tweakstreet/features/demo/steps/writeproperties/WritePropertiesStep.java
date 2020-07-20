package com.twineworks.tweakstreet.features.demo.steps.writeproperties;

import com.twineworks.tweakflow.lang.types.Types;
import com.twineworks.tweakflow.lang.values.DictValue;
import com.twineworks.tweakflow.lang.values.Value;
import com.twineworks.tweakflow.lang.values.ValueInspector;
import com.twineworks.tweakflow.lang.values.ValueProvider;
import com.twineworks.tweakstreet.api.desc.results.ResultDesc;
import com.twineworks.tweakstreet.api.fs.FileSystemConnection;
import com.twineworks.tweakstreet.api.fs.WriteOpenExistingOption;
import com.twineworks.tweakstreet.api.steps.BasePassThroughStep;
import com.twineworks.tweakstreet.api.steps.PassThroughStep;
import com.twineworks.tweakstreet.api.desc.settings.SettingDesc;

import java.io.BufferedWriter;
import java.util.*;

public final class WritePropertiesStep extends BasePassThroughStep implements PassThroughStep {

  static final List<SettingDesc> declaredSettings = Arrays.asList(
    new SettingDesc("fs"),
    new SettingDesc("file"),
    new SettingDesc("charset"),
    new SettingDesc("comment"),
    new SettingDesc("data")
  );

  static final List<SettingDesc> declaredStaticSettings = Collections.emptyList();

  static final List<ResultDesc> declaredResults = Collections.emptyList();

  private WritePropertiesStepSettings s = new WritePropertiesStepSettings();

  @Override
  public void setSettingProviders(Map<String, ValueProvider> settings) {
    this.s.init(settings);
  }

  private String asPropertyValue(Value v) {
    if (v.isNil()) return "";
    if (v.isString()) return v.string();
    if (v.type().canAttemptCastTo(Types.STRING)) {
      return v.castTo(Types.STRING).string();
    } else {
      return ValueInspector.inspect(v);
    }
  }

  @Override
  public void processRow(Value row) {

    // let settings helper fetch potentially new setting values
    s.update();

    // fill a Properties instance with provided dict
    Properties p = new Properties();

    DictValue data = s.data;
    if (data != null) {
      for (String key : data.keys()) {
        p.put(key, asPropertyValue(data.get(key)));
      }
    }

    // write out the file
    try (FileSystemConnection fs = context.fileSystemConnection(s.fs)) {

      // if a relative file is given, it is resolved relative to the flow path and and normalized
      String fileName = fs.relNorm(context.getFlowInfo().getFlowPath().toString(), s.file);

      // ensure we can create the file
      fs.mkParentFor(fileName);

      try(BufferedWriter writer = fs.newBufferedWriter(fileName, s.charset, 64*1024, WriteOpenExistingOption.TRUNCATE)){
        p.store(writer, s.comment);
      }

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
