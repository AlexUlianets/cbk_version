var app = angular.module('main', ['ui.router', 'oc.lazyLoad', 'ngCookies']);

    app.run( ['$rootScope', '$state', '$stateParams', '$http', function ($rootScope,   $state,   $stateParams, $http) {
        $rootScope.$state = $state;
        $rootScope.$stateParams = $stateParams;
        $rootScope.example = "";
        $rootScope.local = {};
        $rootScope.local.typeTemp = 'C';
        $rootScope.searchInput = '';
        $rootScope.searchList = [];

        $rootScope.get_recent_cities_tabs_func = function(){
            $.ajax({
                method: "POST",
                url: "/get_recent_cities_tabs"
            }).done(function( msg ) {
                $rootScope.get_recent_cities_tabs = msg;
                console.log(msg);
                $rootScope.$apply;
            });
        }

        $rootScope.get_api_weather = function(){
            $.ajax({
                method: "POST",
                url: "/get_api_weather"
            }).done(function( msg ) {
                $rootScope.temperature = msg;
                $rootScope.get_recent_cities_tabs_func();
            })
        }
        $rootScope.get_api_weather();

        $rootScope.searchHint = function(){
                console.log($rootScope.searchInput);
                if($rootScope.searchInput.length > 1){
                     $.ajax({
                        method: "POST",
                        url: "/find_occurences/"+$rootScope.searchInput
                     }).done(function( msg ) {
                        console.log(msg);
                        $rootScope.searchList = msg;
                        $rootScope.$apply;
                     })
                }else{
                      $rootScope.searchList = $rootScope.get_recent_cities_tabs;
                      console.log($rootScope.searchList);
                      $rootScope.$apply;

                }
        }
        $rootScope.selectCity = function(e){
            console.log(e);
            $.ajax({
            method: "POST",
                url: "/select_city/"+e
            }).done(function( msg ) {
                console.log(msg)
                $state.reload();
                $rootScope.get_api_weather();
            })
        }
        $rootScope.updateTemp = function(){
            readyGet($rootScope.$$childHead.detailedTemp, $rootScope.$$childHead.get_year_summary, $rootScope.local.typeTemp)
        }
    }]);
    app.config(['$ocLazyLoadProvider', '$stateProvider', '$urlRouterProvider', '$locationProvider', function($ocLazyLoadProvider, $stateProvider, $urlRouterProvider, $locationProvider) {

          $ocLazyLoadProvider.config({
              'debug': false,
              'events': false,
              'modules': [{
                  name: 'outlook',
                  files: ['assets/js/outlook.js']
              }, {
                  name: 'today',
                  files: ['assets/js/today.js']
              }]
          });

          $stateProvider
              .state('outlook', {
                  url: "/",
                  views: {
                      "": {
                          templateUrl: "templates/html/outlook.html"
                      }
                  },
                  resolve: {
                      loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                          return $ocLazyLoad.load('outlook');
                      }]
                  }
              })
              .state('today', {
                  url: "/today",
                  views: {
                      "": {
                          templateUrl: "templates/html/today.html"
                      }
                  },
                  resolve: {
                      loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                          return $ocLazyLoad.load('today');
                      }]
                  }
              });

         $locationProvider.html5Mode(true)
  }]);
