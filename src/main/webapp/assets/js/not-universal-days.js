var app = angular.module('main', ['ui.router', 'oc.lazyLoad']);

// Enter page

app.controller('not-universal-daysCtrl',['$scope', '$http', '$state','$stateParams', function($scope, $http, $state, $stateParams) {
    $scope.graph = $scope.$state.params.graph;

    $scope.getData = function () {
        var sendingTableRequest = {
            method: 'POST',
            url: '/get_not_universal_table_data',
            params: {
                numOfDays:$scope.$state.params.index
            },
            headers: {
                'Content-Type': 'application/json; charset=utf-8'
            }
        };

        $http(sendingTableRequest).success(function (data) {
            $scope.temperatureWeekly = data;

        })
    }

    if($scope.$state.params.page === 'five-days'){
        $scope.getDataGraph = function () {
            var sendingTableRequest = {
                method: 'GET',
                url: '/get_table_data_for_days',
                params: {
                    numOfHours:1,
                    numOfDays:5,
                    pastWeather:false
                },
                headers: {
                    'Content-Type': 'application/json; charset=utf-8'
                }
            }

            $http(sendingTableRequest).success(function (data) {
                console.log(1)
                readyGet(data, [], $scope.local.typeTemp, $scope.$state.params.page)
            })
        }
    } else if($scope.$state.params.page === 'ten-days') {
        $http.post('/get_detailed_forecast').then(function (response) {
            readyGet(response, [], $scope.local.typeTemp, $scope.$state.params.page)
        });
    }

    $http.post('/get_weekly_weather_summary').then(function (response) {
        $scope.$parent.weekly_weather_summary = response.data;
    });

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

}]);