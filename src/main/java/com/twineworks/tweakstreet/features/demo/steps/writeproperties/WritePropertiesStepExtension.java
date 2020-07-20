package com.twineworks.tweakstreet.features.demo.steps.writeproperties;

import com.twineworks.tweakstreet.api.desc.results.ResultDesc;
import com.twineworks.tweakstreet.api.desc.settings.SettingDesc;
import com.twineworks.tweakstreet.api.steps.PassThroughStep;
import com.twineworks.tweakstreet.api.steps.PassThroughStepExtension;
import org.pf4j.Extension;

import java.util.List;


@Extension
public final class WritePropertiesStepExtension implements PassThroughStepExtension {

  @Override
  public String getTypeId() {
    return this.getClass().getPackage().getName();
  }

  @Override
  public PassThroughStep newInstance() {
    return new WritePropertiesStep();
  }

  @Override
  public List<ResultDesc> getDeclaredResults() {
    return WritePropertiesStep.declaredResults;
  }

  @Override
  public List<SettingDesc> getDeclaredSettings() {
    return WritePropertiesStep.declaredSettings;
  }

  @Override
  public List<SettingDesc> getDeclaredStaticSettings() {
    return WritePropertiesStep.declaredStaticSettings;
  }


}
