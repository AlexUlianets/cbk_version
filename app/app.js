var app = angular.module('main', ['ui.router', 'oc.lazyLoad', 'ngCookies']);

// MAIN

  // app.controller('AppCtrl', function($ocLazyLoad, $injector, $mdMedia, $scope, $mdSidenav, $mdBottomSheet, $mdToast, $timeout, $mdDialog, $mdMedia, $rootScope, $window, $http, $cookies, $cookieStore) {
  //         $rootScope.example = "xyu!";
  // });
app.run( ['$rootScope', '$state', '$stateParams', function ($rootScope,   $state,   $stateParams) {
    $rootScope.$state = $state;
    $rootScope.$stateParams = $stateParams; 
    $rootScope.example = "xyu!";
}]);
  app.config(['$ocLazyLoadProvider', '$stateProvider', '$urlRouterProvider', function($ocLazyLoadProvider, $stateProvider, $urlRouterProvider) {
      // $urlRouterProvider.otherwise("outlook");

      $ocLazyLoadProvider.config({
          'debug': false, // For debugging 'false/false'
          'events': false, // For Event 'true/false'
          'modules': [{ // Set modules initially
              name: 'outlook', // State1 module
              files: ['assets/js/outlook.js']
          }, {
              name: 'today', // State2 module
              files: ['assets/js/today.js']
          }]
      });
      //Config/states of UI Router
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
                      return $ocLazyLoad.load('outlook'); // Resolve promise and load before view 
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
                      return $ocLazyLoad.load('today'); // Resolve promise and load before view 
                  }]
              }
          });
  }]);
