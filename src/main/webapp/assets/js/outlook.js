var app = angular.module('main', ['ui.router', 'oc.lazyLoad']);

  app.controller('outlookCtrl', ['$scope', '$http', function($scope, $http) {


      function getTempWeekly() {
          $http.post('/get_weekly_weather').then(function (response) {
              $scope.temperatureWeekly = response;
          }, function (error) {
              throw error;
          })
      }

      getTempWeekly();
  }]);
