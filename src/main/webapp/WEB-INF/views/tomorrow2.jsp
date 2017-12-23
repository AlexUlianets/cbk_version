<%@ include file="header2.jsp"%>
<div>
<div class="container section-margin-top" >
		<div class="wrapper">
			<div class="page-content">
				<section class="tomorrow tomorrow-list">
					<h2 class="section-title">${content.aboveTable}</h2>
					<div class="tomorrow-item">
						<div class="tmr-left-info">
							<div class="tmr-left-block">
								<div class="tmr-sunrise">
									<div class="tmr-icon">
										<img src="img/sunrise.svg" alt="img">
									</div>
									<div class="tmr-text">
										<div class="grey">${content.sunrise}</div>
										<div class="count" ng-bind="getTime('${dynamicTableData[0][1].sunrise}')"></div>
									</div>
								</div>
								<div class="tmr-sunset">
									<div class="tmr-icon">
										<img src="img/sunset.svg" alt="img"></img>
									</div>
									<div class="tmr-text">
										<div class="grey">${content.sunset}</div>

										<div class="count" ng-bind="getTime('${dynamicTableData[0][1].sunset}')"></div>
									</div>
								</div>
							</div>
							<div class="tmr-left-block">
								<div class="tmr-min">
									<div class="tmr-icon">
										<img src="img/min_temp.svg" alt="img"></img>
									</div>
									<div class="tmr-text">
										<div class="grey">${content.min}</div>
										<div class="count">
											<c:if test="${typeTemp == 'C'}">
												${dynamicTableData[0][1].mintempC}
											</c:if>
											<c:if test="${typeTemp == 'F'}">
												${dynamicTableData[0][1].mintempF}
											</c:if>
										</div>
									</div>
								</div>
								<div class="tmr-max">
									<div class="tmr-icon">
										<img src="img/max_temp.svg" alt="img"></img>
									</div>
									<div class="tmr-text">
										<div class="grey" ng-bind="pageContent.max"></div>
										<div class="count">
											<c:if test="${typeTemp == 'C'}">
												${dynamicTableData[0][1].maxtempC}
											</c:if>
											<c:if test="${typeTemp == 'F'}">
												${dynamicTableData[0][1].maxtempF}
											</c:if>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="tmr-main-info">
							<div class="tomorrow-date">
								${dynamicTableData[0][1].dayOfMonth} ${dynamicTableData[0][1].monthOfYear}, ${dynamicTableData[0][1].year}, ${dynamicTableData[0][1].dayOfWeek} (${content.tomorrow.toUpperCase()})
							</div>
							<div class="tb-wrapper">
								<div class="wather-table-wrap">
									<div class="weather-tb 3days-tb no-date">
										<div class="tb-head">
											<div class="tb-time">
												<div class="tb-head-icon">
													<img src="img/time.svg" alt="" height="25"></img>
												</div>
												<div class="tb-head-title">${content.time}</div>
											</div>
											<div class="tb-weather">
												<div class="tb-head-icon">
													<img src="img/weath.svg" alt="" height="25"></img>
												</div>
												<div class="tb-head-title">${content.weather}</div>
											</div>
											<div class="tb-temperature">
												<div class="tb-head-icon">
													<img src="img/temperature.svg" alt="" height="25"></img>
												</div>
												<div class="tb-head-title">${content.temperature}</div>
											</div>
											<div class="tb-feels-like">
												<div class="tb-head-icon">
													<img src="img/feels_like.svg" alt="" height="25"></img>
												</div>
												<div class="tb-head-title">${content.feelsLike}</div>
											</div>
											<div class="tb-precip-chance">
												<div class="tb-head-icon">
													<img src="img/precip_chance.svg" alt="" height="25"></img>
												</div>
												<div class="tb-head-title">${content.precipChance}</div>
											</div>
											<div class="tb-precip">
												<div class="tb-head-icon">
													<img src="img/precip.svg" alt="" height="25"></img>
												</div>
												<div class="tb-head-title">${content.precip}</div>
											</div>
											<div class="tb-wind">
												<div class="tb-head-icon">
													<img src="img/wind.svg" alt="" height="25"></img>
												</div>
												<div class="tb-head-title">${content.wind}</div>
											</div>
											<div class="tb-gust">
												<div class="tb-head-icon">
													<img src="img/gust.svg" alt="" height="25"></img>
												</div>
												<div class="tb-head-title">${content.gust}</div>
											</div>
											<div class="tb-cloud">
												<div class="tb-head-icon">
													<img src="img/cloud.svg" alt="" height="25"></img>
												</div>
												<div class="tb-head-title">${content.cloud}</div>
											</div>
											<div class="tb-humidity">
												<div class="tb-head-icon">
													<img src="img/humidity_black.svg" alt="" height="25"></img>
												</div>
												<div class="tb-head-title">${content.humidity}</div>
											</div>
											<div class="tb-pressure">
												<div class="tb-head-icon">
													<img src="img/pressure.svg" alt="" height="25"></img>
												</div>
												<div class="tb-head-title">${content.pressure}</div>
											</div>
										</div>
										<div class="tb-contant">
											<div class="tb-item">
												<c:forEach var = "i" begin = "0" end = "${dynamicTableData[0][0].size()-1}">
													<c:set var = "item" value = "${dynamicTableData[0][0][i]}" />
													<div class="tb-row" >
														<div class="tb-time">
															<div class="tb-text" ng-bind="getTime('${item.time}')"></div>
														</div>
														<div class="tb-weather">
															<img src="svg/wicons_svg/${item.weatherCode}.svg" style="    width: 45px;    text-align: center;    margin: auto;">

														</div>
														<div class="tb-temperature">
															<div class="tb-text">

																<c:if test = "${typeTemp == 'C'}">
																	${item.tempC} ${typeTemp}
																</c:if>
																<c:if test = "${typeTemp == 'F'}">
																	${item.tempF} ${typeTemp}
																</c:if>
															</div>
														</div>
														<div class="tb-feels-like">
															<div class="tb-text">
																<c:if test = "${typeTemp == 'C'}">
																	${item.feelsLikeC} ${typeTemp}
																</c:if>
																<c:if test = "${typeTemp == 'F'}">
																	${item.feelsLikeF} ${typeTemp}
																</c:if>
															</div>
														</div>
														<div class="tb-precip-chance">
															<div class="tb-text">${item.precipChance}%</div>
														</div>
														<div class="tb-precip">
															<div class="tb-count">
																<c:if test = "${typeTemp == 'C'}">
																	${item.precipMM}
																</c:if>
																<c:if test = "${typeTemp == 'F'}">
																	${item.precipInch}
																</c:if>
															</div>
															<div class="tb-meter">
																<c:if test = "${typeTemp == 'C'}">
																	${item.inDist}
																</c:if>
																<c:if test = "${typeTemp == 'F'}">
																	${item.inDist}
																</c:if>
															</div>
														</div>
														<div class="tb-wind">
																<%--<i class="sprite-wind-tb" style="-ms-transform: rotate(${item.windDegree + 'deg'});    -webkit-transform: rotate(${item.windDegree + 'deg'});    transform: rotate(${item.windDegree + 'deg'});"></i>--%>
															<div class="tb-count" style="color:${item.windspeedColor}; font-weight:${item.boldSpeed}">
																<c:if test = "${typeTemp == 'C'}">
																	${item.windMs} ${content.ms}
																</c:if>
																<c:if test = "${typeTemp == 'F'}">
																	${item.windMph} ${content.mph}
																</c:if>
															</div>
															<div class="tb-meter">${item.winddir}</div>
														</div>
														<div class="tb-gust">
															<div class="tb-count" style="color:${item.windgustColor}; font-weight:${item.boldGust}">
																<c:if test = "${typeTemp == 'C'}">
																	${item.gustMs}
																</c:if>
																<c:if test = "${typeTemp == 'F'}">
																	${item.gustMph}
																</c:if>
															</div>
															<div class="tb-meter">
																<c:if test = "${typeTemp == 'C'}">
																	${content.ms}
																</c:if>
																<c:if test = "${typeTemp == 'F'}">
																	${content.mph}
																</c:if>

															</div>
														</div>
														<div class="tb-cloud">
															<div class="tb-text">${item.cloudCover}%</div>
														</div>
														<div class="tb-humidity">
															<div class="tb-text">${item.humidity}%</div>
														</div>
														<div class="tb-pressure">
															<div class="tb-count">
																<c:if test = "${typeTemp == 'C'}">
																	${item.pressurehPa}
																</c:if>
																<c:if test = "${typeTemp == 'F'}">
																	${item.pressureInch}
																</c:if>
															</div>
															<div class="tb-meter">
																<c:if test = "${typeTemp == 'C'}">
																	${content.hPa}
																</c:if>
																<c:if test = "${typeTemp == 'F'}">
																	${content.in}
																</c:if>
															</div>
														</div>
													</div>
												</c:forEach>
											</div>
										</div>
									</div>
								</div>
								<div class="tb-more-info more-col-4">
									<div class="text">
										<span>Swipe</span>
										<span>FOR</span>
										<span>MORE</span>
										<span>INFO</span>
									</div>
								</div>
							</div>
						</div>

					</div>
					<div class="tomorrow-item">
						<div class="tmr-left-info">
							<div class="tmr-left-block">
								<div class="tmr-sunrise">
									<div class="tmr-icon">
										<img src="img/sunrise.svg" alt="img">
									</div>
									<div class="tmr-text">
										<div class="grey">${content.sunrise}</div>
										<div class="count" ng-bind="getTime('${dynamicTableData[1][1].sunrise}')"></div>
									</div>
								</div>
								<div class="tmr-sunset">
									<div class="tmr-icon">
										<img src="img/sunset.svg" alt="img"></img>
									</div>
									<div class="tmr-text">
										<div class="grey">${content.sunset}</div>

										<div class="count" ng-bind="getTime('${dynamicTableData[1][1].sunset}')"></div>
									</div>
								</div>
							</div>
							<div class="tmr-left-block">
								<div class="tmr-min">
									<div class="tmr-icon">
										<img src="img/min_temp.svg" alt="img"></img>
									</div>
									<div class="tmr-text">
										<div class="grey">${content.min}</div>
										<div class="count">
											<c:if test="${typeTemp == 'C'}">
												${dynamicTableData[1][1].mintempC}
											</c:if>
											<c:if test="${typeTemp == 'F'}">
												${dynamicTableData[1][1].mintempF}
											</c:if>
										</div>
									</div>
								</div>
								<div class="tmr-max">
									<div class="tmr-icon">
										<img src="img/max_temp.svg" alt="img"></img>
									</div>
									<div class="tmr-text">
										<div class="grey">${content.max}</div>
										<div class="count">
											<c:if test="${typeTemp == 'C'}">
												${dynamicTableData[1][1].maxtempC}
											</c:if>
											<c:if test="${typeTemp == 'F'}">
												${dynamicTableData[1][1].maxtempF}
											</c:if>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="tmr-main-info">
							<div class="tomorrow-date">
								${dynamicTableData[1][1].dayOfMonth} ${dynamicTableData[1][1].monthOfYear}, ${dynamicTableData[1][1].year}, ${dynamicTableData[1][1].dayOfWeek}
							</div>
							<div class="tb-wrapper">
								<div class="wather-table-wrap">
									<div class="weather-tb 3days-tb no-date">
										<div class="tb-head">
											<div class="tb-time">
												<div class="tb-head-icon">
													<img src="img/time.svg" alt="" height="25"></img>
												</div>
												<div class="tb-head-title">${content.time}</div>
											</div>
											<div class="tb-weather">
												<div class="tb-head-icon">
													<img src="img/weath.svg" alt="" height="25"></img>
												</div>
												<div class="tb-head-title">${content.weather}</div>
											</div>
											<div class="tb-temperature">
												<div class="tb-head-icon">
													<img src="img/temperature.svg" alt="" height="25"></img>
												</div>
												<div class="tb-head-title">${content.temperature}</div>
											</div>
											<div class="tb-feels-like">
												<div class="tb-head-icon">
													<img src="img/feels_like.svg" alt="" height="25"></img>
												</div>
												<div class="tb-head-title">${content.feelsLike}</div>
											</div>
											<div class="tb-precip-chance">
												<div class="tb-head-icon">
													<img src="img/precip_chance.svg" alt="" height="25"></img>
												</div>
												<div class="tb-head-title">${content.precipChance}</div>
											</div>
											<div class="tb-precip">
												<div class="tb-head-icon">
													<img src="img/precip.svg" alt="" height="25"></img>
												</div>
												<div class="tb-head-title">${content.precip}</div>
											</div>
											<div class="tb-wind">
												<div class="tb-head-icon">
													<img src="img/wind.svg" alt="" height="25"></img>
												</div>
												<div class="tb-head-title">${content.wind}</div>
											</div>
											<div class="tb-gust">
												<div class="tb-head-icon">
													<img src="img/gust.svg" alt="" height="25"></img>
												</div>
												<div class="tb-head-title">${content.gust}</div>
											</div>
											<div class="tb-cloud">
												<div class="tb-head-icon">
													<img src="img/cloud.svg" alt="" height="25"></img>
												</div>
												<div class="tb-head-title">${content.cloud}</div>
											</div>
											<div class="tb-humidity">
												<div class="tb-head-icon">
													<img src="img/humidity_black.svg" alt="" height="25"></img>
												</div>
												<div class="tb-head-title">${content.humidity}</div>
											</div>
											<div class="tb-pressure">
												<div class="tb-head-icon">
													<img src="img/pressure.svg" alt="" height="25"></img>
												</div>
												<div class="tb-head-title">${content.pressure}</div>
											</div>
										</div>
										<div class="tb-contant">
											<div class="tb-item">
												<c:forEach var = "i" begin = "0" end = "${dynamicTableData[1][0].size()-1}">
													<c:set var = "item" value = "${dynamicTableData[1][0][i]}" />
													<div class="tb-row" >
														<div class="tb-time">
															<div class="tb-text" ng-bind="getTime('${item.time}')"></div>
														</div>
														<div class="tb-weather">
															<img src="svg/wicons_svg/${item.weatherCode}.svg" style="    width: 45px;    text-align: center;    margin: auto;">

														</div>
														<div class="tb-temperature">
															<div class="tb-text">

																<c:if test = "${typeTemp == 'C'}">
																	${item.tempC} ${typeTemp}
																</c:if>
																<c:if test = "${typeTemp == 'F'}">
																	${item.tempF} ${typeTemp}
																</c:if>
															</div>
														</div>
														<div class="tb-feels-like">
															<div class="tb-text">
																<c:if test = "${typeTemp == 'C'}">
																	${item.feelsLikeC} ${typeTemp}
																</c:if>
																<c:if test = "${typeTemp == 'F'}">
																	${item.feelsLikeF} ${typeTemp}
																</c:if>
															</div>
														</div>
														<div class="tb-precip-chance">
															<div class="tb-text">${item.precipChance}%</div>
														</div>
														<div class="tb-precip">
															<div class="tb-count">
																<c:if test = "${typeTemp == 'C'}">
																	${item.precipMM}
																</c:if>
																<c:if test = "${typeTemp == 'F'}">
																	${item.precipInch}
																</c:if>
															</div>
															<div class="tb-meter">
																<c:if test = "${typeTemp == 'C'}">
																	${item.inDist}
																</c:if>
																<c:if test = "${typeTemp == 'F'}">
																	${item.inDist}
																</c:if>
															</div>
														</div>
														<div class="tb-wind">
																<%--<i class="sprite-wind-tb" style="-ms-transform: rotate(${item.windDegree + 'deg'});    -webkit-transform: rotate(${item.windDegree + 'deg'});    transform: rotate(${item.windDegree + 'deg'});"></i>--%>
															<div class="tb-count" style="color:${item.windspeedColor}; font-weight:${item.boldSpeed}">
																<c:if test = "${typeTemp == 'C'}">
																	${item.windMs} ${content.ms}
																</c:if>
																<c:if test = "${typeTemp == 'F'}">
																	${item.windMph} ${content.mph}
																</c:if>
															</div>
															<div class="tb-meter">${item.winddir}</div>
														</div>
														<div class="tb-gust">
															<div class="tb-count" style="color:${item.windgustColor}; font-weight:${item.boldGust}">
																<c:if test = "${typeTemp == 'C'}">
																	${item.gustMs}
																</c:if>
																<c:if test = "${typeTemp == 'F'}">
																	${item.gustMph}
																</c:if>
															</div>
															<div class="tb-meter">
																<c:if test = "${typeTemp == 'C'}">
																	${content.ms}
																</c:if>
																<c:if test = "${typeTemp == 'F'}">
																	${content.mph}
																</c:if>

															</div>
														</div>
														<div class="tb-cloud">
															<div class="tb-text">${item.cloudCover}%</div>
														</div>
														<div class="tb-humidity">
															<div class="tb-text">${item.humidity}%</div>
														</div>
														<div class="tb-pressure">
															<div class="tb-count">
																<c:if test = "${typeTemp == 'C'}">
																	${item.pressurehPa}
																</c:if>
																<c:if test = "${typeTemp == 'F'}">
																	${item.pressureInch}
																</c:if>
															</div>
															<div class="tb-meter">
																<c:if test = "${typeTemp == 'C'}">
																	${content.hPa}
																</c:if>
																<c:if test = "${typeTemp == 'F'}">
																	${content.in}
																</c:if>
															</div>
														</div>
													</div>
												</c:forEach>
											</div>
										</div>
									</div>
								</div>
								<div class="tb-more-info more-col-4">
									<div class="text">
										<span>Swipe</span>
										<span>FOR</span>
										<span>MORE</span>
										<span>INFO</span>
									</div>
								</div>
							</div>
						</div>

					</div>
					<%@include file="links2.jsp"%>
				</section><!-- end tomorrow -->

				
			</div><!-- end page-content -->
			<aside class="sidebar top35px">
				<!-- OLD -->
				<!-- <div class="ad300-600 mob-hide"></div> -->
				<!-- <div class="ad300-250 top20px"></div> -->

				<!-- NEW -->
				<img src="https://placehold.it/300x600" alt="alt" class="mob-hide">
				<img src="https://placehold.it/300x250" alt="alt" class="top20px">
				<a href="https://play.google.com/store/apps/developer?id=Oplao" class="g-play" ng-include="'templates/html/google-play.html'"></a>
				<img src="https://placehold.it/300x600" alt="alt" class="mob-hide top30px">
			</aside>
		</div><!-- end wrapper -->
	</div><!-- end container -->
</div>

<%@include file="footer2.jsp"%>