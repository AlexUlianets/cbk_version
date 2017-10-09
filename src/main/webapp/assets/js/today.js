var app = angular.module('main', ['ui.router', 'oc.lazyLoad']);

// Enter page

  app.controller('todayCtrl', function($scope, $http) {

      $scope.graph = $scope.$state.params.graph;

      $http.post('/get_weekly_weather_summary').then(function (response) {
          $scope.$parent.weekly_weather_summary = response.data;
      });

      $http.post('/get_astronomy').then(function (response) {
          $scope.$parent.moon_phase_index = response.data['moon_phase_index'];
          $scope.$parent.moon_phase_name = response.data['moon_phase_name'];
          $scope.$parent.astronomy = response.data;
      });

      $http.post('/get_detailed_forecast_today').then(function (response) {
          $scope.$parent.detailedTemp = response;
          readyGet(response, [], $scope.local.typeTemp, 'today')
      });

      var sendingTableRequest = {
          method: 'GET',
          url: '/get_dynamic_table_data',
          params: {numOfHours:3, numOfDays:1, pastWeather:false},
          headers: {
              'Content-Type': 'application/json; charset=utf-8'
          }
      }

      $http(sendingTableRequest).success(function (data) {
          $scope.$parent.dynamicTableData = data;
          console.log($scope.$parent.dynamicTableData)
      })


      function setIdle(cb, seconds) {
          var timer;
          var interval = seconds * 1000;
          function refresh() {
              clearInterval(timer);
              timer = setTimeout(cb, interval);
          };
          $(document).on('keypress click', refresh);
          refresh();
      }

      setIdle(function() {
          location.href = location.href;
      }, 15 * 60);

  });
