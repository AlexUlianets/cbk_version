var app = angular.module('main', ['ui.router', 'oc.lazyLoad']);

// Enter page

app.controller('tomorrowCtrl', function($scope, $http) {


    var sendingTableRequest = {
        method: 'GET',
        url: '/get_dynamic_table_data',
        params: {numOfHours:3, numOfDays:2, pastWeather:false},
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

})