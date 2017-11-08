var app = angular.module('main', ['ui.router', 'oc.lazyLoad']);

// Enter page

app.controller('widgets',['$scope', '$http', '$state','$stateParams', function($scope, $http, $state, $stateParams) {
    $scope.$state = $state;
    $scope.$stateParams = $stateParams;
    $scope.graph = $scope.$state.params.graph;
    $scope.tabClass = $scope.$state.params.tabClass;
    $scope.page=$scope.$state.params.page;
    $scope.searchInputWidget = '';
    $scope.searchListWidget = [];
    $scope.resultWidget = 0;
    $scope.captcha= false;
    $scope.el=$scope.$parent.el;
    $scope.el1=$scope.$parent.el1;
    $scope.el2=$scope.$parent.el2;

    $scope.getCode = function () {
        var answer = document.getElementById("wg_captcha_input").value;

        var digit1 = parseInt(document.getElementById("digit1").innerHTML);

        var digit2 = parseInt(document.getElementById("digit2").innerHTML);

        var sum = digit1 + digit2;

        if(answer == ""){
            $('#wg_captcha_input').focus()
            $('#wg_captcha_input').css({'border': '1px solid red'})
            setTimeout(function () {
                $('#wg_captcha_input').css({'border': '1px solid #00cda8'})
            }, 1000)
        }else if(answer != sum){
            $('#wg_captcha_input').focus()

            $('#wg_captcha_input').css({'border': '1px solid red'})
            setTimeout(function () {
                $('#wg_captcha_input').css({'border': '1px solid #00cda8'})
            }, 1000)
        }else{
            $scope.captcha= true;
        }

        if($scope.captcha) {
            var htmlWidget = $('.wg_response_wrap').clone()
            htmlWidget.find('.wg_respons_box').removeClass('ui-resizable')
            htmlWidget.find('.respons_handles').remove()
            htmlWidget.find('img').each(function () {
                $(this).attr('src', location.protocol + "//" + window.location.host + '/' + $(this).attr('src'))
            })
            htmlWidget.find('.wg_title').first().attr('id', 'opTitle').html('')
            htmlWidget.find('.wg_weather_img').attr('id', 'opImg')
            htmlWidget.find('.wg_weather_img1').attr('id', 'opImg1')
            htmlWidget.find('.wg_weather_img2').attr('id', 'opImg2')
            htmlWidget.find('.wg_weather_img3').attr('id', 'opImg3')
            htmlWidget.find('.img1').attr('id', 'opImg1')
            htmlWidget.find('.img2').attr('id', 'opImg2')
            htmlWidget.find('.img3').attr('id', 'opImg3')
            htmlWidget.find('.img4').attr('id', 'opImg4')
            htmlWidget.find('.temp1').attr('id', 'opTemp1')
            htmlWidget.find('.temp2').attr('id', 'opTemp2')
            htmlWidget.find('.temp3').attr('id', 'opTemp3')
            htmlWidget.find('.temp4').attr('id', 'opTemp4')
            htmlWidget.find('.wg_temp').attr('id', 'opTemp').html('')
            htmlWidget.find('.wg_temp1').attr('id', 'opTemp1').html('')
            htmlWidget.find('.wg_temp2').attr('id', 'opTemp2').html('')
            htmlWidget.find('.wg_temp3').attr('id', 'opTemp3').html('')
            htmlWidget.find('.wg_date').attr('id', 'opDate').html('')
            htmlWidget.find('.wg_date1').attr('id', 'opDate1').html('')
            htmlWidget.find('.wg_date2').attr('id', 'opDate2').html('')
            htmlWidget.find('.wg_date3').attr('id', 'opDate3').html('')
            htmlWidget.find('.wg_day').attr('id', 'opDay').html('')
            htmlWidget.find('.wg_day1').attr('id', 'opDay1').html('')
            htmlWidget.find('.wg_day2').attr('id', 'opDay2').html('')
            htmlWidget.find('.wg_day3').attr('id', 'opDay3').html('')
            htmlWidget.find('.wg_temp_feels').attr('id', 'opFeel').html('')
            htmlWidget.find('.wg_clarity').attr('id', 'opClarity').html('')
            htmlWidget.find('.wg_clarity1').attr('id', 'opClarity1').html('')
            htmlWidget.find('.wg_clarity2').attr('id', 'opClarity2').html('')
            htmlWidget.find('.wg_clarity3').attr('id', 'opClarity3').html('')
            htmlWidget.find('.wg_pressure').attr('id', 'opPressure').html('')
            htmlWidget.find('.wg_wind').attr('id', 'opWind').html('')
            htmlWidget.find('.widget_6').find('.wg_info_list').attr('id', 'threeDays')
            htmlWidget.find('.widget_7').find('.wg_info_list').attr('id', 'dayThreeDays')
            htmlWidget.find('.widget_4').find('.wg_content').attr('id', 'widget_4')

            if ($('#wg_wind_radio1').is(':checked')) {
                var wind = "m/s"
            } else {
                var wind = "mph"
            }

            if ($('#wg_temp_radio1').is(':checked')) {
                var temp = "C"
            } else {
                var temp = "F"
            }

            if ($('#wg_press_radio1').is(':checked')) {
                var pressure = "hPa"
            } else {
                var pressure = "in"
            }

            $('.wg_textarea').val(('<div id="Oplao" data-temp="' + temp + '" data-wind="' + wind + '" data-pressure="' + pressure + '" style="border: 0!important;" class="' +
            $('.wg_response_wrap').attr('class') + '">' + htmlWidget.html() + '</div>' + '<script type="text/javascript" charset="UTF-8" src="' + location.protocol + '//' + window.location.host + '/js/informer.js"></script>'))

        }
    }

    $scope.searchWidgetCities = function(){

        if($scope.searchInputWidget.length > 1){

            $.ajax({
                method: "POST",
                url: "/find_occurences/"+$scope.searchInputWidget
            }).done(function( msg ) {
                $scope.$apply(function(){
                    $scope.searchListWidget = msg;
                    $scope.resultWidget = 1;
                });
            })
        }else{
            $scope.resultWidget = 0;
        }
    }

    $scope.selectCityWidget = function(e, e1, e2){
        $('.wg_form_resault').removeClass('active_search')
        $scope.searchInputWidget = e1+", "+e2

        $.ajax({
            method: "POST",
            url: "/get_info_widgets/",
            data: {
                city: e
            }
        }).error(function( msg ) {

            $scope.selectedCityWidget =   {  "hours":22,
                "time": "14:10",
                "date": "15 MAY",
                "day": "mon",
                "city":"Minsk",
                "countryCode":"by",
                "feelsLikeC":"5",
                "feelsLikeF":"40",
                "weatherIconCode":"Fog",
                "clarity": 'Fog',
                "pressurehPa":"1030",
                "pressureInch":0.31,
                "temp_c":"6",
                "temp_f":"43",
                "windMph":"4",
                "windMs":2,
                "windDegree":250,
                "threeDays": {
                "day1": {
                    "date": "15 MAY",
                        "day": "mon",
                        "icon": "Clear",
                        "clarity": "Fog",
                        "temp_c":"6",
                        "temp_f":"45"
                },
                "day2": {
                    "date": "16 MAY",
                        "day": "mon",
                        "icon": "Clear",
                        "clarity": "Fog",
                        "temp_c":"6",
                        "temp_f":"43"
                },
                "day3": {
                    "date": "17 MAY",
                        "day": "mon",
                        "icon": "Clear",
                        "clarity": "Fog",
                        "temp_c":"6",
                        "temp_f":"41"
                }
            },
            "wholeDay": {
                "morning": {
                    "name": "morning",
                        "date": "15 MAY",
                        "day": "mon",
                        "icon": "Clear",
                        "clarity": "Fog",
                        "temp_c":"6",
                        "temp_f":"45"
                },
                "day": {
                    "name": "day",
                        "date": "16 MAY",
                        "day": "mon",
                        "icon": "Clear",
                        "clarity": "Fog",
                        "temp_c":"6",
                        "temp_f":"43"
                },
                "evening": {
                    "name": "evening",
                        "date": "17 MAY",
                        "day": "mon",
                        "icon": "Clear",
                        "clarity": "Fog",
                        "temp_c":"6",
                        "temp_f":"41"
                },
                "night": {
                    "name": "night",
                        "date": "17 MAY",
                        "day": "mon",
                        "icon": "Clear",
                        "clarity": "Fog",
                        "temp_c":"6",
                        "temp_f":"41"
                }
            }};
        }).done(function( msg ) {
            $scope.selectedCityWidget = msg[0]
        })
    };

    $scope.loadNumbers = function () {
        var rand_num1 = Math.floor(Math.random() * 10) + 1;

        var rand_num2 = Math.floor(Math.random() * 10) + 1;

        document.getElementById("digit1").innerHTML = rand_num1;

        document.getElementById("digit2").innerHTML = rand_num2;
    }
}])