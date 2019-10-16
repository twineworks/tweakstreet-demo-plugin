package com.twineworks.tweakstreet.features.demo.steps.readxbase;

import com.twineworks.tweakflow.lang.values.Value;
import com.twineworks.tweakflow.lang.values.ValueProvider;
import com.twineworks.tweakstreet.api.util.settings.CharsetSetting;
import com.twineworks.tweakstreet.api.util.settings.StringSetting;
import com.twineworks.tweakstreet.api.util.settings.ValueSetting;

import java.nio.charset.Charset;
import java.util.Map;

final class ReadXBaseStepSettings {

  ValueSetting fsSetting;
  StringSetting fileSetting;

  Value fs;
  String file;
  CharsetSetting charsetSetting;
  Charset charset;

  void init(Map<String, ValueProvider> settings){
    fsSetting = new ValueSetting.Builder("fs")
      .from(settings)
      .build();

    fileSetting = new StringSetting.Builder("file")
      .from(settings)
      .nullable(false)
      .build();

    charsetSetting = new CharsetSetting.Builder("charset")
      .from(settings)
      .nullable()
      .build();

  }

  void update(){
    fs = fsSetting.get();
    file = fileSetting.get();
    charset = charsetSetting.get();
  }

}
