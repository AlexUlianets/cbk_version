<div class="page-content">
    <section id="glance">

                    <h2>${content.weeklyWeatherSummary}</h2>
                    <div class="glance-row">
                        <div class="glance-left">${content.maxTemperature}</div>
                        <div class="glance-right">
                            <div class="glance-temp">
                                <c:if test = "${typeTemp == 'C'}">
                                  ${weekly_weather_summary.maxTempC} ${typeTemp}
                                </c:if>
                                <c:if test = "${typeTemp == 'F'}">
                                    ${weekly_weather_summary.maxTempF} ${typeTemp}
                                </c:if>
                            </div>
                            <div class="glance-date">${weekly_weather_summary.maxTempDay}</div>
                        </div>
                    </div>
                    <div class="glance-row">
                        <div class="glance-left">${content.minTemperature}</div>
                        <div class="glance-right">
                            <div class="glance-temp">

                                <c:if test = "${typeTemp == 'C'}">
                                    ${weekly_weather_summary.minTempC} ${typeTemp}
                                </c:if>
                                <c:if test = "${typeTemp == 'F'}">
                                    ${weekly_weather_summary.minTempF} ${typeTemp}
                                </c:if>
                            </div>
                            <div class="glance-date">${weekly_weather_summary.minTempDay}</div>
                        </div>
                    </div>
                    <div class="glance-row">
                        <div class="glance-left">${content.averageMaxTemperature}</div>
                        <div class="glance-right">
                            <div class="glance-temp">
                                <c:if test = "${typeTemp == 'C'}">
                                    ${weekly_weather_summary.avgMaxTempC} ${typeTemp}
                                </c:if>
                                <c:if test = "${typeTemp == 'F'}">
                                    ${weekly_weather_summary.avgMaxTempF} ${typeTemp}
                                </c:if>
                            </div>
                            <div class="glance-date">${content.next7Days}</div>
                        </div>
                    </div>
                    <div class="glance-row">
                        <div class="glance-left">${content.averageMinTemperature}</div>
                        <div class="glance-right">
                            <div class="glance-temp">
                                <c:if test = "${typeTemp == 'C'}">
                                    ${weekly_weather_summary.avgMinTempC} ${typeTemp}
                                </c:if>
                                <c:if test = "${typeTemp == 'F'}">
                                    ${weekly_weather_summary.avgMinTempF} ${typeTemp}
                                </c:if>
                            </div>
                            <div class="glance-date">${content.next7Days}</div>
                        </div>
                    </div>
                    <div class="glance-row">
                        <div class="glance-left">${content.totalRainfall}</div>
                        <div class="glance-right">

                            <div class="glance-temp">

                            <c:if test = "${typeTemp == 'C'}">
                                ${weekly_weather_summary.totalRainfallMM} ${content.mm}
                            </c:if>
                            <c:if test = "${typeTemp == 'F'}">
                                ${weekly_weather_summary.totalRainfallInch} ${content.in}
                            </c:if>
                            </div>
                            <div class="glance-date">${content.next7Days}</div>
                        </div>
                    </div>
                    <div class="glance-row">
                        <div class="glance-left">${content.windiest}</div>
                        <div class="glance-right">
                            <div class="glance-temp">
                                <c:if test = "${typeTemp == 'C'}">
                                    ${weekly_weather_summary.windiestMS} ${content.ms}
                                </c:if>
                                <c:if test = "${typeTemp == 'F'}">
                                    ${weekly_weather_summary.windiestMiles} ${content.mph}
                                </c:if>
                            </div>
                            <div class="glance-date">${weekly_weather_summary.windiestDay}</div>
                        </div>
                    </div>


                </section><!-- end glance -->
</div>