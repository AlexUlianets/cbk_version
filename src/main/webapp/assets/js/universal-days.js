var app = angular.module('main', ['ui.router', 'oc.lazyLoad']);

// Enter page

app.controller('three-daysCtrl',['$scope', '$http', '$state','$stateParams', function($scope, $http, $state, $stateParams) {
    $scope.$state = $state;
    $scope.$stateParams = $stateParams;
    $scope.selectedTab = 1;
    $scope.graph = $scope.$state.params.graph;
    $scope.tabClass = $scope.$state.params.tabClass;
    $scope.page=$scope.$state.params.page;

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
            console.log($scope.dynamicTableData)
            if($scope.$state.params.page === 'three-days') {
                console.log($scope.dynamicTableData);
                readyGet($scope.dynamicTableData, [], $scope.local.typeTemp, $scope.$state.params.page)
            }
        })

        if($scope.page=='fourteen-days'){
        setTimeout(function () {

            $(function () {
                if ($('.tb-slider').length) {
                    if ($(window).width() >= '881') {
                        $('.tb-slider').slick({
                            infinite: false,
                            //speed: 300,
                            slide: 'li',
                            slidesToShow: 7,
                            slidesToScroll: 7,
                            prevArrow: '<button type="button" class="slick-prev slick-arrow"><</button>',
                            nextArrow: '<button type="button" class="slick-next slick-arrow">></button>'
                        });
                        console.log('too')
                    }
                }
            });
            $(window).resize()

        }, 1000);

        $(window).resize()
// setTimeout(function () {
//     $(window).resize();
// },3000);

        }

    };


    if($scope.$state.params.page === 'seven-days' || $scope.$state.params.page === 'fourteen-days') {
        $http.post('/get_detailed_forecast').then(function (response) {
            readyGet(response, [], $scope.local.typeTemp, $scope.$state.params.page)
        });
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