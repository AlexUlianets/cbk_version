<section id="weekly-report">
    <h2>${content.aboveTable}</h2>
<div class="tb-tabs-header tb-slider-wrap">
    <div class="tb-tabs-ul-wrap">
        <ul class="tb-slider tabs tb-tabs tb-tabs-full">
            <c:forEach var = "i" begin = "0" end = "${dynamicTableData.size()-1}">
            <li class="active tabclass1" rel="tab1" ng-click="selectTab(${i})">
                <div class="wrn-block">
                    <div class="wrn-top">
                        <div class="wrn-today">${content.today}</div>
                        ${dynamicTableData[i][1].dayOfMonth} ${dynamicTableData[i][1].monthOfYear}, ${dynamicTableData[i][1].dayOfWeek}
                    </div>
                    <div class="wrn-bot">
                        <div class="wrn-img">
                            <img src="svg/wicons_svg/${dynamicTableData[i][1].weatherCode}.svg" style="    width: 45px;    text-align: center;    margin: auto;">
                        </div>
                        <div class="wrn-temp">
                            <span class="min-temperature">
                                <c:if test = "${typeTemp == 'C'}">
                                    ${dynamicTableData[i][1].mintempC} ${typeTemp}
                                </c:if>
                                <c:if test = "${typeTemp == 'F'}">
                                    ${dynamicTableData[i][1].mintempF} ${typeTemp}
                                </c:if>
                            </span>
                            <span class="max-temperature">
                                     <c:if test = "${typeTemp == 'C'}">
                                         ${dynamicTableData[i][1].maxtempC} ${typeTemp}
                                     </c:if>
                                <c:if test = "${typeTemp == 'F'}">
                                    ${dynamicTableData[i][1].maxtempF} ${typeTemp}
                                </c:if>

                            </span>
                        </div>
                    </div>
                </div>
            </li>
            </c:forEach>
        </ul>

    </div>

    <div class="tab_container tb-container">
        <div id="tab1" class="tab_content">
            <div class="tb-wrapper">
                <div class="wather-table-wrap">
                    <div class="weather-tb 3days-tb no-date">
                        <div class="tb-head">
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
                        <div class="tb-contant">
                            <div class="tb-item">
                                <c:set var = "data" value = "${dynamicTableData.get(0).get(0)}"/>
                                <c:forEach var = "item" begin = "0" end = "${data.size()-1}">
                                    <div class="tb-row">
                                    <div class="tb-time">
                                        <div class="tb-text">${data[item].time}</div>
                                    </div>
                                    <div class="tb-weather">
                                        <img src="svg/wicons_svg/${data[item].weatherCode}.svg" style="    width: 45px;    text-align: center;    margin: auto;">
                                    </div>
                                    <div class="tb-temperature">
                                        <div class="tb-text">
                                            <c:if test = "${typeTemp == 'C'}">
                                                ${data[item].tempC} ${typeTemp}
                                            </c:if>
                                            <c:if test = "${typeTemp == 'F'}">
                                                ${data[item].tempF} ${typeTemp}
                                            </c:if>
                                        </div>
                                    </div>
                                    <div class="tb-feels-like">
                                        <div class="tb-text">
                                            <c:if test = "${typeTemp == 'C'}">
                                                ${data[item].feelsLikeC} ${typeTemp}
                                            </c:if>
                                            <c:if test = "${typeTemp == 'F'}">
                                                ${data[item].feelsLikeF} ${typeTemp}
                                            </c:if>

                                        </div>
                                    </div>
                                    <div class="tb-precip-chance">
                                        <div class="tb-text">${data[item].precipChance}%</div>
                                    </div>
                                    <div class="tb-precip">
                                        <div class="tb-count">
                                            <c:if test = "${typeTemp == 'C'}">
                                                ${data[item].precipMM}
                                            </c:if>
                                            <c:if test = "${typeTemp == 'F'}">
                                                ${data[item].precipInch}
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
                                        <i class="sprite-wind-tb" style="-ms-transform: rotate(${data[item].windDegree} + 'deg');    -webkit-transform: rotate(${data[item].windDegree } + 'deg');    transform: rotate(${data[item].windDegree} + 'deg');"></i>
                                        <div class="tb-count" style="color:${data[item].windspeedColor}; font-weight:${data[item].boldSpeed}">

                                            <c:if test = "${typeTemp == 'C'}">
                                                ${data[item].windMs} ${content.ms}
                                            </c:if>
                                            <c:if test = "${typeTemp == 'F'}">
                                                ${data[item].windMph} ${content.mph}
                                            </c:if>

                                        </div>
                                        <div class="tb-meter">${data[item].winddir}</div>
                                    </div>
                                    <div class="tb-gust">
                                        <div class="tb-count" style="color:${data[item].windgustColor}; font-weight:${data[item].boldGust}">
                                            <c:if test = "${typeTemp == 'C'}">
                                                ${data[item].gustMs}
                                            </c:if>
                                            <c:if test = "${typeTemp == 'F'}">
                                                ${data[item].gustMph}
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
                                                ${data[item].pressurehPa}
                                            </c:if>
                                            <c:if test = "${typeTemp == 'F'}">
                                                ${data[item].pressureInch}
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
    <div class="tb-header-more-info">
        <div class="icon">icon</div>
    </div>
</div>
</section>