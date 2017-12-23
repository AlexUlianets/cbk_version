<section id="top-main">
<div class="container">
    <div class="head-top">
        <div class="ht-search">
            <form class="ht-search-wrap">
                <div class="ht-search-inner">
                    <div class="ht-search-input" ng-click="searchHint()">
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
                    <img src="../../images/cloud_load.gif"
                         style="width: 18%;margin-left: 41%;display: none;margin-right: 41%;" class="load_search">
                    <ul>
                        <li ng-repeat="i in searchList" ng-if="searchInput.length>1 && result==1"><a
                                ng-class="{'active':i.geonameId==temperature.geonameId}"
                                ng-click="selectCity(i.geonameId)">{{i.name}}, {{i.countryName}} <span
                                ng-if="false"></span></a></li>
                        <li ng-repeat="i in searchList track by $index" ng-if="searchInput.length<=1"
                            class="w{{$index}}_li"><a id="w{{$index}}"
                                                      ng-class="{'active':i.geonameId==temperature.geonameId}"
                                                      ng-click="selectCity(i.geonameId)">{{i.city}}</a><span
                                ng-if="i.geonameId!=temperature.geonameId"
                                ng-click="deleteCity(i.geonameId, $index)"></span></li>
                        <li ng-if="searchList.length==0"><a>No results</a></li>
                    </ul>
                    <a href="#" class="dropdown-top"></a>
                </div>
            </form>
        </div>
        <div class="ht-location">
            <dl>
                <dt><i class="icon location"></i><span class="search-text1">${temperature.cityWeather}</span> <span
                        class="search-text2">${temperature.country}</span></dt>
                <dd>${content.today} ${temperature.month} ${temperature.day}. ${temperature.dayOfWeek}. <span
                        ng-bind="getTime(('0' + '${temperature.hours}').slice(-2)+':'+('0' + '${temperature.minutes}').slice(-2))"></span>
                </dd>
            </dl>
        </div>
    </div>

    <div class="head-bot">
        <div class="hb-inner">
            <div class="weather-now">
                <img width="90px" src="svg/wicons_svg_white/${temperature.weatherIconCode}.svg" alt="img">
                <div><b ng-bind="local.typeTemp=='C'?${temperature.temp_c}:${temperature.temp_f}"></b><em
                        ng-bind="local.typeTemp"></em>
                    <span>/<strong
                            ng-bind="local.typeTemp=='C'?${temperature.temp_f}:${temperature.temp_c}"></strong><abbr
                            ng-bind="local.typeTemp!='C'?'C':'F'"></abbr></span></div>
            </div>

            <div class="weather-info">
                <div class="weather-info-block">
                    <div class="wb-img">
                        <span class="helper"></span>
                        <img src="../../img/feels_like_white.svg" alt="img" style="    width: 24px;">
                    </div>
                    <h4> ${content.feelsLike}</h4>
                    <c:if test="${typeTemp == 'C'}">
                        <p> ${temperature.feelsLikeC} <span class="cels">${typeTemp}</span></p>
                    </c:if>
                    <c:if test="${typeTemp == 'F'}">
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
                    <c:if test="${typeTemp == 'C'}">
                        <p>    ${temperature.pressurehPa} <span>${content.hPa} </span></p>
                    </c:if>
                    <c:if test="${typeTemp == 'F'}">
                        <p>    ${temperature.pressureInch} <span>${content.in} </span></p>
                    </c:if>
                </div>
                <div class="weather-info-block">
                    <div class="wb-img">
                        <span class="helper"></span>
                        <!-- <i class="sprite-wind"></i> -->
                        <img src="../../img/wind_white.svg" alt="img"
                             style="    width: 25px;    -ms-transform: rotate(${temperature.windDegree} + 'deg' );    -webkit-transform: rotate(${temperature.windDegree } + 'deg');    transform: rotate(${temperature.windDegree} +  'deg');">
                    </div>
                    <h4>${content.wind}</h4>

                    <c:if test="${typeTemp == 'C'}">
                        <p>    ${temperature.windMs} <span>${content.ms} </span></p>
                    </c:if>
                    <c:if test="${typeTemp == 'F'}">
                        <p>    ${temperature.windMph} <span>${content.mph} </span></p>
                    </c:if>
                </div>
            </div><!-- end weather-info -->

            <div class="weather-time">
                <div class="weather-time-block">
                    <div class="wtb-img">
                        <span class="helper"></span>
                        <img src="../../images/svg-sprite/sunrise-weather-symbol.svg" alt="" style="width: 35px;">
                    </div>
                    <div class="wt-time" ng-bind="getTime('${temperature.sunrise}')"></div>
                </div>
                <div class="weather-time-block">
                    <div class="wtb-img">
                        <span class="helper"></span>
                        <!-- <i class="icon pm"></i> -->
                        <img src="../../images/svg-sprite/sunset-fill-interface-symbol.svg" alt="" style="width: 35px;">
                    </div>
                    <div class="wt-time" ng-bind="getTime('${temperature.sunset}')"></div>
                </div>
            </div>
        </div>
    </div><!-- end head-bot -->

    <div class="h-full-forecast">
        <c:if test="${pageName == 'frontPage'}">
            <div class="container">
                <a href="/${currentCountryCode}/weather/outlook/${selectedCity}"> ${content.fullForecast}<span
                        class="arr-right-white"></span></a>
            </div>
        </c:if>
    </div>
</div>
</section>