
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en" ng-app="main">
<head>
    <base href="/" />
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv='cache-control' content='no-cache'>
    <meta http-equiv='expires' content='0'>
    <meta http-equiv='pragma' content='no-cache'>
    <link rel="apple-touch-icon" sizes="57x57" href="../../favicon/apple-icon-57x57.png">
    <link rel="apple-touch-icon" sizes="60x60" href="../../favicon/apple-icon-60x60.png">
    <link rel="apple-touch-icon" sizes="72x72" href="../../favicon/apple-icon-72x72.png">
    <link rel="apple-touch-icon" sizes="76x76" href="../../favicon/apple-icon-76x76.png">
    <link rel="apple-touch-icon" sizes="114x114" href="../../favicon/apple-icon-114x114.png">
    <link rel="apple-touch-icon" sizes="120x120" href="../../favicon/apple-icon-120x120.png">
    <link rel="apple-touch-icon" sizes="144x144" href="../../favicon/apple-icon-144x144.png">
    <link rel="apple-touch-icon" sizes="152x152" href="../../favicon/apple-icon-152x152.png">
    <link rel="apple-touch-icon" sizes="180x180" href="../../favicon/apple-icon-180x180.png">
    <link rel="icon" type="image/png" sizes="192x192" href="../../favicon/android-icon-192x192.png">
    <link rel="icon" type="image/png" sizes="32x32" href="../../favicon/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="96x96" href="../../favicon/favicon-96x96.png">
    <link rel="icon" type="image/png" sizes="16x16" href="../../favicon/favicon-16x16.png">
    <link rel="manifest" href="../../favicon/manifest.json">
    <link rel="canonical" href="{{pageContent.canonical}}{{selectedCity}}">
    <meta name="msapplication-TileColor" content="#ffffff">
    <meta name="msapplication-TileImage" content="/ms-icon-144x144.png">
    <meta name="theme-color" content="#ffffff">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"/>
    <meta name="description" content="{{pageContent.description}}">
    <meta property="og:title" content="Oplao"/>
    <meta property="og:type" content="website"/>
    <meta property="og:description" content="{{pageContent.description}}"/>
    <meta property="og:image" content="https://simplesharebuttons.com/wp-content/uploads/2014/08/simple-share-buttons-logo-square.png"/>
    <title ng-bind="pageContent.title"></title>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <link rel="stylesheet" href="../../css/style.css">
    <link rel="stylesheet" href="../../css/jquery-ui.min.css">
    <link rel="stylesheet" href="../../css/jquery.formstyler.css">
    <link rel="stylesheet" href="../../css/style.css">
    <link rel="stylesheet" href="../../css/slick.css">
    <link href='https://fonts.googleapis.com/css?family=Fira+Sans:300,400,500,700' rel='stylesheet'>
    <link rel="stylesheet" href="../../scss/widget.css">
    <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAqRjBmS8aGyPsZqxDpZg9KsG9xiqgi95o"></script>
    <style>
        #widget_carusel, .wg_choice_wrap {
            visibility: hidden;
        }
    </style>
</head>
<body>

