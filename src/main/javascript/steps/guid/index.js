const ID = "com.twineworks.tweakstreet.features.demo.steps.guid";

const extension = {
  id: ID,
  type: "step",
  stepType: "passthrough",
  name: "GUID",
  description: "Generates a type 4 UUID",
  icon: "steps/guid/icon.svg",
  docs: "steps/guid/docs.html",
  colorCode: "branch",
  settings: [
  ],
  results: [
    { type: "string", name: "uuid", label: "the generated UUID" }
  ],
  outputFields: {
    initial: [
      {
        type: "string",
        name: "uuid",
        value: { type: "result", name: "uuid" }
      }
    ]
  },
  layout: {
    children: [
      { id: "resultVariables" },
      { id: "outputFields" }
    ]
  }
};

module.exports.extension = extension;