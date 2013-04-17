(function(window, angular, undefined) {
  "use strict";

  App.namespace("views.angular");
  App.views.angular.AngularExample = {
    init: function(data) {
      angular.module('AngularExample',[])
        .factory('settings', function() {
          return {
            thing : {
              x : data
            }
          };
        })
        .controller('FirstCtrl', function($scope, settings) {
          $scope.thing = settings.thing;
          $scope.name = "First Controller";
        });

    }
  };

  /*function(init) {
    var self = this;

    console.log("init: ", init);

    self.textInput = ko.observable("");

    self.submitForm = function() {
      var ret = { textInput: self.textInput() };
      // call the passed in save function with the form data as an argument.
      saveFunc(ret);
    };

    self.sendSuccess = sendSuccess;

    self.showWarning = function() {
      // sends a notice to the client
      $(document).trigger("add-alerts", {message: "<em>This is a warning!</em>", priority: "warning"});
    };
  };*/

})(this, angular);
