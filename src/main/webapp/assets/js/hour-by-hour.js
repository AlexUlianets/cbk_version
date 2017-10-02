var app = angular.module('main', ['ui.router', 'oc.lazyLoad']);

// Enter page

app.controller('hour-by-hourCtrl',['$scope', '$http', '$state','$stateParams', function($scope, $http, $state, $stateParams) {


    $scope.$state = $state;
    $scope.$stateParams = $stateParams;
    $scope.selectedTab = 1;
    $scope.tabClass = $scope.$state.params.tabClass;
    $scope.hrs = 3;


    $scope.refreshTableWithHours = function (hours) {
        $scope.hrs = hours;
        if($scope.hrs === 1){
            document.getElementById('hr-selector3').className="";
            document.getElementById('hr-selector1').className="active";
            document.getElementById('h-tab-2').className = "tab-hour-content";
            document.getElementById('h-tab-1').className = document.getElementById('h-tab-1').className+ " active";

        }else {
            document.getElementById('h-tab-1').className = "tab-hour-content";
            document.getElementById('h-tab-2').className = document.getElementById('h-tab-2').className+ " active";
            document.getElementById('hr-selector1').className="";
            document.getElementById('hr-selector3').className="active";
        }

        $scope.getData();
    };
    $scope.getData = function () {

        var sendingTableRequest = {
            method: 'GET',
            url: '/get_dynamic_table_data',
            params: {
                numOfHours:$scope.hrs,
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
        console.log(index);
        activateTab(index);
        $scope.selectedTab = index;
        $scope.getData();
    }

    $scope.sendRequest = function (numOfHrs, days, pastWeath, date) {
        var sendingTableRequest = {
            method: 'GET',
            url: '/get_dynamic_table_data',
            params: {
                numOfHours:numOfHrs,
                numOfDays:days,
                pastWeather:pastWeath,
                date:date},
            headers: {
                'Content-Type': 'application/json; charset=utf-8'
            }
        }
        $scope.date = date;
        $scope.hrs = numOfHrs;

        $http(sendingTableRequest).success(function (data) {
            $scope.dynamicTableData = data;
        })

    };

    $http.post('/get_weekly_weather_summary').then(function (response) {
        $scope.$parent.weekly_weather_summary = response.data;
    });

}]);