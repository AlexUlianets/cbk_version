var app = angular.module('main', ['ui.router', 'oc.lazyLoad']);

app.controller('front-pageCtrl', ['$scope', '$http','$rootScope', function($scope, $http, $rootScope) {


    $http.get("get_top_holidays_destinations").success(function (data) {
        $scope.top_holidays_destinations = data;
    })

    $http.get("get_holidays_weather").success(function (data) {
        $scope.holidays_weather = data;
    })

    $http.get("get_country_weather").success(function (data) {
        $scope.country_weather = data;
    })

    var sendingTableRequest = {
        method: 'GET',
        url: '/get_table_data_for_days',
        params: {
            numOfHours:1,
            numOfDays:3,
            pastWeather:false
        },
        headers: {
            'Content-Type': 'application/json; charset=utf-8'
        }
    }
    $http(sendingTableRequest).success(function (data) {
        $scope.header_tabs = data;
    })

    $http.get("get_recent_cities_tabs").success(function (data) {
        $scope.recent_tabs = data;
    })

    $scope.select_holiday_destination = function (index) {
        var destination = $(".destination"+index).text();

        $.ajax({
            method: "POST",
            url: "/find_occurences/"+destination
        }).done(function( msg ) {
            if(msg.length>0){
                msg = msg[0]
                window.location.pathname="/en/weather/outlook/"+msg.name+"_"+msg.countryCode
            }else{
                alert("Requested city is not found");
            }
        });
    }
}]);