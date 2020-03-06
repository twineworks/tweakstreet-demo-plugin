const ID = "com.twineworks.tweakstreet.features.demo.steps.readxbase";

const extension = {
  id: ID,
  type: "step",
  stepType: "readFields",
  name: "Read XBase file",
  category: "Files",
  description: "reads records from an XBase file (.dbf) ",
  icon: "steps/readxbase/icon.svg",
  colorCode: "io_read",
  settings: [
    {
      id: "fs",
      type: "storage",
      label: "Filesystem",
    },
    {
      id: "file",
      type: "file",
      label: "File",
      value: "./input.dbf"
    },
    {
      id: "charset",
      type: "charset",
      label: "Charset",
      hoverHtml: "<p>Charset encoding of character data</p><p><i>Evaluated for each input row</i></p>",
      value: "UTF-8"
    },
  ],
  readFields: {
    actions: [
      {
        label: "Fetch fields",
        hoverHtml: "<p>Fetch fields from dbf file</p>",
        type: "fetch_read_fields",
        source: "service",
        service: ID + ".services.fetchreadfields"
      }
    ]
  },
  results: [
    { type: "list", name: "fields", label: "fields stored in the file" },
    { type: "list", name: "values", label: "values of current record" },
    { type: "dict", name: "record", label: "current record as dict" }
  ],
};

module.exports.extension = extension;