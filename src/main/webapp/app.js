var app = angular.module('main', ['ui.router', 'oc.lazyLoad', 'ngCookies']);

    app.run( ['$rootScope', '$state', '$stateParams', '$http', '$cookies', function ($rootScope,   $state,   $stateParams, $http, $cookies) {
        $rootScope.$state = $state;
        $rootScope.$stateParams = $stateParams;
        $rootScope.example = "";
        $rootScope.local = {};

        if($cookies.get('temp_val')===undefined){
            $cookies.put('temp_val', 'C');
            $rootScope.local.typeTemp = 'C';
        }else {
            $rootScope.local.typeTemp = $cookies.get('temp_val').toString();

        }
        $rootScope.searchInput = '';
        $rootScope.searchList = [];
        $rootScope.result = 0;

        $rootScope.get_recent_cities_tabs_func = function(){
            $.ajax({
                  method: "POST",
                url: "/get_recent_cities_tabs"
            }).done(function( msg ) {
                $rootScope.$apply(function(){
                    $rootScope.get_recent_cities_tabs = msg;
                });
                var ln = $('.favorite-location .container')[0]['children'].length;
                if ($(window).width() < 500) {
                    $('#top-page').animate({height: (40+((ln) * 10) - 10)+'vh'});
                }
            });
            $('.tb-contant').removeClass('inner-html')

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
            $('.search-dropdown ul').css({'display': 'none'})
            $('.search-dropdown img').css({'display': 'block'})
            $('.search-dropdown').css({'display': 'block'})
                if($rootScope.searchInput.length > 1){

                     $.ajax({
                        method: "POST",
                        url: "/find_occurences/"+$rootScope.searchInput
                     }).done(function( msg ) {
                         $rootScope.$apply(function(){
                             $rootScope.searchList = msg;
                             $rootScope.result = 1;
                         });
                         $('.search-dropdown img').css({'display': 'none'})
                         $('.search-dropdown ul').css({'display': 'block'})


                     })
                }else{
                      $rootScope.searchList = $rootScope.get_recent_cities_tabs;
                      $rootScope.result = 0;
                        $('.search-dropdown img').css({'display': 'none'})
                        $('.search-dropdown ul').css({'display': 'block'})
                      if($rootScope.get_recent_cities_tabs===undefined){
                          $('.search-dropdown').css({'display': 'none'})

                      }
                }
        }
        $rootScope.selectCity = function(e){
            $('.search-dropdown').css({'display': 'none'})
            $rootScope.searchInput = '';
            $('.ht-search-input input').val('')

            $.ajax({
            method: "POST",
                url: "/select_city/"+e
            }).done(function( msg ) {
                $state.reload();
                $rootScope.get_api_weather();
            })

        };
        $rootScope.deleteCity = function(e, index){
            $.ajax({
                method: "POST",
                url: "/deleteCity/"+e
            }).done(function( msg ) {
                var $this = $(this);
                var $item = $('.weather-block-favorite')[0] ? $('.weather-block-favorite') : $('.weather-block-width');
                $this.parent().slideUp();
                var $menuIndex = index;
                var block_weater = $('.weather-block-favorite')[0] ? $('.weather-block-favorite') : $('.weather-block-width');
                if (!$('.weather-block-favorite')[0]) {
                    console.log('true');
                }
                $('.w' + $menuIndex).remove();
                $('.w' + $menuIndex + '_li').remove();

                var ln = $('.favorite-location .container')[0]['children'].length;
                if ($(window).width() < 500) {
                        $('#top-page').animate({height: (40+(ln * 10) - 10)+'vh'});
                }
            })
        };
        $rootScope.updateTemp = function(val){
            if(val===$cookies.get('temp_val')){

            }
            else {
                if ($cookies.get('temp_val') === 'C') {
                    $cookies.put('temp_val', 'F');

                } else {
                    $cookies.put('temp_val', 'C');

                }
                document.location.reload(true);
            }
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
              },{
                  name: 'tomorrow',
                  files: ['assets/js/tomorrow.js']
              },{
                  name: 'past-weather',
                  files: ['assets/js/past-weather.js']
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
              })
                .state('tomorrow', {
                        url: "/tomorrow",
                        views: {
                            "": {
                                templateUrl: "templates/html/tomorrow.html"
                            }
                        },
                        resolve: {
                            loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                                return $ocLazyLoad.load('tomorrow');
                            }]
                        }
                    }) .state('past-weather', {
                        url: "/past-weather",
                        views: {
                            "": {
                                templateUrl: "templates/html/past-weather.html"
                            }
                        },
                        resolve: {
                            loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                                return $ocLazyLoad.load('past-weather');
                            }]
                        }
                    });
        $locationProvider.html5Mode(true)
  }]);

