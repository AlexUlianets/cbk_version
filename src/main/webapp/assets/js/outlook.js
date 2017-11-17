var app = angular.module('main', ['ui.router', 'oc.lazyLoad']);

  app.controller('outlookCtrl', ['$scope', '$http', function($scope, $http) {
      $scope.climate=[];
      $scope.graph = $scope.$state.params.graph;
      $scope.day=$scope.$state.params.day;
      $scope.graphTitle=$scope.$state.params.graphTitle;
      var slav = ["ua", "by", "ru"];
      if(slav.includes(location.pathname.split("/")[1])){
          $scope.outTable = "slavTable";
      }else{
          $scope.outTable = "enTable"
      }

      $scope.dayTrans = ["Day", "der Tag"];
      $scope.nightTrans = ["Night", "die Nacht"];
      $scope.dayTransSlav = ["День", "Дзень"];
      $scope.nightTransSlav = ["Ніч", "Ночь", "Ноч"];

      $http.post('/get_weekly_weather/'+location.pathname.split("/")[1]).then(function (response) {
          console.log(response);
          console.log($scope.outTable);
         $scope.$parent.temperatureWeekly = response;
      });

      $http.post('/get_detailed_forecast').then(function (response) {
          $scope.$parent.detailedTemp = response;
          $http.post('/get_year_summary').then(function (responseYear) {
              $scope.$parent.get_year_summary = responseYear;
              $scope.getActiveClimate(-1);

              readyGet(response, responseYear, $scope.local.typeTemp, 'outlook', $scope.graphTitle, $scope.local.timeRange)
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


      $scope.activeTabMobile = function(){
           $('.climate-dropdown-bot').slideToggle();
      };
      $scope.getActiveClimate = function(active){
          var inpNum= -1;

          if (active === -1) {
              $scope.get_year_summary.data.forEach(function (element, index) {
                if(element.active===true){
                    inpNum = index
                }
              })
          } else {
              inpNum = active
          }
          $scope.get_year_summary.data.forEach(function (element, index) {
              if(index === inpNum) {
                  $scope.climate.month = element.fullMonthName;
                  $scope.climate.tempMaxC = element.maxtempC;
                  $scope.climate.tempMaxF = element.maxtempF;
                  $scope.climate.tempMinC = element.mintempC;
                  $scope.climate.tempMinF = element.mintempF;
                  $scope.climate.precipMm = element.precipMM;
                  $scope.climate.precipIn = element.precipInch;
              }
              $('.climate-dropdown-bot').fadeOut('slow');
          })
      }

  }]);
