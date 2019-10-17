package com.twineworks.tweakstreet.features.demo.steps.guid;

import com.twineworks.tweakflow.lang.types.Types;
import com.twineworks.tweakflow.lang.values.Value;
import com.twineworks.tweakflow.lang.values.ValueProvider;
import com.twineworks.tweakflow.lang.values.Values;
import com.twineworks.tweakstreet.api.steps.BasePassThroughStep;
import com.twineworks.tweakstreet.api.steps.PassThroughStep;
import com.twineworks.tweakstreet.api.desc.results.ResultDesc;

import java.util.*;

public final class GuidStep extends BasePassThroughStep implements PassThroughStep {

  private UUID uuid;

  @Override
  public List<ResultDesc> getDeclaredResults() {
    // step provides a single result
    return Collections.singletonList(new ResultDesc(Types.STRING, "uuid"));
  }

  @Override
  public Map<String, ValueProvider> getResultProviders(Set<String> names) {

    // create the return value
    HashMap<String, ValueProvider> providers = new HashMap<>();

    // a value provider is a one-method interface that can
    // be implemented as an inline closure
    // it supplies the value of a result to the engine
    providers.put("uuid", () -> Values.make(uuid.toString()));

    return providers;
  }

  @Override
  public void processRow(Value row) {
    uuid = UUID.randomUUID();
  }

}
