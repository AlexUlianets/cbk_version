var app = angular.module('main', ['ui.router', 'oc.lazyLoad', 'ngCookies']);

    app.run( ['$rootScope', '$state', '$stateParams', function ($rootScope,   $state,   $stateParams) {
        $rootScope.$state = $state;
        $rootScope.$stateParams = $stateParams;
        $rootScope.example = "";
        $rootScope.local = {};
        $rootScope.local.typeTemp = 'C';

        $.ajax({
            method: "POST",
            url: "/get_api_weather"
        }).done(function( msg ) {
            $rootScope.temperature = msg;
        })
    }]);
    app.config(['$ocLazyLoadProvider', '$stateProvider', '$urlRouterProvider', function($ocLazyLoadProvider, $stateProvider, $urlRouterProvider) {

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
                  url: "/outlook",
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
                  url: "/",
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
  }]);
