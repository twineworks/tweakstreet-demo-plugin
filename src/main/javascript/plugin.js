const guid = require("./steps/guid");
const readxbase = require("./steps/readxbase");
const writeproperties = require("./steps/writeproperties");

const activate = api => {
  api.registerExtension(guid.extension);
  api.registerExtension(readxbase.extension);
  api.registerExtension(writeproperties.extension);
};

module.exports.activate = activate;
