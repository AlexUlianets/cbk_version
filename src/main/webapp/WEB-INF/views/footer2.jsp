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
                    <h3>${content.weather}</h3>
                    <ul>
                        <li><a href="/${currentCountryCode}/weather/3/${selectedCity}">${content.threeDaysWeatherForecast}</a></li>
                        <li><a href="/${currentCountryCode}/weather/5/${selectedCity}">${content.fiveDaysWeatherForecast}</a></li>
                        <li><a href="/${currentCountryCode}/weather/7/${selectedCity}">${content.sevenDaysWeatherForecast}}</a></li>
                        <li><a href="/${currentCountryCode}/weather/14/${selectedCity}">${content.fourteenDaysWeatherForecast}</a></li>
                        <li><a href="/${currentCountryCode}/weather/hour-by-hour3/${selectedCity}">${content.hourlyWeather}</a></li>
                        <li><a href="/${currentCountryCode}/weather/map/${selectedCity}">${content.weatherMap}</a></li>
                        <li><a href="/${currentCountryCode}/weather/history3/${selectedCity}">${content.weatherHistory}</a></li>
                    </ul>
                </nav>

                <nav class="main-menu-block">
                    <h3>${content.applications}</h3>
                    <ul>
                        <li><a href="https://play.google.com/store/apps/developer?id=Oplao">${content.androidApps}</a></li>
                        <li><a href="https://chrome.google.com/webstore/detail/oplao-weather/jbjeihiakbjchgaehgcilindlghfffnb">${content.googleChromeWeatherExtension}</a></li>
                        <li><a href="https://addons.mozilla.org/en-US/firefox/addon/oplao-weather/">${content.firefoxWeatherExtension}</a></li>
                        <li><a href="https://addons.opera.com/en/extensions/details/oplao-weather/">${content.operaWeatherExtension}</a></li>
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
        <div class="apt-left">${content.alerts}</div>
    </div>
</div>
<!-- Popup -->
<div id="settings-popup" class="settings-popup mfp-with-anim mfp-hide">
    <div class="settings-popup-top">
        <div class="spt-left">${content.settings}</div>
    </div>
    <div class="settings-popup-md">
        <div class="popup-lang-wrap">
            <h2>${content.language}</h2>
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
                <h2>${content.unitsPreference}</h2>
                <a href="#" class="spr-item" ng-class="{active:local.typeTemp == 'C'}" ng-click="local.typeTemp = 'C'; updateTemp(local.typeTemp);">°C</a>
                <a href="#" class="spr-item" ng-class="{active:local.typeTemp == 'F'}" ng-click="local.typeTemp = 'F'; updateTemp(local.typeTemp);">°F</a>
            </div>
            <div class="spr-block popup-time">
                <h2>${content.timeFormat}</h2>
                <a href="#" class="spr-item" ng-class="{active:local.typeTime == 12}" ng-click="local.typeTime = 12; updateTime(local.typeTime);" ng-bind="pageContent.twelveHours"></a>
                <a href="#" class="spr-item" ng-class="{active:local.typeTime == 24}" ng-click="local.typeTime = 24; updateTime(local.typeTime);" ng-bind="pageContent.twentyFourHours"></a>
            </div>
        </div>
    </div>

    <div class="set-pop-bot">
        <div class="alerts_block">
            <div class="alerts_text">${content.alerts}</div>
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