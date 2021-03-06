const ID = "com.twineworks.tweakstreet.features.demo.steps.writeproperties";

const extension = {
  id: ID,
  type: "step",
  stepType: "passthrough",
  name: "Write Properties",
  category: "Files",
  description: "Writes a dict value to a properties file",
  icon: "steps/writeproperties/icon.svg",
  docs: "steps/writeproperties/docs.html",
  colorCode: "io_write",
  settings: [
    {
      id: "fs",
      type: "storage",
      label: "Filesystem",
      hoverHtml: "<p>The filesystem to use</p><p><i>Evaluated for each input row</i></p>"
    },
    {
      id: "file",
      type: "file",
      label: "File",
      hoverHtml: "<p>The output file to write to</p><p><i>Evaluated for each input row</i></p>",
      value: "./output.properties"

    },
    {
      id: "charset",
      type: "charset",
      label: "Charset",
      hoverHtml: "<p>Charset encoding of output file</p><p><i>Evaluated for each input row</i></p>",
      value: "UTF-8"
    },
    {
      id: "comment",
      type: "text",
      label: "Comment",
      hoverHtml: "<p>This text appears commented out in the header of the file</p><p><i>Evaluated for each input row</i></p>",
      value: {
        text: "Properties file generated by Tweakstreet",
        size: {height: 100}
      }
    },
    {
      id: "data",
      type: "dict",
      label: "Data",
      hoverHtml: "<p>Key value pairs to write into the properties file</p><p><i>Evaluated for each input row</i></p>",
      value: [
        ["key_1", "value_1"],
        ["key_2", "value_2"]
      ]
    },
  ]
};

module.exports.extension = extension;