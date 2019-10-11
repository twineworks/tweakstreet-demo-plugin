package com.twineworks.tweakstreet.features.demo.steps.writeproperties;

import com.twineworks.tweakflow.lang.values.DictValue;
import com.twineworks.tweakflow.lang.values.Value;
import com.twineworks.tweakflow.lang.values.ValueProvider;
import com.twineworks.tweakstreet.api.util.settings.CharsetSetting;
import com.twineworks.tweakstreet.api.util.settings.DictSetting;
import com.twineworks.tweakstreet.api.util.settings.StringSetting;
import com.twineworks.tweakstreet.api.util.settings.ValueSetting;

import java.nio.charset.Charset;
import java.util.Map;

public class WritePropertiesStepSettings {

  ValueSetting fsSetting;
  StringSetting fileSetting;
  CharsetSetting charsetSetting;
  StringSetting commentSetting;
  DictSetting dataSetting;

  Value fs;
  String file;
  Charset charset;
  String comment;
  DictValue data;

  void init(Map<String, ValueProvider> settings){
    fsSetting = new ValueSetting.Builder("fs").from(settings).build();
    fileSetting = new StringSetting.Builder("file").from(settings).nullable(false).build();
    charsetSetting = new CharsetSetting.Builder("charset").from(settings).nullable(false).build();
    commentSetting = new StringSetting.Builder("comment").from(settings).nullable().build();
    dataSetting = new DictSetting.Builder("data").from(settings).nullable().build();
  }

  void update(){
    fs = fsSetting.get();
    file = fileSetting.get();
    data = dataSetting.get();
    charset = charsetSetting.get();
    comment = commentSetting.get();
  }

}
