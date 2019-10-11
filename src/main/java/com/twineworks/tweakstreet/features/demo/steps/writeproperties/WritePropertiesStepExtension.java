package com.twineworks.tweakstreet.features.demo.steps.writeproperties;

import com.twineworks.tweakstreet.api.steps.PassThroughStep;
import com.twineworks.tweakstreet.api.steps.PassThroughStepExtension;
import org.pf4j.Extension;


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


}
