  [![Maintainability](https://api.codeclimate.com/v1/badges/a5e16222c01e400e39a7/maintainability)](https://codeclimate.com/github/EmbeddedMontiArc/MontiMath/maintainability)
  [![Build Status](https://travis-ci.org/EmbeddedMontiArc/MontiMath.svg?branch=master)](https://travis-ci.org/EmbeddedMontiArc/MontiMath)
  [![Build Status](https://circleci.com/gh/EmbeddedMontiArc/MontiMath/tree/master.svg?style=shield&circle-token=:circle-token)](https://circleci.com/gh/EmbeddedMontiArc/MontiMath/tree/master)
[![Coverage Status](https://coveralls.io/repos/github/EmbeddedMontiArc/MontiMath/badge.svg?branch=master)](https://coveralls.io/github/EmbeddedMontiArc/MontiMath?branch=master)

# MontiMath

* please use new version of grammer: https://github.com/EmbeddedMontiArc/MontiMath/tree/nina-improve_grammar/src/main/grammars/de/monticore/lang
    * the new grammer uses a new MontiCore version, you need to refactor some methods (for questions please ask Nina in a personal meeting)
* and extend the grammar by creating a new `.mc4` file which extends the `Math.mc4` file, so that your extension can be used optional and is not default enabled in `EMAM`
    * for information about it see the MontiCore book of the SLE lecture
    * http://monticore.de/MontiCore_Reference-Manual.2017.pdf (chapter 7.4)
