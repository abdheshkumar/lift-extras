var app = angular.module('AngDemo', ['AngDemoServer']);

app.config(function ($routeProvider) {
  "use strict";

  $routeProvider
    .when('/form', {
      controller: 'FormController',
      templateUrl: '/angdemo/partials/form'
    })
    .when('/success', {
      controller: 'SuccessController',
      templateUrl: '/angdemo/partials/success'
    })
    .when('/warning', {
      controller: 'WarningController',
      templateUrl: '/angdemo/partials/warning'
    })
    .otherwise({ redirectTo: '/form' });
});

app.controller('FormController', function($scope, ServerParams, ServerFuncs) {
  "use strict";

  $scope.textInput = ServerParams.x;

  $scope.saveForm = function() {
    if (this.textInput) {
      ServerFuncs.saveForm({ textInput: this.textInput });
    }
  };

  $scope.$on('reset-form', function() {
    $scope.$apply(function() {
      $scope.textInput = "";
    });
  });

});

app.controller('SuccessController', function($scope, ServerFuncs) {
  "use strict";

  $scope.sendSuccess = ServerFuncs.sendSuccess;
});

app.controller('WarningController', function($scope) {
  "use strict";

  $scope.showWarning = function() {
    $(document).trigger("add-alerts", {
      message: "<em>This is a warning!</em>",
      priority: "warning"
    });
  };

});

app.controller('NavbarController', function ($scope, $location) {
  "use strict";

  $scope.getClass = function (path) {
    if ($location.path().substr(0, path.length) === path) {
      return true;
    }
    else {
      return false;
    }
  };
});