<header>
    <div class="blur">
        <div class="header-top">
            <div class="container">
                <div class="header-top-banner"></div>
            </div>
        </div><!-- end header-top -->
    </div>
    <div class="header-bot" id="fix-menu">
        <div class="container">
            <a href="/{{currentCountryCode}}/weather/{{selectedCity}}" class="h-logo"><img src="images/icons/logo.png" alt="img"></a>

            <div class="header-bot-right" >

                <a id="nav-toggle" href="#"><span></span><em></em></a>

                <div class="temp-wrap">
                    <div ng-if="local.typeTemp !== 'F'" class="temp-block tmp-cel"  ng-click="local.typeTemp = 'F'; updateTemp(local.typeTemp);">°F</div>
                    <div ng-if="local.typeTemp !== 'C'" class="temp-block tmp-cel"  ng-click="local.typeTemp = 'C'; updateTemp(local.typeTemp);">°C</div>

                    <div ng-if="local.typeTemp == 'F'" class="temp-block tmp-far active active-mob">°F</div>
                    <div ng-if="local.typeTemp == 'C'" class="temp-block tmp-far active active-mob">°C</div>
                </div><!-- end temp-wrap -->

                <div id="h-share">
                    <a href="#">
                        <span class="helper"></span>
                        <i class="icon share"></i>
                    </a>
                    <ul>
                        <li><a href="#">
                            <span class="helper"></span>
                            <i class="icon vk"></i>
                        </a></li>
                        <li>
                            <a href="http://www.facebook.com/sharer.php?u=http://192.168.0.102:8080/" target="_blank">
                                <span class="helper"></span>
                                <i class="icon facebook"></i>
                            </a>
                        </li>
                        <li><a href="#">
                            <span class="helper"></span>
                            <i class="icon twitter"></i>
                        </a></li>
                        <li><a href="#">
                            <span class="helper"></span>
                            <i class="icon google"></i>
                        </a></li>
                    </ul>
                </div>

                <div id="h-share" class="h-lang">
                    <a>
                        <span class="helper"></span>
                        <i>{{currentCountryCode}}</i>
                    </a>
                    <ul>
                        <li><a ng-click="selectLanguage('en')" ng-class="{active:currentCountryCode == 'en'}">En</a></li>
                        <li><a ng-click="selectLanguage('ru')" ng-class="{active:currentCountryCode == 'ru'}">Ru</a></li>
                        <li><a ng-click="selectLanguage('de')" ng-class="{active:currentCountryCode == 'de'}">De</a></li>
                        <li><a ng-click="selectLanguage('fr')" ng-class="{active:currentCountryCode == 'fr'}">Fr</a></li>
                        <li><a ng-click="selectLanguage('it')" ng-class="{active:currentCountryCode == 'it'}">It</a></li>
                        <li><a ng-click="selectLanguage('ua')" ng-class="{active:currentCountryCode == 'ua'}">Ua</a></li>
                        <li><a ng-click="selectLanguage('by')" ng-class="{active:currentCountryCode == 'by'}">By</a></li>
                    </ul>
                </div>


                <div class="h-settings">
                    <a href="#settings-popup" data-effect="mfp-zoom-in" class="s-popup"><span class="helper"></span><i
                            class="icon settings"></i></a>
                </div>

                <nav id="h-menu">
                    <ul>
                        <li><a href="/{{currentCountryCode}}/weather/widgets" ng-bind="pageContent.widgets"></a></li>
                        <li><a href="/{{currentCountryCode}}/weather/outlook/{{selectedCity}}" ng-bind="pageContent.weather"></a></li>
                        <li><a href="/{{currentCountryCode}}/weather/map/{{selectedCity}}" ng-bind="pageContent.temperatureMap"></a></li>
                        <li><a href="#alert-popup" data-effect="mfp-zoom-in" class="a-popup" ng-bind="pageContent.alerts"><span></span></a>
                        </li>
                    </ul>
                </nav>
            </div>
        </div><!-- end container -->

        <div id="main-menu">
            <div class="container">
                <div class="main-menu-inner">
                    <nav class="main-menu-block">
                        <h3 ng-bind="pageContent.weather"></h3>
                        <ul>
                            <li><a href="/{{currentCountryCode}}/weather/3/{{selectedCity}}" ng-bind="pageContent.threeDaysWeatherForecast"></a></li>
                            <li><a href="/{{currentCountryCode}}/weather/5/{{selectedCity}}" ng-bind="pageContent.fiveDaysWeatherForecast"></a></li>
                            <li><a href="/{{currentCountryCode}}/weather/7/{{selectedCity}}" ng-bind="pageContent.sevenDaysWeatherForecast"></a></li>
                            <li><a href="/{{currentCountryCode}}/weather/14/{{selectedCity}}" ng-bind="pageContent.fourteenDaysWeatherForecast"></a></li>
                            <li><a href="/{{currentCountryCode}}/weather/hour-by-hour3/{{selectedCity}}" ng-bind="pageContent.hourlyWeather"></a></li>
                            <li><a href="/{{currentCountryCode}}/weather/map/{{selectedCity}}" ng-bind="pageContent.weatherMap"></a></li>
                            <li><a href="/{{currentCountryCode}}/weather/history3/{{selectedCity}}" ng-bind="pageContent.weatherHistory"></a></li>
                        </ul>
                    </nav>

                    <nav class="main-menu-block">
                        <h3 ng-bind="pageContent.applications"></h3>
                        <ul>
                            <li><a href="https://chrome.google.com/webstore/detail/oplao-weather/jbjeihiakbjchgaehgcilindlghfffnb" ng-bind="pageContent.googleChromeWeatherExtension"></a></li>
                            <li><a href="https://addons.mozilla.org/en-US/firefox/addon/oplao-weather/" ng-bind="pageContent.firefoxWeatherExtension"></a></li>
                            <li><a href="https://addons.opera.com/en/extensions/details/oplao-weather/" ng-bind="pageContent.operaWeatherExtension"></a></li>
                            <li><a href="/{{currentCountryCode}}/weather/widgets" ng-bind="pageContent.widgets"></a></li>

                        </ul>
                    </nav>

                    <nav class="main-menu-block">
                        <h3 ng-bind="pageContent.information"></h3>
                        <ul>
                            <!--<li><a href="/{{currentCountryCode}}/about"></a></li>-->
                            <li><a href="https://blog.oplao.com/">Blog</a></li>
                        </ul>
                    </nav>
                </div>
            </div>
        </div><!-- end main-menu -->
    </div><!-- end header-bot -->
