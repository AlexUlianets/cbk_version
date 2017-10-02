var app = angular.module('main', ['ui.router', 'oc.lazyLoad']);

// Enter page

app.controller('not-universal-daysCtrl',['$scope', '$http', '$state','$stateParams', function($scope, $http, $state, $stateParams) {

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
            console.log($scope.temperatureWeekly);
        })
    }

    $http.post('/get_weekly_weather_summary').then(function (response) {
        $scope.$parent.weekly_weather_summary = response.data;
    });

}]);