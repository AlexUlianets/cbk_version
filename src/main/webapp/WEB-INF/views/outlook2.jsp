<%@ include file="header2.jsp"%>

<div ng-controller="outlookCtrl">
    <div class="container section-margin-top">
        <div class="wrapper">
            <div class="page-content">


                <c:choose>
                    <c:when test="${slavTable}">
                    <section id="weekly-repor">
                        <%@ include file="weekly-report-slav.jsp" %>
                    </section><!-- end weeekly-report -->
                    </c:when>
                    <c:otherwise>
                        <section id="weekly-report" >
                            <%@ include file="weekly-report.jsp" %>
                        </section>
                    </c:otherwise>
                </c:choose>

            </div><!-- end page-content -->
            <aside class="sidebar" style="padding-top: 35px">
                <!-- <div class="ad300-520 mob-hide"></div> -->
                <img src="https://placehold.it/300x600" alt="alt" class="mob-hide">
                <img src="https://placehold.it/300x250" alt="alt" class="top20px">
            </aside>
        </div><!-- end wrapper -->
    </div><!-- end container -->

    <div ng-include="'templates/graphic.html'"></div>

    <div class="container section-margin-top">
        <div class="wrapper">
            <div ng-include="'templates/wth_glance.html'" class="page-content"></div>

            <aside class="sidebar top35px">
                <!-- OLD -->
                <!-- <div class="ad300-600 mob-hide"></div> -->
                <!-- <div class="ad300-250 top20px"></div> -->

                <!-- NEW -->
                <img src="https://placehold.it/300x600" alt="alt" class="mob-hide">
                <!--<img src="https://placehold.it/300x250" alt="alt" class="top20px">-->
            </aside>
        </div><!-- end wrapper -->
    </div><!-- end container -->

    <div class="container section-margin-top">
        <div class="wrapper">
            <div class="page-content" ng-include="'templates/astronomy-sect.html'">

            </div>
            <aside href="#" class="sidebar">
                <div>
                    <a href="https://play.google.com/store/apps/developer?id=Oplao" class="g-play" ng-include="'templates/html/google-play.html'"></a>
                </div>
            </aside>
        </div>
    </div><!-- end container -->

    <section id="climate-in"  style="position: relative;">
        <div class="container" style="margin-bottom: 4%">
            <h2 style="margin-bottom: 4%" ng-bind="pageContent.climateIn"></h2>

            <div class="climate-dropdown-wrap">
                <div class="climate-dropdown-top" ng-click="activeTabMobile()">{{climate.month}}</div>
                <ul class="climate-dropdown-bot">
                    <li ng-repeat="month in get_year_summary.data track by $index"><a ng-click="getActiveClimate($index)">{{month.fullMonthName}}</a></li>
                </ul>
            </div>
           <div class="cit-block c-t-max">
                    <span></span>
                    <i></i>
                    <em>T° {{pageContent.max}}</em>
                    <b>{{local.typeTemp=='C'?climate.tempMaxC:climate.tempMaxF}}°{{local.typeTemp}}</b>
                </div>
                <div class="cit-block c-t-min">
                    <span></span>
                    <i></i>
                    <em>T° {{pageContent.min}}</em>
                    <b>{{local.typeTemp=='C'?climate.tempMinC:climate.tempMinF}}°{{local.typeTemp}}</b>
                </div>
                <div class="cit-block c-t-prec">
                    <span></span>
                    <i></i>
                    <em>{{pageContent.precipitation}}</em>
                    <b>{{local.typeTemp=='C'?climate.precipMm:climate.precipIn}} {{local.typeTemp=='C'?pageContent.mmDist:pageContent.inDist}}</b>
                </div>
        </div>
        <div class="green-bg graff-wrap">
            <div class="container">
                <div id="weatherYear"></div>
                <br/>

            </div> <a href="https://www.highcharts.com/" target="_blank" style="position: absolute;
                                                                                                                                           bottom: 10px;
                                                                                                                                           font-size: 9px;
                                                                                                                                           right: 7px;
                                                                                                                                           color: #80ffd8;">highcharts.com</a>


                    </div>
                       </section><!-- end climate-in -->

    <section id="coordinates">
        <div class="container">
            <h2 ng-bind="pageContent.coordinates"></h2>
            <p><b>{{coordinates.city}}, {{coordinates.country}}.</b> {{coordinates.city}} <a href="#" ng-bind="pageContent.onMap"></a> {{coordinates.latitude}}°, {{coordinates.longitude}}°. {{pageContent.timezone}}: UTC {{coordinates.hours_between}}</p>
        </div>
    </section><!-- end coordinates -->

    <section class="last-year5 theme-wrapper theme-block">
        <div class="container last-year-five">
            <h4 ng-bind="pageContent.last5YearWeatherData"></h4>
        </div>
        <div class="blue-bg-full">
            <div class="container">
                <div class="last-info-wrap">
                    <div class="v-date">
                        <div class="img-wrap">
                            <img src="img/calendar.svg" alt="img" class="img">
                        </div>
                        <div class="v-today-wrap">
                            <div class="today" ng-bind="pageContent.today"></div>
                            <div class="date">{{temperature.day}} {{temperature.month}}</div>
                            <div style="font-size: 12px">Data at {{getTime('2:00 PM')}}</div>
                        </div>
                    </div>
                    <div class="btn-wrap">
                        <a href="../../templates/html/past-weather.html" class="btn-theme" ng-bind="pageContent.checkPastWeather"></a>
                    </div>
                </div>
                <div class="v-year-wrap">
                    <div class="v-year-list">
                        <div class="v-year-item" ng-repeat="year in five_years_average">
                            <div class="v-year">{{year.year}}</div>
                            <img class="v-img" ng-src="svg/wicons_svg_white/{{year.weatherCode}}_day.svg" alt="img" style="    width: 48px;    margin: auto; margin-bottom: 14px;">
                            <div class="temperature">
                                <span class="count">{{local.typeTemp=='C'?year.tempC:year.tempF}}</span>
                                <span class="mark">°{{local.typeTemp}}</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <section id="uv-index">
        <div class="container">
            <h4 ng-bind="pageContent.uvIndex"></h4>
            <div class="uv-top">
                <div class="uv-top-block">
                    <span class="greenLow"></span>
                    <em ng-bind="pageContent.uvIndex1"></em>
                </div>
                <div class="uv-top-block">
                    <span class="yellowAverage"></span>
                    <em ng-bind="pageContent.uvIndex2"></em>
                </div>
                <div class="uv-top-block">
                    <span class="orangeHigh"></span>
                    <em ng-bind="pageContent.uvIndex3"></em>
                </div>
                <div class="uv-top-block">
                    <span class="redHigh"></span>
                    <em ng-bind="pageContent.uvIndex4"></em>
                </div>
                <div class="uv-top-block">
                    <span class="redExtreme"></span>
                    <em ng-bind="pageContent.uvIndex5"></em>
                </div>
            </div>

            <div class="uv-bot" >
                <div class="uv-bot-block" ng-repeat="ultra in ultraviolet track by $index">
                    <em>{{$index == 0 ? pageContent.today:ultra.date}}</em>
                    <span class="{{colorsUV[$index]}}"></span>
                </div>
            </div>
        </div>
    </section>
</div>

<%@include file="footer2.jsp"%>
