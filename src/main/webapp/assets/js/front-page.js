var app = angular.module('main', ['ui.router', 'oc.lazyLoad']);

app.controller('front-pageCtrl', ['$scope', '$http', function($scope, $http) {

    $scope.get_api_weather = function(){
        $.ajax({
            method: "POST",
            url: "/get_api_weather"
        }).done(function( msg ) {
            $scope.temperature = msg;
            $scope.get_recent_cities_tabs_func();
            loadScript();
            console.log(msg)
        })
    }
}]);