import data, math, time, bin from "std";
import assert, expect, expect_error, to, describe, it, subject, before, after from "std/spec";

import flow, find from "tweakstreet/spec";

library spec {

  spec:

    describe("guid step", [

      # find all *.spec files
      # run them and and expect them to return true

      find("glob:*.spec.[cd]fl", ".", (files) ->

        describe("spec flows", data.map(files, (file) ->

          describe(file, [

            subject(effect: flow(file)),

            it("returns true", (result) ->
              expect(result, to.be_true())
            )

          ])
        ))
      ),

    ]);
}