var app = angular.module('main', ['ui.router', 'oc.lazyLoad']);

  app.controller('outlookCtrl', ['$scope', '$http', function($scope, $http) {

      $http.post('/get_weekly_weather').then(function (response) {
         $scope.$parent.temperatureWeekly = response;
      });

      $http.post('/get_detailed_forecast').then(function (response) {
          $scope.$parent.detailedTemp = response;
          readyGet(response, $scope.local.typeTemp)
      });

      $http.post('/get_astronomy').then(function (response) {
          $scope.$parent.coordinates = response.data['coordinates'];
      });

  }]);
