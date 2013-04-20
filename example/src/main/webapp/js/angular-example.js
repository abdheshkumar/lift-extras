(function(window, angular, undefined) {
  "use strict";

  angular.module('App.views.angular.AngularExample', ['server'])
    .controller('PageCtrl', function($scope, ServerFuncs) {
      $scope.textInput = "";
      $scope.sendSuccess = ServerFuncs.sendSuccess;

      $scope.saveForm = function() {
        if (this.textInput) {
          ServerFuncs.saveForm({ textInput: this.textInput });
        }
      };

      $scope.showWarning = function() {
        $(document).trigger("add-alerts", {
          message: "<em>This is a warning!</em>",
          priority: "warning"
        });
      };

      $scope.$on('reset-form', function(e) {
        $scope.$apply(function() {
          $scope.textInput = "";
        });
      });

    });
})(this, angular);
