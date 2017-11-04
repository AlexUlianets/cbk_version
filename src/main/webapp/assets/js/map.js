var app = angular.module('main');

app.controller('MapMarkerCtrl', function($scope, $element, mapMarkerConstructor){

        const latlng = new google.maps.LatLng($scope.location.lat, $scope.location.lng);
        const googleOverlayView = new mapMarkerConstructor.GoogleOverlayView($element, latlng, $scope.location, $scope.local.typeTemp);
        googleOverlayView.setMap($scope.gmap);

    }).service('mapMarkerConstructor', function () {

        const GoogleOverlayView = function(element, latlng, location, typeTemp) {
            this.element = element;
            this.latlng = latlng;
            this.temp = typeTemp==='C'?location.temp_C:location.temp_F;
            this.img = location.img;
            this.day = location.day;
            this.typeTemp = typeTemp;
            this.style = new setColor(location.temp_C)[0][2];
        };

        const setColor = function(temp){

            var tempList = [
                [-40, -31, "betweenM40andM30"], [-30, -21, "betweenM30andM20"], [-20, -16, "betweenM20andM15"],
                [-15, -10, "betweenM15andM10"], [-10, -6, "betweenM10andM5"], [-5, -1, "betweenM5and0"],
                [0, 4, "between0and5"], [5, 9, "between5and10"], [10, 14, "between10and15"],
                [15, 19, "between15and20"], [20, -24, "between20and25"], [25, 29, "between25and30"],
                [30, 39, "between30and40"], [40, 50, "between40and50"]
            ];

            return tempList.filter(function (item) {
                return temp >= item[0] && temp <= item[1]
            })
        };

        GoogleOverlayView.prototype = new google.maps.OverlayView();

        GoogleOverlayView.prototype.draw = function() {

            this.element[0].className = "arrow_box "+this.style;
            this.element[0].innerHTML = "<img src='svg/wicons_svg_white/"+this.img+"_"+this.day+".svg' alt=''>" +
                "<h5>"+this.temp+" <span>"+this.typeTemp+"</span><h5>";

            var panes = this.getPanes();
            var point = this.getProjection().fromLatLngToDivPixel(this.latlng);

            panes.overlayImage.appendChild(this.element[0]);

            if (point) {
                this.element.css('left', point.x -20 + 'px');
                this.element.css('top', point.y -70 + 'px');
            }
        };
        GoogleOverlayView.prototype.onRemove = function() {};

        this.GoogleOverlayView = GoogleOverlayView;
    });


