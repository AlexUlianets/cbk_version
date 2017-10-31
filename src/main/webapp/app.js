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
                if($('.favorite-location .container')[0]!= undefined) {
                    var ln = $('.favorite-location .container')[0]['children'].length;
                }
                if ($(window).width() < 500) {
                    $('#top-page').animate({height: (300+((ln) * 50))+'px'});
                }
            });
            // $('.tb-contant').removeClass('inner-html')

        }

        $rootScope.get_api_weather = function(){
            $.ajax({
                method: "POST",
                url: "/get_api_weather"
            }).done(function( msg ) {
                $rootScope.temperature = msg;
                $rootScope.get_recent_cities_tabs_func();
                $.ajax({
                    method: "GET",
                    url:"/get_selected_city"
                }).done(function (data) {
                    console.log(data)
                    $rootScope.selectedCity = data;
                })
                    loadScript();
            })
        }
        $rootScope.get_api_weather();
        $rootScope.generate_meta_title = function () {

            var sendingTableRequest = {
                method: 'GET',
                url: '/generate_meta_title',
                params: {
                    path:location.pathname
                },
                headers: {
                    'Content-Type': 'application/json; charset=utf-8'
                }}

            $http(sendingTableRequest).success(function (data) {
                $rootScope.metaData = data;
            })
        }
        $rootScope.generate_meta_title();
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
                var url = document.location.pathname.split("/");
                $rootScope.selectedCity = msg.name+"_"+msg.countryCode;
                if(url.length>2){
                    if(document.URL.includes('_')){
                        url[url.length-1]=url[url.length-1].replace(url[url.length-1], msg.name+"_"+msg.countryCode);
                        url = url.join('/').replace('//','/')
                    }else{
                        url.push(msg.name+"_"+msg.countryCode)
                        url = url.join('/').replace('//','/');
                    }
                }else{
                    url = "/en/weather/"+msg.name+"_"+msg.countryCode;
                }
                if (history.pushState) {
                    var newurl = window.location.protocol + "//" + window.location.host + url;
                    window.history.pushState({path:newurl},'',newurl);
                }
                $state.reload();
                $rootScope.get_api_weather();
                $('html, body').animate({
                    scrollTop: $('body').offset().top
                }, 1000);
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
                        $('#top-page').animate({height: (300+(ln * 50))+'px'});
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
              },{   name: 'three-days',
                  files: ['assets/js/universal-days.js']
              },{   name: 'seven-days',
                  files: ['assets/js/universal-days.js']
              },{   name: 'five-days',
                  files: ['assets/js/not-universal-days.js']
              },{   name: 'ten-days',
                  files: ['assets/js/not-universal-days.js']
              },{   name: 'hour-by-hour',
                  files: ['assets/js/hour-by-hour.js']
              },{   name: 'fourteen-days',
                  files: ['assets/js/universal-days.js']
              },{   name: 'about',
                  files: ['assets/js/universal-days.js']
              },{   name: 'front-page',
                  files: ['assets/js/front-page.js']
              }, {
                  name: 'widgets',
                  files: ['assets/js/widgets.js']
              }]
          });

          $stateProvider
              .state('main', {
                  url: "/",
                  params:{
                      city: {squash: true, value: null},
                      "graph": "",
                      "day": "front-page",
                      "pos": "slash"
                  },
                  views: {
                      "": {
                          templateUrl: "templates/html/front-page.html"
                      }
                  },
                  resolve: {
                      loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                          return $ocLazyLoad.load('front-page');
                      }]
                  }
              })
              .state('front-page', {
                  url: "/en/weather/:city",
                  params:{
                      city: {squash: true, value: null},
                      "graph": "",
                      "day": "front-page",
                      "pos": "main"
                  },
                  views: {
                      "": {
                          templateUrl: "templates/html/front-page.html"
                      }
                  },
                  resolve: {
                      loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                          return $ocLazyLoad.load('front-page');
                      }]
                  }
              })
              .state('outlook', {
                  url: "/en/weather/outlook/:city",
                  params:{
                      city: {squash: true, value: null},
                      "graph": "weatherTen",
                      "day": "Outlook",
                      "graphTitle":"Detailed weather for 10 days"
                  },
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
                  url: "/en/weather/today/:city",
                  params:{
                      city: {squash: true, value: null},
                      "graph" : "weatherDetailed",
                      "day": "Today",
                      "graphTitle":"Hour by hour weather"
                  },
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
                        url: "/en/weather/tomorrow/:city",
                        params:{
                            city: {squash: true, value: null},
                            "graph" : "none",
                            "day": "Tomorrow"
                        },
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
                        url: "/en/weather/history3/:city",
                          params:{
                              city: {squash: true, value: null},
                              "graph" : "weatherDetailed",
                              "day": "Past weather",
                              "hrs":3,
                              "graphTitle":"Weather for detailed day"
                          },
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
                    }).state('past-weather1', {
                        url: "/en/weather/history1/:city",
                          params:{
                              city: {squash: true, value: null},
                              "graph" : "weatherDetailed",
                              "day": "Past weather",
                              "hrs":1,
                              "graphTitle":"Weather for detailed day"
                          },
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
                    }).state('three-days', {
                        url: "/en/weather/3/:city",
                        params:{
                            city: {squash: true, value: null},
                            "index":3,
                            "tabClass" : "tabs tabs-three tb-tabs",
                            "page": "three-days",
                            "graph" : "weatherThree",
                            "day": "3 day",
                            "graphTitle": "weather for 3 days"
                        },
                        views: {
                            "": {
                                templateUrl: "templates/html/universal-days.html"
                            }
                        },
                        resolve: {
                            loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                                return $ocLazyLoad.load('three-days');
                            }]
                        }
                    }).state('seven-days', {
                        url: "/en/weather/7/:city",
                        params:{
                            city: {squash: true, value: null},
                          "index":7,
                           "tabClass" : "tb-slider tabs tb-tabs tb-tabs-full",
                            "page": "seven-days",
                            "graph" : "weatherSeven",
                            "day": "7 day",
                            "graphTitle": "weather for 7 days"
                        },
                        views: {
                            "": {
                                templateUrl: "templates/html/universal-days.html"
                            }
                        },
                        resolve: {
                            loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                                return $ocLazyLoad.load('seven-days');
                            }]
                        }
                    }).state('five-days', {
                        url: "/en/weather/5/:city",
                        params:{
                            city: {squash: true, value: null},
                          "index":5,
                            "page": "five-days",
                            "graph": "weatherFive",
                            "day": "5 day",
                            "graphTitle":"Weather for 5 days"

                        },
                        views: {
                            "": {
                                templateUrl: "templates/html/not-universal-days.html"
                            }
                        },
                        resolve: {
                            loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                                return $ocLazyLoad.load('five-days');
                            }]
                        }
                    }).state('ten-days', {
                        url: "/en/weather/10/:city",
                        params:{
                            city: {squash: true, value: null},
                          "index":10,
                            "page": "ten-days",
                            "graph": "weatherTen",
                            "day": "10 day",
                            "graphTitle":"Weather for 10 days"
                        },
                        views: {
                            "": {
                                templateUrl: "templates/html/not-universal-days.html"
                            }
                        },
                        resolve: {
                            loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                                return $ocLazyLoad.load('ten-days');
                            }]
                        }
                    }).state('hour-by-hour', {
                        url: "/en/weather/hour-by-hour3/:city",
                        params:{
                            city: {squash: true, value: null},
                          "index":7,
                            "day": "Hour by hour",
                            "hrs":3,
                            "graphTitle":"Hour by hour weather"
                            //"tabClass" : "tb-slider tabs tb-tabs tb-tabs-full"
                        },
                        views: {
                            "": {
                                templateUrl: "templates/html/hour-by-hour.html"
                            }
                        },
                        resolve: {
                            loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                                return $ocLazyLoad.load('hour-by-hour');
                            }]
                        }
                    }).state('hour-by-hour1', {
                        url: "/en/weather/hour-by-hour1/:city",
                        params:{
                            city: {squash: true, value: null},
                          "index":7,
                            "day": "Hour by hour",
                            "hrs":1,
                            "graphTitle":"Hour by hour weather"
                            //"tabClass" : "tb-slider tabs tb-tabs tb-tabs-full"
                        },
                        views: {
                            "": {
                                templateUrl: "templates/html/hour-by-hour.html"
                            }
                        },
                        resolve: {
                            loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                                return $ocLazyLoad.load('hour-by-hour');
                            }]
                        }
                    }).state('fourteen-days', {
                        url: "/en/weather/14/:city",
                          params:{
                              city: {squash: true, value: null},
                              "index":14,
                              "tabClass" : "tb-slider tabs tb-tabs tb-tabs-full",
                              "page": "fourteen-days",
                              "graph" : "weatherFourteen",
                              "day": "14 day",
                              "graphTitle":"Long term weather"
                          },
                        views: {
                            "": {
                                templateUrl: "templates/html/universal-days.html"
                            }
                        },
                        resolve: {
                            loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                                return $ocLazyLoad.load('fourteen-days');
                            }]
                        }
                    }).state('about', {
                        url: "/en/about",

                        views: {
                            "": {
                                templateUrl: "templates/html/about.html"
                            }
                        },
                        resolve: {
                            loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                                return $ocLazyLoad.load('about');
                            }]
                        }
                    }).state('widgets', {
                          url: "/en/widgets",

                          views: {
                              "": {
                                  templateUrl: "templates/html/widgets.html"
                              }
                          },
                          resolve: {
                              loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                                  return $ocLazyLoad.load('widgets');
                              }]
                          }
                      });
        $locationProvider.html5Mode({
            enabled: true,
            rewriteLinks: false
        })
  }]);

