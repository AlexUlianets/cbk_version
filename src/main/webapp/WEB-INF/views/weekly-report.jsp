    <section id="weekly-report">
    <h2>${content.aboveTable}</h2>

    <div class="tb-wrapper">
        <div class="wather-table-wrap">
            <div class="weather-tb 5days-tb">
                <div class="tb-head">
                    <div class="tb-date">
                        <div class="tb-head-icon">
                            <img src="img/date.svg" alt="" height="25">
                        </div>
                        <div class="tb-head-title">${content.date}</div>
                    </div>
                    <div class="tb-time">
                        <div class="tb-head-icon">
                            <img src="img/time.svg" alt="" height="25">
                        </div>
                        <div class="tb-head-title">${content.time}</div>
                    </div>
                    <div class="tb-weather">
                        <div class="tb-head-icon">
                            <img src="img/weath.svg" alt="" height="25">
                        </div>
                        <div class="tb-head-title">${content.weather}</div>
                    </div>
                    <div class="tb-temperature">
                        <div class="tb-head-icon">
                            <img src="img/temperature.svg" alt="" height="25">
                        </div>
                        <div class="tb-head-title">${content.temperature}</div>
                    </div>
                    <div class="tb-feels-like">
                        <div class="tb-head-icon">
                            <img src="img/feels_like.svg" alt="" height="25">
                        </div>
                        <div class="tb-head-title">${content.feelsLike}</div>
                    </div>
                    <div class="tb-precip-chance">
                        <div class="tb-head-icon">
                            <img src="img/precip_chance.svg" alt="" height="25">
                        </div>
                        <div class="tb-head-title">${content.precipChance}</div>
                    </div>
                    <div class="tb-precip">
                        <div class="tb-head-icon">
                            <img src="img/precip.svg" alt="" height="25">
                        </div>
                        <div class="tb-head-title">${content.precip}</div>
                    </div>
                    <div class="tb-wind">
                        <div class="tb-head-icon">
                            <img src="img/wind.svg" alt="" height="25">
                        </div>
                        <div class="tb-head-title">${content.wind}</div>
                    </div>
                    <div class="tb-gust">
                        <div class="tb-head-icon">
                            <img src="img/gust.svg" alt="" height="25">
                        </div>
                        <div class="tb-head-title">${content.gust}</div>
                    </div>
                    <div class="tb-pressure">
                        <div class="tb-head-icon">
                            <img src="img/pressure.svg" alt="" height="25">
                        </div>
                        <div class="tb-head-title">${content.pressure}</div>
                    </div>
                </div>
                <div class="tb-contant inner-html">

                    <c:forEach var = "elem" begin = "0" end = "${temperatureWeekly.size()-1}">
                        <div class="tb-item">
                        <c:forEach var = "day" begin = "0" end = "${temperatureWeekly[elem].size()-1}">
                            <c:if test="${day == 0}">
                                <c:set var = "data" value = "${temperatureWeekly[elem]['Day']}" />
                            </c:if>
                            <c:if test="${day == 1}">

                            </c:if>
                            <c:if test="${day == 2}">
                                <c:set var = "data" value = "${temperatureWeekly[elem]['wholeDay']}" />
                            </c:if>

                            <div class="tb-row">
                                <c:if test = "${day == 0}">

                                    <div class="tb-date">
                                        <c:if test="${elem == 0}">
                                            <div class="tb-today">${content.today}</div>
                                        </c:if>

                                        <div class="tb-date-day">${temperatureWeekly[elem]['wholeDay'].dayOfMonth}
                                            ${temperatureWeekly[elem]['wholeDay'].monthOfYear},
                                        </div>
                                        <div class="tb-day">${temperatureWeekly[elem]['wholeDay'].dayOfWeek}</div>
                                    </div>
                                </c:if>
                                <c:if test="${day == 1}">
                                    <div class="tb-date">
                                        <div class="tb-img">
                                            <img src="svg/wicons_svg/${temperatureWeekly[elem]['wholeDay'].weatherCode}.svg"
                                                 style="width: 42px;margin-bottom:6px">
                                        </div>
                                        <div class="tb-temp"><span class="min-temperature">
                                             <c:if test = "${typeTemp == 'C'}">
                                                 ${temperatureWeekly[elem]['wholeDay'].minTemperatureC} ${typeTemp}
                                             </c:if>
                                            <c:if test = "${typeTemp == 'F'}">
                                                ${temperatureWeekly[elem]['wholeDay'].minTemperatureF} ${typeTemp}
                                            </c:if>
                                        </span>/<span
                                                class="max-temperature">
                                                 <c:if test = "${typeTemp == 'C'}">
                                                     ${temperatureWeekly[elem]['wholeDay'].maxTemperatureC} ${typeTemp}
                                                 </c:if>
                                            <c:if test = "${typeTemp == 'F'}">
                                                ${temperatureWeekly[elem]['wholeDay'].maxTemperatureF} ${typeTemp}
                                            </c:if>
                                        </span>
                                        </div>
                                    </div>
                                </c:if>

                                <c:if test="${day!=2}">
                            <div class="tb-time">
                                <div class="tb-text">${data.time}</div>
                            </div>
                            <div class="tb-weather">
                                <img src="svg/wicons_svg/${data.weatherCode}.svg" style="    width: 45px;    text-align: center;    margin: auto;">
                            </div>
                            <div class="tb-temperature">
                                <div class="tb-text">
                                    <c:if test = "${typeTemp == 'C'}">
                                        ${data.avgTempC} ${typeTemp}
                                    </c:if>
                                    <c:if test = "${typeTemp == 'F'}">
                                        ${data.avgTempF} ${typeTemp}
                                    </c:if>

                                </div>
                            </div>
                            <div class="tb-feels-like">
                                <div class="tb-text">
                                    <c:if test = "${typeTemp == 'C'}">
                                        ${data.feelsLikeC} ${typeTemp}
                                    </c:if>
                                    <c:if test = "${typeTemp == 'F'}">
                                        ${data.feelsLikeF} ${typeTemp}
                                    </c:if>
                                </div>
                            </div>
                            <div class="tb-precip-chance">
                                <div class="tb-text">${data.precipChance}%</div>
                            </div>
                            <div class="tb-precip">
                                <div class="tb-count">
                                    <c:if test = "${typeTemp == 'C'}">
                                        ${data.precipMm}
                                    </c:if>
                                    <c:if test = "${typeTemp == 'F'}">
                                        ${data.precipIn}
                                    </c:if>
                                </div>
                                <div class="tb-meter">
                                    <c:if test = "${typeTemp == 'C'}">
                                        ${content.mmDist}
                                    </c:if>
                                    <c:if test = "${typeTemp == 'F'}">
                                        ${content.inDist}
                                    </c:if>
                                </div>
                            </div>
                            <div class="tb-wind">
                                <i class="sprite-wind-tb" style="    -ms-transform: rotate(${data.windDegree} + 'deg');    -webkit-transform: rotate(${data.windDegree} + 'deg');    transform: rotate(${data.windDegree} + 'deg');"></i>
                                <div class="tb-count" style="color:${data.windspeedColor}; font-weight:${data.boldSpeed}">

                                    <c:if test = "${typeTemp == 'C'}">
                                        ${data.windMs} ${content.ms}
                                    </c:if>
                                    <c:if test = "${typeTemp == 'F'}">
                                        ${data.windMph} ${content.mph}
                                    </c:if>
                                </div>
                                <div class="tb-meter">${data.winddir}</div>
                            </div>
                            <div class="tb-gust">
                                <div class="tb-count" style="color:${data.windgustColor}; font-weight:${data.boldGust}">
                                    <c:if test = "${typeTemp == 'C'}">
                                        ${data.gustMs}
                                    </c:if>
                                    <c:if test = "${typeTemp == 'F'}">
                                        ${data.gustMph}
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
                            <div class="tb-pressure">
                                <div class="tb-count">
                                    <c:if test = "${typeTemp == 'C'}">
                                        ${data.pressurehPa}
                                    </c:if>
                                    <c:if test = "${typeTemp == 'F'}">
                                        ${data.pressureInch}
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

                                </c:if>
                        </div>
                        </c:forEach>
                     </div>
                    </c:forEach>
                </div>
            </div>
        </div>
        <div class="tb-more-info">
            <div class="text">
                <span>Swipe</span>
                <span>FOR</span>
                <span>MORE</span>
                <span>INFO</span>
            </div>
        </div>
    </div>
    <div class="links-after" ng-include="'templates/links.html'"></div>
    <!-- .tab_container -->
</section><!-- end weeekly-report -->