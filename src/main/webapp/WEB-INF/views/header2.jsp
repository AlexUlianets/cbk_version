<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en" ng-app="main">
<head>
    <base href="/"/>
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
    <link rel="canonical" href="${content.canonical}${selectedCity}">
    <meta name="msapplication-TileColor" content="#ffffff">
    <meta name="msapplication-TileImage" content="/ms-icon-144x144.png">
    <meta name="theme-color" content="#ffffff">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"/>
    <meta name="description" content="${content.description}">
    <meta property="og:title" content="Oplao"/>
    <meta property="og:type" content="website"/>
    <meta property="og:description" content="${content.description}"/>
    <meta property="og:image"
          content="https://simplesharebuttons.com/wp-content/uploads/2014/08/simple-share-buttons-logo-square.png"/>
    <title>${content.title}</title>
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
    <script type="text/javascript"
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAqRjBmS8aGyPsZqxDpZg9KsG9xiqgi95o"></script>
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
        <div class="container relative">
            <a href="/${currentCountryCode}/weather/${selectedCity}" class="h-logo"><img src="images/icons/logo.png"
                                                                                         alt="img"></a>

            <div class="header-bot-right">

                <a id="nav-toggle" href="#"><span></span><em></em></a>

                <div class="temp-wrap">
                    <div class="temp-block  tmp-cel">°C</div>
                    <div class="temp-block active active-mob tmp-far">°F</div>
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
                        <li><a href="#">
                            <span class="helper"></span>
                            <i class="icon facebook"></i>
                        </a></li>
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
                        <i>${currentCountryCode}</i>
                    </a>
                    <ul>
                        <li><a ng-click="selectLanguage('en')" ng-class="{active:currentCountryCode == 'en'}">En</a>
                        </li>
                        <li><a ng-click="selectLanguage('ru')" ng-class="{active:currentCountryCode == 'ru'}">Ru</a>
                        </li>
                        <li><a ng-click="selectLanguage('de')" ng-class="{active:currentCountryCode == 'de'}">De</a>
                        </li>
                        <li><a ng-click="selectLanguage('fr')" ng-class="{active:currentCountryCode == 'fr'}">Fr</a>
                        </li>
                        <li><a ng-click="selectLanguage('it')" ng-class="{active:currentCountryCode == 'it'}">It</a>
                        </li>
                        <li><a ng-click="selectLanguage('ua')" ng-class="{active:currentCountryCode == 'ua'}">Ua</a>
                        </li>
                        <li><a ng-click="selectLanguage('by')" ng-class="{active:currentCountryCode == 'by'}">By</a>
                        </li>
                    </ul>
                </div>
                <div class="h-settings">
                    <a href="#settings-popup" data-effect="mfp-zoom-in" class="s-popup"><span class="helper"></span><i
                            class="icon settings"></i></a>
                </div>

                <nav id="h-menu">
                    <ul>
                        <li><a href="/${currentCountryCode}/weather/widgets">${content.widgets}</a></li>
                        <li><a href="/${currentCountryCode}/weather/outlook/${selectedCity}">${content.weather}</a></li>
                        <li><a href="/${currentCountryCode}/weather/map/${selectedCity}">${content.temperatureMap}</a></li>
                        <li><a href="#alert-popup" data-effect="mfp-zoom-in" class="a-popup">${content.alerts}<span></span></a>
                        </li>
                    </ul>
                </nav>
            </div>
        </div><!-- end container -->

        <div id="main-menu">
            <div class="container">
                <div class="main-menu-inner">
                    <nav class="main-menu-block">
                        <h3>${content.weather}</h3>
                        <ul>
                            <li>
                                <a href="/${currentCountryCode}/weather/3/${selectedCity}">${content.threeDaysWeatherForecast}</a>
                            </li>
                            <li>
                                <a href="/${currentCountryCode}/weather/5/${selectedCity}">${content.fiveDaysWeatherForecast}</a>
                            </li>
                            <li>
                                <a href="/${currentCountryCode}/weather/7/${selectedCity}">${content.sevenDaysWeatherForecast}</a>
                            </li>
                            <li>
                                <a href="/${currentCountryCode}/weather/14/${selectedCity}">${content.fourteenDaysWeatherForecast}</a>
                            </li>
                            <li>
                                <a href="/${currentCountryCode}/weather/hour-by-hour3/${selectedCity}">${content.hourlyWeather}</a>
                            </li>
                            <li><a href="/${currentCountryCode}/weather/map/${selectedCity}">${content.weatherMap}</a>
                            </li>
                            <li>
                                <a href="/${currentCountryCode}/weather/history3/${selectedCity}">${content.weatherHistory}</a>
                            </li>
                        </ul>
                    </nav>

                    <nav class="main-menu-block">
                        <h3>${content.applications}</h3>
                        <ul>
                            <li>
                                <a href="https://play.google.com/store/apps/developer?id=Oplao">${content.androidApps}</a>
                            </li>
                            <li>
                                <a href="https://chrome.google.com/webstore/detail/oplao-weather/jbjeihiakbjchgaehgcilindlghfffnb">${content.googleChromeWeatherExtension}</a>
                            </li>
                            <li>
                                <a href="https://addons.mozilla.org/en-US/firefox/addon/oplao-weather/">${content.firefoxWeatherExtension}</a>
                            </li>
                            <li>
                                <a href="https://addons.opera.com/en/extensions/details/oplao-weather/">${content.operaWeatherExtension}</a>
                            </li>
                            <li><a href="/${currentCountryCode}/weather/widgets">${content.widgets}</a></li>
                        </ul>
                    </nav>

                    <nav class="main-menu-block">
                        <h3>${content.information}</h3>
                        <ul>
                            <!--<li><a href="/${currentCountryCode}/about" ng-bind="pageContent.about"></a></li>-->
                            <li><a href="https://blog.oplao.com/">${content.blog}</a></li>
                        </ul>
                    </nav>
                </div>
            </div>
        </div><!-- end main-menu -->
    </div><!-- end header-bot -->

</header>


<c:if test="${pageName == 'frontPage'}">

        <%@ include file="front-page-header.jsp" %>
</c:if>
<c:if test="${pageName != 'frontPage'}">
        <%@ include file="main-blue-header.jsp" %>
        <%@include file="body.jsp"%>
</c:if>