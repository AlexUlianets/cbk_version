<%@ include file="header2.jsp"%>

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

<%@include file="footer2.jsp"%>