var app = angular.module('main', ['ui.router', 'oc.lazyLoad']);

// Enter page

app.controller('past-weatherCtrl', function($scope, $http) {

    $scope.past = false;
    $scope.showWeatherForDate = function (date) {
        var currentDate = new Date(date);
        $scope.past = currentDate < new Date();
        $scope.date = date;
        $scope.sendRequest($scope.hrs, 1, $scope.past, $scope.date);
    };

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

       $scope.sendRequest($scope.hrs, 1, $scope.past, $scope.date);
    };

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
