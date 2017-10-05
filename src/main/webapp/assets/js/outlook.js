var app = angular.module('main', ['ui.router', 'oc.lazyLoad']);

  app.controller('outlookCtrl', ['$scope', '$http', function($scope, $http) {

      $scope.graph = $scope.$state.params.graph;

      $http.post('/get_weekly_weather').then(function (response) {
         $scope.$parent.temperatureWeekly = response;
      });

      $http.post('/get_detailed_forecast').then(function (response) {
          $scope.$parent.detailedTemp = response;
          $http.post('/get_year_summary').then(function (responseYear) {
              $scope.$parent.get_year_summary = responseYear;
              readyGet(response, responseYear, $scope.local.typeTemp, 'outlook')
          });
      });

      $http.post('/get_astronomy').then(function (response) {
          $scope.$parent.moon_phase_index = response.data['moon_phase_index'];
          $scope.$parent.moon_phase_name = response.data['moon_phase_name'];
          $scope.$parent.astronomy = response.data;
      });

      $http.post('/get_coordinates').then(function (response) {
          $scope.$parent.coordinates = response.data;
      });

      $http.post('/get_weekly_ultraviolet_index').then(function (response) {
          var colorsUV = ['greenLow', 'yellowAverage', 'orangeHigh', 'redHigh', 'redExtreme'];
          $scope.$parent.ultraviolet = response.data;
          $scope.$parent.colorsUV = response.data.map (function (value) {
              value = parseInt(value.index);
              if(value<=2){
                  return colorsUV[0];
              }else if (value<=5 && value>=3) {
                  return colorsUV[1];
              }else if (value<=7 && value>=6) {
                  return colorsUV[2];
              }else if (value<=10 && value>=8) {
                  return colorsUV[3];
              }else {
                  return colorsUV[4];
              }
          });
      });

      $http.post('/get_five_years_average').then(function (response) {
          $scope.$parent.five_years_average = response.data;
      });

      $http.post('/get_weekly_weather_summary').then(function (response) {
          $scope.$parent.weekly_weather_summary = response.data;
      });



  }]);
