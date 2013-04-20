(function(window, angular, undefined) {
  "use strict";

  /*angular.module('SrvrStuff', [])
    .factory('serverFuncs', function() {
      return {
        sendSuccess: function() { console.log('sendSuccess'); },
        saveForm: function() { console.log('saveForm'); }
      };
    });

  angular.module('App.views.angular.AngularExample',['SrvrStuff'])
    .controller('PageCtrl', function($scope, serverFuncs) {
      $scope.textInput = "";
      $scope.sendSuccess = serverFuncs.sendSuccess;
      $scope.saveForm = function() {
        if (this.textInput) {
          serverFuncs.saveForm({ textInput: this.textInput });
        }
      };
      $scope.showWarning = function() {
        $(document).trigger("add-alerts", {
          message: "<em>This is a warning!</em>",
          priority: "warning"
        });
      };
    });*/



  /*App.namespace("views.angular");
  App.views.angular.AngularExample = {
    init: function(sendSuccess, saveForm) {
      angular.module('App.views.angular.AngularExample',[])
        .factory('serverFuncs', function() {
          return {
            sendSuccess: sendSuccess,
            saveForm: saveForm
          };
        })
        .controller('PageCtrl', function($scope, serverFuncs) {
          $scope.textInput = "";
          $scope.sendSuccess = serverFuncs.sendSuccess;
          $scope.saveForm = function() {
            if (this.textInput) {
              serverFuncs.saveForm({ textInput: this.textInput });
            }
          };
          $scope.showWarning = function() {
            $(document).trigger("add-alerts", {message: "<em>This is a warning!</em>", priority: "warning"});
          };
        });
    }
  };*/
})(this, angular);
