var app = angular.module('main', ['ui.router', 'oc.lazyLoad', 'ngCookies']);

app.run( ['$rootScope', '$state', '$stateParams', '$http', '$cookies', function ($rootScope,   $state,   $stateParams, $http, $cookies) {
    $rootScope.$state = $state;
    $rootScope.$stateParams = $stateParams;
    $rootScope.example = "";
    $rootScope.local = {};
    $rootScope.currentCountryCode = $cookies.get('langCookieCode');

    $rootScope.updateLang = function () {
        var langRequest = {
            method: 'GET',
            url: '/generate_language_content',
            params: {
                langCode:$rootScope.currentCountryCode,
                path : location.pathname
            },
            headers: {
                'Content-Type': 'application/json; charset=utf-8'
            }}
        $http(langRequest).success(function (data) {
            $rootScope.pageContent = data;
        })
    }


    if($cookies.get('temp_val')===undefined){
        $cookies.put('temp_val', 'C');
        $rootScope.local.typeTemp = 'C';
    }else {
        $rootScope.local.typeTemp = $cookies.get('temp_val').toString();

    }
    if($cookies.get('time_val')===undefined){
        $cookies.put('time_val', 24);
        $rootScope.local.typeTime = 24;
    }else {
        $rootScope.local.typeTime = $cookies.get('time_val').toString();

    }

    if(parseInt($cookies.get('time_val'))===24){
        $rootScope.local.timeRange = ['00:00', '01:00', '02:00', '03:00', '04:00', '05:00', '06:00', '07:00', '08:00', '09:00', '10:00', '11:00', '12:00', '13:00', '14:00', '15:00', '16:00', '17:00', '18:00', '19:00', '20:00', '21:00', '22:00', '23:00'];
    }else {
        $rootScope.local.timeRange = ['12 AM', '1 AM', '2 AM', '3 AM', '4 AM', '5 AM', '6 AM', '7 AM', '8 AM', '9 AM', '10 AM', '11 AM', '12 PM', '1 PM', '2 PM', '3 PM', '4 PM', '5 PM', '6 PM', '7 PM', '8 PM', '9 PM', '10 PM', '11 PM'];
    }
    $rootScope.searchInput = '';
    $rootScope.searchList = [];
    $rootScope.result = 0;

    $rootScope.searchHint = function(){
        $('.search-dropdown ul').css({'display': 'none'})
        $('.search-dropdown img').css({'display': 'block'})
        // $('.search-dropdown').css({'display': 'block'})
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
                // $('.search-dropdown').css({'display': 'none'})

            }
        }
    }



    $rootScope.selectCity = function(e, e1, e2){
        $('.search-dropdown').removeClass('opened');
        $('.search-dropdown').css({'display': 'none'})
        $rootScope.searchInput = '';
        $('.ht-search-input input').val('')

        $rootScope.el=e;
        $rootScope.el1=e1;
        $rootScope.el2=e2;
        console.log(e);

        $.ajax({
            method: "POST",
            url: "/select_city/"+e
        }).done(function( msg ) {
            var url = document.location.pathname.split("/");
            $rootScope.selectedCity = msg.asciiName+"_"+msg.countryCode;
            if(url.length>2){
                if(document.URL.includes('_')){
                    url[url.length-1]=url[url.length-1].replace(url[url.length-1], msg.asciiName+"_"+msg.countryCode);
                    url = url.join('/').replace('//','/')
                }else{
                    url.push(msg.asciiName+"_"+msg.countryCode)
                    url = url.join('/').replace('//','/');
                }
            }else{
                url = "/"+$rootScope.currentCountryCode+"/weather/"+msg.asciiName+"_"+msg.countryCode;
            }
            if (history.pushState) {
                var newurl = window.location.protocol + "//" + window.location.host + url;
                // window.history.pushState({path:newurl},'',newurl);
                location.replace(newurl);
            }
            $state.reload();
            $rootScope.updateLang();
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
            $('.w' + $menuIndex).remove();
            $('.w' + $menuIndex + '_li').remove();

            var ln = $('.favorite-location .container')[0]['children'].length;
            if ($(window).width() < 500) {
                $('#top-page').animate({height: (300+(ln * 50))+'px'});
                $('#top-main').animate({height: '580px'});
            }
        })
    };
    $rootScope.selectLanguage = function (lan) {
        var curUrl = location.pathname.split('/');
        var url = curUrl[1].length == 2 ? window.location.href.replace(curUrl[1], lan) : "/" + lan + "/weather/" + $rootScope.selectedCity;
        var langRequest = {
            method: 'POST',
            url: "/generate_custom_request_weather",
            params: {
                language: lan,
                currentLocation: $rootScope.selectedCity
            },
            headers: {
                'Content-Type': 'application/json; charset=utf-8'
            }
        };

        $http(langRequest).success(function () {
            window.location.href = url;
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
        }
        location.reload();

    }
    $rootScope.updateTime = function(val){
        if(val===$cookies.get('time_val')){

        }
        else {
            if (parseInt($cookies.get('time_val')) === 24) {
                $cookies.put('time_val', 12);

            } else {
                $cookies.put('time_val', 24);

            }
        }


    }
    $rootScope.getTime = function(str){
        return changeTimeFormat(str, $rootScope.local.typeTime)
    }
}]);

