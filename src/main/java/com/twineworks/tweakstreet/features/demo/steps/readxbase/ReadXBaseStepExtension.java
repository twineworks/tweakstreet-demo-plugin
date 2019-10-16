package com.twineworks.tweakstreet.features.demo.steps.readxbase;

import com.twineworks.tweakstreet.api.steps.ReadFieldsStep;
import com.twineworks.tweakstreet.api.steps.ReadFieldsStepExtension;
import org.pf4j.Extension;


@Extension
public final class ReadXBaseStepExtension implements ReadFieldsStepExtension {

  @Override
  public String getTypeId() {
    return this.getClass().getPackage().getName();
  }

  @Override
  public ReadFieldsStep newInstance() {
    return new ReadXBaseStep();
  }

}
