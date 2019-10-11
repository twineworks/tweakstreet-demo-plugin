const guid = require("./steps/guid");
const writeproperties = require("./steps/writeproperties");

const activate = api => {
  api.registerExtension(guid.extension);
  api.registerExtension(writeproperties.extension);
};

module.exports.activate = activate;
