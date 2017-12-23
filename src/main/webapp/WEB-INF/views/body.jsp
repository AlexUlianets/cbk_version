<%--<c:if test="${pageName} != front-page">--%>
<div class="page-nav">
    <nav class="pn-top" >
        <ul>
            <li style="display: none" class="mob_weater"><a onclick="blockUrl(event)">${$state.params.day}</a></li>
            <li><a href="/${currentCountryCode}/weather/outlook/${selectedCity}" class=${pageName.equals("outlook")?"active":""}>${content.outlook}</a></li>
            <li><a href="/${currentCountryCode}/weather/today/${selectedCity}" class=${pageName.equals("today")?"active":""}>${content.today}</a></li>
            <li><a href="/${currentCountryCode}/weather/tomorrow/${selectedCity}" class=${pageName.equals("tomorrow")?"active":""}>${content.tomorrow}</a></li>
            <li><a href="/${currentCountryCode}/weather/history3/${selectedCity}" class=${pageName.equals("history3") || pageName.equals("history1")?"active":""}>${content.pastWeather}</a></li>
            <li><a href="/${currentCountryCode}/weather/map/${selectedCity}" class=${pageName.equals("map")?"active":""}>${content.temperatureMap}</a></li>
        </ul>

    </nav>
    <nav class="pn-bot">
        <ul>
            <li><a href="/${currentCountryCode}/weather/3/${selectedCity}" class=${pageName.equals("weather3")?"active":""}>${content.threeDay}</a></li>
            <li><a href="/${currentCountryCode}/weather/5/${selectedCity}" class=${pageName.equals("weather5")?"active":""}>${content.fiveDay}</a></li>
            <li><a href="/${currentCountryCode}/weather/7/${selectedCity}" class=${pageName.equals("weather7")?"active":""}>${content.sevenDay}</a></li>
            <li><a href="/${currentCountryCode}/weather/10/${selectedCity}" class=${pageName.equals("weather10")?"active":""}>${content.tenDay}</a></li>
            <li><a href="/${currentCountryCode}/weather/14/${selectedCity}" class=${pageName.equals("weather14")?"active":""}>${content.fourteenDay}</a></li>
            <li><a href="/${currentCountryCode}/weather/hour-by-hour3/${selectedCity}" class=${pageName.equals("hour-by-hour-3") || pageName.equals("hour-by-hour-1") ? "active":""}>${content.hourByHour}</a></li>
        </ul>
    </nav>
</div><!-- end page-nav -->
<%--</c:if>--%>
<div ui-view></div>
<script>
   function blockUrl(event) {
        event.preventDefault()
        activeMenu();
   }
</script>