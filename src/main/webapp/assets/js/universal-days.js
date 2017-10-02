var app = angular.module('main', ['ui.router', 'oc.lazyLoad']);

// Enter page

app.controller('three-daysCtrl',['$scope', '$http', '$state','$stateParams', function($scope, $http, $state, $stateParams) {
    $scope.$state = $state;
    $scope.$stateParams = $stateParams;
    $scope.selectedTab = 1;
    $scope.tabClass = $scope.$state.params.tabClass;

    $scope.getData = function () {
        var sendingTableRequest = {
            method: 'GET',
            url: '/get_table_data_for_days',
            params: {
                numOfHours:1,
                numOfDays:$scope.$state.params.index,
                pastWeather:false
            },
            headers: {
                'Content-Type': 'application/json; charset=utf-8'
            }
        }

        $http(sendingTableRequest).success(function (data) {
            $scope.dynamicTableData = data;
            console.log($scope.dynamicTableData);
        })
    }

    $scope.selectTab = function (index) {
        activateTab(index);
        $scope.selectedTab = index;
        $scope.getData();
    }

    $http.post('/get_weekly_weather_summary').then(function (response) {
        $scope.$parent.weekly_weather_summary = response.data;
    });

}])