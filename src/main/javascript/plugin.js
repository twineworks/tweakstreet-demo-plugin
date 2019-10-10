const guid = require("./steps/guid");

const activate = api => {
  api.registerExtension(guid.extension);
};

module.exports.activate = activate;
