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
	<link rel="canonical" href="${content.canonical}${selectedCity}">
	<meta name="msapplication-TileColor" content="#ffffff">
	<meta name="msapplication-TileImage" content="/ms-icon-144x144.png">
	<meta name="theme-color" content="#ffffff">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"/>
	<meta name="description" content="${content.description}">
	<meta property="og:title" content="Oplao"/>
	<meta property="og:type" content="website"/>
	<meta property="og:description" content="${content.description}"/>
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
			<div class="container relative">
				<a href="/${currentCountryCode}/weather/${selectedCity}" class="h-logo"><img src="images/icons/logo.png" alt="img"></a>

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
						<a href="#settings-popup"  data-effect="mfp-zoom-in" class="s-popup"><span class="helper"></span><i class="icon settings"></i></a>
					</div>

					<nav id="h-menu">
						<ul>
							<li><a href="/${currentCountryCode}/weather/widgets" ng-bind="pageContent.widgets"></a></li>
							<li><a href="/${currentCountryCode}/weather/outlook/${selectedCity}" ng-bind="pageContent.weather"></a></li>
							<li><a href="/${currentCountryCode}/weather/map/${selectedCity}" ng-bind="pageContent.temperatureMap"></a></li>
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
								<li><a href="/${currentCountryCode}/weather/3/${selectedCity}" ng-bind="pageContent.threeDaysWeatherForecast"></a></li>
								<li><a href="/${currentCountryCode}/weather/5/${selectedCity}" ng-bind="pageContent.fiveDaysWeatherForecast"></a></li>
								<li><a href="/${currentCountryCode}/weather/7/${selectedCity}" ng-bind="pageContent.sevenDaysWeatherForecast"></a></li>
								<li><a href="/${currentCountryCode}/weather/14/${selectedCity}" ng-bind="pageContent.fourteenDaysWeatherForecast"></a></li>
								<li><a href="/${currentCountryCode}/weather/hour-by-hour3/${selectedCity}" ng-bind="pageContent.hourlyWeather"></a></li>
								<li><a href="/${currentCountryCode}/weather/map/${selectedCity}" ng-bind="pageContent.weatherMap"></a></li>
								<li><a href="/${currentCountryCode}/weather/history3/${selectedCity}" ng-bind="pageContent.weatherHistory"></a></li>
							</ul>
						</nav>

						<nav class="main-menu-block">
							<h3 ng-bind="pageContent.applications"></h3>
							<ul>
								<li><a href="https://chrome.google.com/webstore/detail/oplao-weather/jbjeihiakbjchgaehgcilindlghfffnb" ng-bind="pageContent.googleChromeWeatherExtension"></a></li>
								<li><a href="https://addons.mozilla.org/en-US/firefox/addon/oplao-weather/" ng-bind="pageContent.firefoxWeatherExtension"></a></li>
								<li><a href="https://addons.opera.com/en/extensions/details/oplao-weather/" ng-bind="pageContent.operaWeatherExtension"></a></li>
								<li><a href="/${currentCountryCode}/weather/widgets" ng-bind="pageContent.widgets"></a></li>

							</ul>
						</nav>

						<nav class="main-menu-block">
							<h3 ng-bind="pageContent.information"></h3>
							<ul>
								<!--<li><a href="/${currentCountryCode}/about"></a></li>-->
								<li><a href="https://blog.oplao.com/">Blog</a></li>
							</ul>
						</nav>
					</div>
				</div>
			</div><!-- end main-menu -->
		</div><!-- end header-bot -->

	</header>

	<div class="blur">
	<section id="top-main">
		<div class="container">
			<div class="head-top">
				<div class="ht-search">
					<form class="ht-search-wrap">
						<div class="ht-search-inner">
							<div class="ht-search-input" ng-click="searchHint()"  >
								<div class="searchIco" onclick="onIcoSearch()">
									<i></i>
								</div>
								<input type="text" value=""
									   placeholder="${content.searchTip}" ng-keyup="searchHint()" ng-model="searchInput">
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
						<dt><i class="icon location"></i><span class="search-text1">${temperature.cityWeather}</span>  <span
								class="search-text2">${temperature.country}</span></dt>
						<dd>${content.today} ${temperature.month} ${temperature.day}. ${temperature.dayOfWeek}. <span  ng-bind="getTime(('0' + ${temperature.hours}).slice(-2)+':'+('0' + ${temperature.minutes}).slice(-2))"></span></dd>
					</dl>
				</div>
			</div>

			<div class="head-bot">
				<div class="hb-inner">
					<div class="weather-now">
						<img width="90px" ng-src="svg/wicons_svg_white/${temperature.weatherIconCode}.svg" alt="img">
						<div><b ng-bind="local.typeTemp=='C'?${temperature.temp_c}:${temperature.temp_f}"></b><em ng-bind="local.typeTemp"></em>
							<span>/<strong ng-bind="local.typeTemp=='C'?${temperature.temp_f}:${temperature.temp_c}"></strong><abbr ng-bind="local.typeTemp!='C'?'C':'F'"></abbr></span></div>
					</div>

					<div class="weather-info">
						<div class="weather-info-block">
							<div class="wb-img">
								<span class="helper"></span>
								<img src="../../img/feels_like_white.svg" alt="img" style="    width: 24px;">
							</div>
							<h4> ${content.feelsLike}</h4>
							<c:if test = "${typeTemp == 'C'}">
								<p> ${temperature.feelsLikeC} <span class="cels">${typeTemp}</span></p>
							</c:if>
							<c:if test = "${typeTemp == 'F'}">
								<p> ${temperature.feelsLikeF} <span class="cels">${typeTemp}</span></p>
							</c:if>

						</div>
						<div class="weather-info-block">
							<div class="wb-img">
								<span class="helper"></span>
								<img src="../../img/humidity.svg" alt="img" style="    width: 40px;">
							</div>
							<h4>${content.humidity}</h4>
							<p>${temperature.humidity}<span>%</span></p>
						</div>
						<div class="weather-info-block">
							<div class="wb-img">
								<span class="helper"></span>
								<img src="../../images/svg-sprite/pressure_white_fill.svg" alt="img" style="    width: 42px;">
							</div>
							<h4>${content.pressure}</h4>
							<c:if test = "${typeTemp == 'C'}">
								<p>	${temperature.pressurehPa} <span>${content.hPa} </span> </p>
							</c:if>
							<c:if test = "${typeTemp == 'F'}">
								<p>	${temperature.pressureInch} <span>${content.in} </span> </p>
							</c:if>
						</div>
						<div class="weather-info-block">
							<div class="wb-img">
								<span class="helper"></span>
								<!-- <i class="sprite-wind"></i> -->
								<img src="../../img/wind_white.svg" alt="img" style="    width: 25px;    -ms-transform: rotate(${temperature.windDegree} + 'deg' );    -webkit-transform: rotate(${temperature.windDegree } + 'deg');    transform: rotate(${temperature.windDegree} +  'deg');">
							</div>
							<h4>${content.wind}</h4>

							<c:if test = "${typeTemp == 'C'}">
								<p>	${temperature.windMs} <span>${content.ms} </span> </p>
							</c:if>
							<c:if test = "${typeTemp == 'F'}">
								<p>	${temperature.windMph} <span>${content.mph} </span> </p>
							</c:if>
						</div>
					</div><!-- end weather-info -->

					<div class="weather-time">
						<div class="weather-time-block">
							<div class="wtb-img">
								<span class="helper"></span>
								<img src="../../images/svg-sprite/sunrise-weather-symbol.svg" alt="" style="width: 35px;">
							</div>
							<div class="wt-time"  ng-bind="getTime(${temperature.sunrise})">${temperature.sunrise}</div>
						</div>
						<div class="weather-time-block">
							<div class="wtb-img">
								<span class="helper"></span>
								<!-- <i class="icon pm"></i> -->
								<img src="../../images/svg-sprite/sunset-fill-interface-symbol.svg" alt="" style="width: 35px;">
							</div>
							<div class="wt-time" ng-bind="getTime(${temperature.sunset})">${temperature.sunset}</div>
						</div>
					</div>
				</div>
			</div><!-- end head-bot -->

			<div class="h-full-forecast"">
				<div class="container">
					<a href="/${currentCountryCode}/weather/outlook/${selectedCity}"> ${content.fullForecast}<span class="arr-right-white"></span></a>
				</div>
			</div>
		</div><!-- end container -->
	</section><!-- end top main -->

	<section class="three-days">
		<c:forEach var = "i" begin = "0" end = "2">
			<div class="three-days-block three-days-${i+1}">
				<div class="tdb-inner">
					<div class="tdb-top">
						${threeDaysTabs[i][1].dayOfMonth} ${threeDaysTabs[i][1].monthOfYear}, ${threeDaysTabs[i][1].dayOfWeek}
					</div>
					<div class="tdb-bot">
						<div class="tdb-min">

							<c:if test = "${typeTemp == 'C'}">
								${content.min} <em>${threeDaysTabs[i][1].mintempC} <span>${typeTemp}</span></em>
							</c:if>
							<c:if test = "${typeTemp == 'F'}">
								${content.min} <em>${threeDaysTabs[i][1].mintempF} <span>${typeTemp}</span></em>
							</c:if>
						</div>


						<div class="tdb-max">
							<c:if test = "${typeTemp == 'C'}">
								${content.max} <em>${threeDaysTabs[i][1].maxtempC} <span>${typeTemp}</span></em>
							</c:if>
							<c:if test = "${typeTemp == 'F'}">
								${content.max} <em>${threeDaysTabs[i][1].maxtempF} <span>${typeTemp}</span></em>
							</c:if>
						</div>
						<!-- <img src="images/svg-icons/cloud-sm.svg" alt=""> -->
							<%--${threeDaysTabs[i][1].weatherCode}--%>
						<img class="green_tabs_header_icon" ng-src="svg/wicons_svg/${threeDaysTabs[i][1].weatherCode}.svg" style="    text-align: center;    margin: auto;">
					</div>
				</div>
			</div>
		</c:forEach>

	</section><!-- end three-days -->


	<section id="popular-weather">
		<div class="container">
			<div class="weather-block-width-wrap">
				<c:forEach var = "elem" begin = "0" end = "${recentTabs.size()-1}">
				<div class="weather-block-width w${elem}">
					<div class="wb-wrap">
						<div class="wb-left">
							<img ng-src="svg/wicons_svg/${recentTabs[elem].weatherCode}.svg" style="width: 30px;">

							<c:if test = "${typeTemp == 'C'}">
								<div class="wbl-temp">${recentTabs[elem].tempC} <span>${typeTemp}</span></div>
							</c:if>
							<c:if test = "${typeTemp == 'F'}">
								<div class="wbl-temp">${recentTabs[elem].tempF}<span>${typeTemp}</span></div>
							</c:if>

						</div>
						<div class="wb-right">
							<a>
								<h3>${recentTabs[elem].city}</h3>
								<h4>${recentTabs[elem].countryName}</h4>
							</a>
						</div>
					</div>
				</div>
				</c:forEach>
			</div><!-- weather-block-width-wrap -->
		</div><!-- end container -->
	</section><!-- end popular-weather -->

	<div class="container section-margin-top">
		<div class="wrapper">
			<div class="page-content">
				<section id="temperature-map">
					<div class="temperature-heading">
						<h2>${content.temperatureMap}</h2>
						<a href="#" class="t-view-map">${content.viewMap}</a>
					</div>
					<div id="map"></div>
				</section><!-- end temperature-map -->
			</div><!-- end page-content -->
			<aside class="sidebar">
				<!-- <div class="ad300-250 top62px ">300x250</div> -->
				<img src="https://placehold.it/300x250" alt="alt" class="top62px">
				<a href="https://play.google.com/store/apps/developer?id=Oplao" class="g-play" ng-include="'templates/html/google-play.html'"></a>
			</aside>
		</div><!-- end wrapper -->
	</div><!-- end container -->


	<div class="container section-margin-top">
		<div class="wrapper">
			<div class="page-content">
				<section id="country-weather-section">
					<h2>${content.locationWeather}</h2>
					<div class="country-weather">
						<c:forEach var = "elem" begin = "0" end = "${countryWeather.size()-1}">
						<div class="weather-block">
							<div class="wb-left">
								<img ng-src="svg/wicons_svg/${countryWeather[elem].weatherCode}.svg" style="    width: 45px;    text-align: center;    margin: auto;">
								<c:if test = "${typeTemp == 'C'}">
									<div class="wbl-temp">${countryWeather[elem].temp_C} <span>${typeTemp}</span></div>
								</c:if>
								<c:if test = "${typeTemp == 'F'}">
									<div class="wbl-temp">${countryWeather[elem].temp_F}<span>${typeTemp}</span></div>
								</c:if>
							</div>
							<div class="wb-right">
								<a href="#"><h3>${countryWeather[elem].name}</h3></a>
							</div>
						</div>
						</c:forEach>
					</div>
				</section><!-- end country-weather-section -->
				<section id="holiday-weather-section">
					<h3>${content.holidayWeather}</h3>
					<div class="holiday-weather">
						<c:forEach var = "elem" begin = "0" end = "${holidayWeather.size()-1}">
						<div class="weather-block">
							<div class="wb-left">
								<img ng-src="svg/wicons_svg/${holidayWeather[elem].weatherCode}.svg" style="    width: 45px;    text-align: center;    margin: auto;">
								<c:if test = "${typeTemp == 'C'}">
									<div class="wbl-temp">${holidayWeather[elem].temp_C} <span>${typeTemp}</span></div>
								</c:if>
								<c:if test = "${typeTemp == 'F'}">
									<div class="wbl-temp">${holidayWeather[elem].temp_F}<span>${typeTemp}</span></div>
								</c:if>
							</div>
							<div class="wb-right">
								<a href="#">
									<h3>${holidayWeather[elem].name}</h3>
									<h4>${holidayWeather[elem].countryName}</h4>
								</a>
							</div>
						</div>
						</c:forEach>
					</div>
				</section><!-- end holiday-weather-section -->
				<section id="top-holiday-dest">
						<h4>${content.topHolidayDestinations}</h4>
						<ul>
							<c:forEach var = "elem" begin = "0" end = "${holidayDestinations.size()-1}">
								<li><a href="#">${holidayDestinations[elem]}<span>/</span></a></li>
							</c:forEach>
						</ul>
				</section><!-- end top-holiday-dest -->
			</div><!-- end page-content -->
			<aside class="sidebar">
				<!-- <div class="ad300-600">300x600</div> -->
				<img src="https://placehold.it/300x600" alt="alt" class="mob-hide">
			</aside>
		</div><!-- end wrapper -->
	</div><!-- end container -->


	<div class="main-bot-banner">
		AD 729x90
	</div>

	<section id="weather-extensions">
		<div class="container">
			<h2 ng-bind="pageContent.oplaoWeatherExtension"></h2>
		</div>
		<div class="ext-row">
			<a target="_blank" href="https://addons.mozilla.org/en-US/firefox/addon/oplao-weather/" class="ext-block">
				<img src="images/svg-icons/firefox.svg" alt="img">
				<h3>Firefox</h3>
			</a>
			<a target="_blank"  href="https://chrome.google.com/webstore/detail/oplao-weather/jbjeihiakbjchgaehgcilindlghfffnb" class="ext-block">
				<img src="images/svg-icons/crome.svg" alt="img">
				<h3>Chrome</h3>
			</a>
			<a target="_blank"  href="https://addons.opera.com/en/extensions/details/oplao-weather/" class="ext-block">
				<img src="images/svg-icons/opera.svg" alt="img">
				<h3>Opera</h3>
			</a>
			<a target="_blank"  href="https://addons.opera.com/en/extensions/details/oplao-weather/" class="ext-block">
				<img src="images/svg-icons/yandex.svg" alt="img">
				<h3>yandex</h3>
			</a>
		</div>
	</section><!-- end "weather-extensions -->

	<footer>
		<div class="footer-top">
			<div class="container">
				<div class="main-menu-inner">
					<nav class="main-menu-block">
						<h3 ng-bind="pageContent.weather"></h3>
						<ul>
							<li><a href="/${currentCountryCode}/weather/3/${selectedCity}" ng-bind="pageContent.threeDaysWeatherForecast"></a></li>
							<li><a href="/${currentCountryCode}/weather/5/${selectedCity}" ng-bind="pageContent.fiveDaysWeatherForecast"></a></li>
							<li><a href="/${currentCountryCode}/weather/7/${selectedCity}" ng-bind="pageContent.sevenDaysWeatherForecast"></a></li>
							<li><a href="/${currentCountryCode}/weather/14/${selectedCity}" ng-bind="pageContent.fourteenDaysWeatherForecast"></a></li>
							<li><a href="/${currentCountryCode}/weather/hour-by-hour3/${selectedCity}" ng-bind="pageContent.hourlyWeather"></a></li>
							<li><a href="/${currentCountryCode}/weather/map/${selectedCity}" ng-bind="pageContent.weatherMap"></a></li>
							<li><a href="/${currentCountryCode}/weather/history3/${selectedCity}" ng-bind="pageContent.weatherHistory"></a></li>
						</ul>
					</nav>

					<nav class="main-menu-block">
						<h3 ng-bind="pageContent.applications"></h3>
						<ul>
							<li><a href="https://play.google.com/store/apps/developer?id=Oplao" ng-bind="pageContent.androidApps"></a></li>
							<li><a href="https://chrome.google.com/webstore/detail/oplao-weather/jbjeihiakbjchgaehgcilindlghfffnb" ng-bind="pageContent.googleChromeWeatherExtension"></a></li>
							<li><a href="https://addons.mozilla.org/en-US/firefox/addon/oplao-weather/" ng-bind="pageContent.firefoxWeatherExtension"></a></li>
							<li><a href="https://addons.opera.com/en/extensions/details/oplao-weather/" ng-bind="pageContent.operaWeatherExtension"></a></li>
							<li><a href="/${currentCountryCode}/weather/widgets" ng-bind="pageContent.widgets"></a></li>
						</ul>
					</nav>

					<nav class="main-menu-block">
						<h3 ng-bind="pageContent.information"></h3>
						<ul>
							<!--<li><a href="/${currentCountryCode}/about" ng-bind="pageContent.about"></a></li>-->
							<li><a href="https://blog.oplao.com/" ng-bind="pageContent.blog"></a></li>
						</ul>
					</nav>
				</div>

				<nav class="footer-soc">
					<a class="doorbell-button"  onclick="showDoorbellModal();"><img src="images/svg-icons/feedback.svg" alt=""><em ng-bind="pageContent.feedback"></em></a>
					<a target="_blank" href="https://vk.com/oplao"><i class="icon vk" style="border: none;"></i></a>
					<a target="_blank" href="https://www.facebook.com/Oplao"><i class="icon facebook"></i></a>
					<a target="_blank" href="https://twitter.com/Oplaoweather"><i class="icon twitter"></i></a>
					<a target="_blank" href="https://plus.google.com/u/0/b/111062725555627000569/+Oplaoweather/posts"><i class="icon google"></i></a>
					<a target="_blank" href="https://www.youtube.com/channel/UC1rtXk9LSRD-pJb253pN0lg"><i class="icon youtube"></i></a>
				</nav>
			</div>
		</div><!-- end footer-top -->
		<div class="footer-bot">
			2016. Oplao, All right reserved
		</div>
	</footer>


	<!-- Popup -->
	<div id="alert-popup" class="alert-popup mfp-with-anim mfp-hide">
		<div class="alert-popup-top">
			<div class="apt-left">Alerts</div>
		</div>
	</div>
	<!-- Popup -->
	<div id="settings-popup" class="settings-popup mfp-with-anim mfp-hide">
		<div class="settings-popup-top">
			<div class="spt-left" ng-bind="pageContent.settings"></div>
		</div>
		<div class="settings-popup-md">
			<div class="popup-lang-wrap">
				<h2 ng-bind="pageContent.language"></h2>
				<ul class="popup-lang">
					<li><a ng-click="selectLanguage('en')" ng-class="{active:currentCountryCode == 'en'}">En</a></li>
					<li><a ng-click="selectLanguage('ru')" ng-class="{active:currentCountryCode == 'ru'}">Ru</a></li>
					<li><a ng-click="selectLanguage('de')" ng-class="{active:currentCountryCode == 'de'}">De</a></li>
					<li><a ng-click="selectLanguage('fr')" ng-class="{active:currentCountryCode == 'fr'}">Fr</a></li>
					<li><a ng-click="selectLanguage('it')" ng-class="{active:currentCountryCode == 'it'}">It</a></li>
					<li><a ng-click="selectLanguage('ua')" ng-class="{active:currentCountryCode == 'ua'}">Ua</a></li>
					<li><a ng-click="selectLanguage('by')" ng-class="{active:currentCountryCode == 'by'}">By</a></li>
				</ul>
			</div>

			<div class="set-popup-row">
				<div class="spr-block popup-units">
					<h2 ng-bind="pageContent.unitsPreference"></h2>
					<a href="#" class="spr-item" ng-class="{active:local.typeTemp == 'C'}" ng-click="local.typeTemp = 'C'; updateTemp(local.typeTemp);">°C</a>
					<a href="#" class="spr-item" ng-class="{active:local.typeTemp == 'F'}" ng-click="local.typeTemp = 'F'; updateTemp(local.typeTemp);">°F</a>
				</div>
				<div class="spr-block popup-time">
					<h2 ng-bind="pageContent.timeFormat"></h2>
					<a href="#" class="spr-item" ng-class="{active:local.typeTime == 12}" ng-click="local.typeTime = 12; updateTime(local.typeTime);" ng-bind="pageContent.twelveHours"></a>
					<a href="#" class="spr-item" ng-class="{active:local.typeTime == 24}" ng-click="local.typeTime = 24; updateTime(local.typeTime);" ng-bind="pageContent.twentyFourHours"></a>
				</div>
			</div>
		</div>

		<div class="set-pop-bot">
			<div class="alerts_block">
				<div class="alerts_text" ng-bind="pageContent.alerts"></div>
				<div class="onoffswitch">
					<input type="checkbox" name="onoffswitch" class="onoffswitch-checkbox" id="myonoffswitch" checked>
					<label class="onoffswitch-label" for="myonoffswitch">
						<span class="onoffswitch-inner"></span>
						<span class="onoffswitch-switch"></span>
					</label>
				</div>
			</div>
		</div>
	</div>

	<script src="../../js/jquery.min.js"></script>
	<script src="../../js/charts.js"></script>
	<script src="../../js/charts_init.js"></script>
	<script src="../../js/popup.js"></script>
	<script src="../../js/scripts.js"></script>
	<script src="../../js/slick.min.js"></script>
	<script src="../../js/jquery-ui.min.js"></script>
	<script src="../../js/jquery.formstyler.min.js"></script>
	<script src="../../assets/plugins/angularJS/angular.min.js"></script>
	<script src="../../assets/plugins/angularJS/angular-ui-router.min.js"></script>
	<script src="../../assets/plugins/angularJS/ocLazyLoad.min.js"></script>
	<script src="../../assets/plugins/other/angular-cookies.min.js"></script>
	<script src="../../app.js?n=1"></script>
	<script type="text/javascript" src="../../assets/js/map.js"></script>
	<script type="text/javascript">
        window.doorbellOptions = {
            hideButton: true,
            appKey: 'BGzSckphD3XdH71dyC32xiriWxSN0BfLyPnVJUz8VWTn6UgnCznMYoZnQXZq0tuS',
            onInitialized: function() {
            }
        };
        (function(w, d, t) {
            var hasLoaded = false;
            function l() { if (hasLoaded) { return; }
                hasLoaded = true;

                window.doorbellOptions.windowLoaded = true;
                var g = d.createElement(t);
                g.id = 'doorbellScript';
                g.type = 'text/javascript';
                g.async = true;
                g.src = 'https://embed.doorbell.io/button/1291?t='+(new Date().getTime());
                (d.getElementsByClassName('head')[0]||d.getElementsByTagName('body')[0]).appendChild(g); }
            if (w.attachEvent) { w.attachEvent('onload', l); } else if (w.addEventListener) { w.addEventListener('load', l, false); } else { l(); }
            if (d.readyState == 'complete') { l(); }
        }(window, document, 'script'));
        function showDoorbellModal() {
            doorbell.show(); // The doorbell object gets created by the doorbell.js script
        }

	</script>
</body>
</html>