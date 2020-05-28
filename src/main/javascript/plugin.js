const writeproperties = require("./steps/writeproperties");

const activate = api => {
  api.registerExtension(writeproperties.extension);
};

module.exports.activate = activate;