</header>


<div class="blur">
    <section ng-attr-id="$state.params.day=='front-page' ? 'top-main': 'top-page'">
        <span  style="text-align: center; display:block; margin-top: 90px;text-align: -webkit-center;" class="load_header">
              <svg xmlns="http://www.w3.org/2000/svg" width="100" height="25" viewBox="0 0 120 30" fill="#fff"><circle cx="15" cy="15" r="11.8022"><animate attributeName="r" from="15" to="15" begin="0s" dur="0.8s" values="15;9;15" calcMode="linear" repeatCount="indefinite"/><animate attributeName="fill-opacity" from="1" to="1" begin="0s" dur="0.8s" values="1;.5;1" calcMode="linear" repeatCount="indefinite"/></circle><circle cx="60" cy="15" r="12.1978" fill-opacity="0.3"><animate attributeName="r" from="9" to="9" begin="0s" dur="0.8s" values="9;15;9" calcMode="linear" repeatCount="indefinite"/><animate attributeName="fill-opacity" from="0.5" to="0.5" begin="0s" dur="0.8s" values=".5;1;.5" calcMode="linear" repeatCount="indefinite"/></circle><circle cx="105" cy="15" r="11.8022"><animate attributeName="r" from="15" to="15" begin="0s" dur="0.8s" values="15;9;15" calcMode="linear" repeatCount="indefinite"/><animate attributeName="fill-opacity" from="1" to="1" begin="0s" dur="0.8s" values="1;.5;1" calcMode="linear" repeatCount="indefinite"/></circle></svg>
        </span>
        <div class="container" style="display: none">
            <div class="head-top">
                <div class="ht-search">
                    <form class="ht-search-wrap">
                        <div class="ht-search-inner">
                            <div class="ht-search-input" ng-click="searchHint()"  >
                                <div class="searchIco" onclick="onIcoSearch()">
                                    <i></i>
                                </div>
                                <input type="text" value=""
                                       placeholder="{{pageContent.searchTip}}" ng-keyup="searchHint()" ng-model="searchInput">
                            </div>
                            <!--<div class="ht-search-ico"><input type="submit" value=""></div> \-->
                            <div class="ht-search-autoloc"><span></span></div>
                        </div>
                        <div class="search-dropdown">
                            <img src="../../images/cloud_load.gif" style="width: 18%;margin-left: 41%;display: none;margin-right: 41%;" class="load_search">
                            <ul>
                                <li ng-repeat="i in searchList" ng-if="searchInput.length>1 && result==1"><a  ng-class="{'active':i.geonameId==temperature.geonameId}" ng-click="selectCity(i.geonameId)">{{i.name}}, {{i.countryName}} <span ng-if="false"></span></a></li>
                                <li ng-repeat="i in searchList track by $index" ng-if="searchInput.length<=1" class="w{{$index}}_li"><a id="w{{$index}}" ng-class="{'active':i.geonameId==temperature.geonameId}" ng-click="selectCity(i.geonameId)" >{{i.city}}</a><span ng-if="i.geonameId!=temperature.geonameId" ng-click="deleteCity(i.geonameId, $index)"></span></li>
                                <li ng-if="searchList.length==0"><a>No results</a></li>
                            </ul>
                            <a href="#" class="dropdown-top"></a>
                        </div>
                    </form>
                </div>
                <div class="ht-location">
                    <dl>
                        <dt><i class="icon location"></i><span class="search-text1">{{temperature.cityWeather}}</span>  <span
                                class="search-text2">{{temperature.country}}</span></dt>
                        <dd>{{pageContent.today}} {{temperature.month}} {{temperature.day}}. {{temperature.dayOfWeek}}. <span  ng-bind="getTime(('0' + temperature.hours).slice(-2)+':'+('0' + temperature.minutes).slice(-2))"></span></dd>
                    </dl>
                </div>


            </div><!-- end head-top -->


            <div class="favorite-location" ng-if="get_recent_cities_tabs.length!=0 && $state.params.day!='front-page'">
                <div class="container">
                    <a ng-repeat="i in get_recent_cities_tabs track by $index" ng-click="selectCity(i.geonameId)" class="weather-block-favorite w{{$index}}">
                        <div class="wb-left">
                            <img ng-src="svg/wicons_svg/{{i.weatherCode}}_{{(i.hours<=6 || i.hours>=18)?'night':'day'}}.svg" style="width: 30px;">
                            <div class="wbl-temp">{{local.typeTemp=='C'?i.tempC:i.tempF}} <span>{{local.typeTemp}}</span></div>
                        </div>
                        <div class="wb-right">
                            <div>
                                <h3>{{i.city}}<span>,&nbsp;</span></h3>
                                <h4>{{i.countryCode}}</h4>
                            </div>
                        </div>
                    </a>
                </div>
            </div>
            <div class="head-bot" id="head-bot" ng-if="$state.params.day=='front-page'">
                <div class="hb-inner">
                    <div class="weather-now">
                        <img width="90px" ng-src="svg/wicons_svg_white/{{temperature.weatherIconCode}}_{{(temperature.hours<=6 || temperature.hours>=18)?'night':'day'}}.svg" alt="img">
                        <div><b ng-bind="local.typeTemp=='C'?temperature.temp_c:temperature.temp_f"></b><em ng-bind="local.typeTemp"></em>
                            <span>/<strong ng-bind="local.typeTemp=='C'?temperature.temp_f:temperature.temp_c"></strong><abbr ng-bind="local.typeTemp!='C'?'C':'F'"></abbr></span></div>
                    </div>

                    <div class="weather-info">
                        <div class="weather-info-block">
                            <div class="wb-img">
                                <span class="helper"></span>
                                <img src="../../img/feels_like_white.svg" alt="img" style="    width: 24px;">
                            </div>
                            <h4 ng-bind="pageContent.feelsLike"></h4>
                            <p>{{local.typeTemp=='C'?temperature.feelsLikeC:temperature.feelsLikeF}} <span class="cels">{{local.typeTemp}}</span></p>
                        </div>
                        <div class="weather-info-block">
                            <div class="wb-img">
                                <span class="helper"></span>
                                <img src="../../img/humidity.svg" alt="img" style="    width: 40px;">
                            </div>
                            <h4 ng-bind="pageContent.humidity"></h4>
                            <p>{{temperature.humidity}}<span>%</span></p>
                        </div>
                        <div class="weather-info-block">
                            <div class="wb-img">
                                <span class="helper"></span>
                                <img src="../../images/svg-sprite/pressure_white_fill.svg" alt="img" style="    width: 42px;">
                            </div>
                            <h4 ng-bind="pageContent.pressure"></h4>
                            <p>{{local.typeTemp=='C'?temperature.pressurehPa:temperature.pressureInch}} <span>{{local.typeTemp=='C'?pageContent.hPa:pageContent.in}}</span></p>
                        </div>
                        <div class="weather-info-block">
                            <div class="wb-img">
                                <span class="helper"></span>
                                <!-- <i class="sprite-wind"></i> -->
                                <img src="../../img/wind_white.svg" alt="img" style="    width: 25px;    -ms-transform: rotate({{temperature.windDegree + 'deg'}});    -webkit-transform: rotate({{temperature.windDegree + 'deg'}});    transform: rotate({{temperature.windDegree + 'deg'}});">
                            </div>
                            <h4 ng-bind="pageContent.wind"></h4>
                            <p>{{temperature.wind}} <span>{{local.typeTemp=='C'?temperature.windMs:temperature.windMph}} {{local.typeTemp=='C'?pageContent.ms:pageContent.mph}}</span></p>
                        </div>
                    </div><!-- end weather-info -->

                    <div class="weather-time">
                        <div class="weather-time-block">
                            <div class="wtb-img">
                                <span class="helper"></span>
                                <img src="../../images/svg-sprite/sunrise-weather-symbol.svg" alt="" style="width: 35px;">
                            </div>
                            <div class="wt-time"  ng-bind="getTime(temperature.sunrise)">{{temperature.sunrise}}</div>
                        </div>
                        <div class="weather-time-block">
                            <div class="wtb-img">
                                <span class="helper"></span>
                                <!-- <i class="icon pm"></i> -->
                                <img src="../../images/svg-sprite/sunset-fill-interface-symbol.svg" alt="" style="width: 35px;">
                            </div>
                            <div class="wt-time" ng-bind="getTime(temperature.sunset)">{{temperature.sunset}}</div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="h-full-forecast" ng-if="$state.params.day=='front-page'">
                <div class="container">
                    <a href="/{{currentCountryCode}}/weather/outlook/{{selectedCity}}"> {{pageContent.fullForecast}}<span class="arr-right-white"></span></a>
                </div>
            </div>
        </div><!-- end container -->
        <div class="head-bot-mini" style="display: none" id="head-bot-mini" >
            <div class="hb-inner" ng-if="$state.params.day!='front-page'">
                <div class="weather-now">
                    <img ng-src="svg/wicons_svg_white/{{temperature.weatherIconCode}}_{{(temperature.hours<=6 || temperature.hours>=18)?'night':'day'}}.svg">
                    <div><b ng-bind="local.typeTemp=='C'?temperature.temp_c:temperature.temp_f"></b><em ng-bind="local.typeTemp"></em>
                        <span>/<strong ng-bind="local.typeTemp=='C'?temperature.temp_f:temperature.temp_c"></strong><abbr ng-bind="local.typeTemp!='C'?'C':'F'"></abbr></span></div>
                </div>

                <div class="weather-info">
                    <div class="weather-info-block">
                        <div class="wb-img">
                            <span class="helper"></span>
                            <img src="../../img/feels_like_white.svg" alt="img" style="    width: 24px;">
                        </div>
                        <h4 ng-bind="pageContent.feelsLike"></h4>
                        <p>{{local.typeTemp=='C'?temperature.feelsLikeC:temperature.feelsLikeF}} <span class="cels">{{local.typeTemp}}</span></p>
                    </div>
                    <div class="weather-info-block">
                        <div class="wb-img">
                            <span class="helper"></span>
                            <img src="../../img/humidity.svg" alt="img" style="    width: 40px;">
                        </div>
                        <h4 ng-bind="pageContent.humidity"></h4>
                        <p>{{temperature.humidity}}<span>%</span></p>
                    </div>
                    <div class="weather-info-block">
                        <div class="wb-img">
                            <span class="helper"></span>
                            <img src="../../images/svg-sprite/pressure_white_fill.svg" alt="img" style="    width: 42px;">
                        </div>
                        <h4 ng-bind="pageContent.pressure"></h4>
                        <p>{{local.typeTemp=='C'?temperature.pressurehPa:temperature.pressureInch}} <span>{{local.typeTemp=='C'?pageContent.hPa:pageContent.in}}</span></p>
                    </div>
                    <div class="weather-info-block">
                        <div class="wb-img">
                            <span class="helper"></span>
                            <!-- <i class="sprite-wind"></i> -->
                            <img src="../../img/wind_white.svg" alt="img" style="    width: 25px;    -ms-transform: rotate({{temperature.windDegree + 'deg'}});    -webkit-transform: rotate({{temperature.windDegree + 'deg'}});    transform: rotate({{temperature.windDegree + 'deg'}});">
                        </div>
                        <h4 ng-bind="pageContent.wind"></h4>
                        <p>{{temperature.wind}} <span>{{local.typeTemp=='C'?temperature.windMs:temperature.windMph}} {{local.typeTemp=='C'?pageContent.ms:pageContent.mph}}</span></p>
                    </div>
                </div><!-- end weather-info -->

                <div class="weather-time">
                    <div class="weather-time-block">
                        <div class="wtb-img">
                            <span class="helper"></span>
                            <img src="../../images/svg-sprite/sunrise-weather-symbol.svg" alt="" style="width: 35px;">
                        </div>
                        <div class="wt-time"  ng-bind="getTime(temperature.sunrise)"></div>
                    </div>
                    <div class="weather-time-block">
                        <div class="wtb-img">
                            <span class="helper"></span>
                            <!-- <i class="icon pm"></i> -->
                            <img src="../../images/svg-sprite/sunset-fill-interface-symbol.svg" alt="" style="width: 35px;">
                        </div>
                        <div class="wt-time"  ng-bind="getTime(temperature.sunset)"></div>
                    </div>
                </div>
            </div>
        </div>

    </section>
