function loadScript() {
    $(document).ready(function () {

        setTimeout(function () {
            $('.load_header').fadeOut("slow", function () {
                $(this).css({'display': 'none'});
                // $('#top-page').find('#head-bot-mini').css({'display': 'block'});
                $('#top-page').find('.container').css({'display': 'block'});
                $('#top-page').find('.head-bot-mini').css({'display': 'block'});
                $('#top-page').find('.head-bot').css({'display': 'block'});
                $('#top-main').find('.container').css({'display': 'block'});
                $('#top-main').find('.head-bot-mini').css({'display': 'block'});
                $('#top-main').find('.head-bot').css({'display': 'block'});
                // $('#top-main').find('#head-bot').css({'display': 'block'});
                // $('.tb-contant').removeClass('inner-html')
            })
        }, 100)
        $('.mob_weater').click(function (e) {
            e.preventDefault()
        });
        $("head").append("<link href='https://fonts.googleapis.com/css?family=Fira+Sans:300,400,500,700' rel='stylesheet'>");
        $('.temp-block').on('click', function (e) {
            $this = $(this);
            $this.addClass('active').siblings().removeClass('active');
            $('#main-menu , #nav-toggle, #h-menu').removeClass('active');
            $('#h-share').removeClass('active');
        });
        $('.temp-wrap').on('click', function (e) {
            $(this).toggleClass('open');
        });
        $('body').on('click', function () {
            $('.temp-wrap').removeClass('open');
        });
        $('.temp-wrap').on('click', function (e) {
            e.stopPropagation();
        });
        $('#nav-toggle').on('click', function (e) {
            $(".blur").toggleClass("the-blur");
            e.preventDefault();
            var $this = $(this);
            $this.toggleClass('active');
            $('#h-menu, #main-menu').toggleClass('active');
            $('.temp-wrap').removeClass('open');
        });
        $(document).on('mouseup', function (e) {
            e.preventDefault();
            var div = $("#main-menu");
            if (!div.is(e.target) && div.has(e.target).length === 0 && $('#nav-toggle').has(e.target).length === 0) {
                $('#main-menu , #nav-toggle, #h-menu').removeClass('active');
                $(".blur").removeClass("the-blur");
            }
        })
        var MenuChangeToResolution = function () {
            if ($(window).width() <= 700) {
                //var a = $(window).width();
                // console.log(a);
                $('.pn-top ul li:first').show();
                $('.page-nav li a').each(function (i, elem) {
                    if ($(this).hasClass("active")) {
                        $('.pn-top ul li:first a').text(elem.text);
                    }
                })
                $('body').click(function (e) {
                    if($(e.target)[0]['localName']!=='a') {
                        $('.pn-top ul li:not(:first), .pn-bot ul li').fadeOut("slow");
                    }
                });
            } else {
                $('.mob_weater').css('display', 'none');
            }

        };
        MenuChangeToResolution();
        $(window).resize(function () {
            MenuChangeToResolution();
        })
        var $nav = $('.text-weather-nav nav ul li a');
        var $tab = $('.text-weather-tab');
        $nav.eq(0).addClass('active');
        $tab.not(":first").hide();
        $nav.on('click', function (event) {
            event.preventDefault();
            var $this = $(this);
            var $href = $this.attr('href');
            $($href).fadeIn().siblings('.text-weather-tab').hide();
            $this.addClass('active').parent().siblings().find('a').removeClass('active');
            $(".blur").removeClass("the-blur");
        });
        $('.forecast-row-inner-wrap, .hour-row-inner-wrap').hide();
        $('.forecast-row.active, .hour-row.active').next('.forecast-row-inner-wrap, .hour-row-inner-wrap').show();
        $('.forecast-row, .hour-row').on('click', function (e) {
            var $this = $(this);
            var $innner = $('.forecast-row-inner-wrap, .hour-row-inner-wrap');
            $this.addClass('active').parent().siblings().find('.forecast-row, .hour-row').removeClass('active');
            var is_visible = $('.forecast-row.active, .hour-row.active').next('.forecast-row-inner-wrap, .hour-row-inner-wrap').is(':visible');
            if (is_visible) {
                $('.hour-row').removeClass('active');
            } else {
                $this.addClass('active');
            }
            $this.parent().find($innner).slideToggle();
            $this.parent().siblings().find($innner).slideUp();
        });
        $('.for-arr-top').on('click', function () {
            $(this).parent().slideUp();
        });
        $(".tab_content").hide();
        if ($(window).width() > 700) {
            $(".tab_drawer_heading[rel^='tab1']").addClass("d_active");
            $(".tab_drawer_heading[rel^='hov-tab1']").addClass("d_active");
            $("#tab1").show();
            $("#hov-tab1").show();
        }

        if ($("ul.tabs").length > 1) {
            $("ul.tabs").each(function (i) {
                //   $(this).find('li').click(function () {

                //   console.log('here');
                // });
            });
        } else {

        }
        $(".tab_drawer_heading").click(function () {
            if ($('#' + ($(this).attr("rel"))).css('display') == 'none' && $(window).width() < 400) {
                $(".tab_content").hide();
                var d_activeTab = $(this).attr("rel");
                $("#" + d_activeTab).fadeIn();
                $(".tab_drawer_heading").removeClass("d_active");
                $(this).addClass("d_active");
                $("ul.tabs li").removeClass("active");
                $("ul.tabs li[rel^='" + d_activeTab + "']").addClass("active");
            } else if ($('#' + ($(this).attr("rel"))).css('display') == 'block' && $(window).width() < 400) {
                $('.tab_content').slideUp();
                $(".tab_drawer_heading").removeClass("d_active");
            } else if ($(window).width() > 400) {
                $(".tab_content").hide();
                var d_activeTab = $(this).attr("rel");
                $("#" + d_activeTab).fadeIn();
                $(".tab_drawer_heading").removeClass("d_active");
                $(this).addClass("d_active");
                $("ul.tabs li").removeClass("active");
                $("ul.tabs li[rel^='" + d_activeTab + "']").addClass("active");
            }
        });
        $('.tab-arrow').on('click', function () {
            $('.tab_content').slideUp();
            $(".tab_drawer_heading").removeClass("d_active");
        });
        $('ul.tabs li').last().addClass("tab_last");
        var $width = $(document).width();
        if ($width <= 700) {
          
        }
        $(document).on("click", ".transformer-tabs a:not('.active')", function (event) {
            event.preventDefault();
            var $this = $(this);
            var $hash = $(this).attr('rel');
            $this.addClass('active').parent().siblings().find('a').removeClass('active');
            $($hash).addClass("active").siblings().removeClass("active");
            $this.closest("ul").toggleClass("open");
        }).on("click", ".transformer-tabs a.active", function (event) {
            event.preventDefault();
            var $this = $(this);
            $this.closest("ul").toggleClass("open");
        });
        $('.a-popup, .s-popup').magnificPopup({
            removalDelay: 500, callbacks: {
                beforeOpen: function () {
                    this.st.mainClass = this.st.el.attr('data-effect');
                    $('.temp-wrap').removeClass('open');
                    $('.main-menu , #nav-toggle, .h-menu').removeClass('active');
                    $(".blur").removeClass("the-blur");
                }, afterClose: function () {
                },
            }, midClick: true
        });
        var popupToggleActive = function () {
            $('.settings-popup-md li a').on('click', function (e) {
                e.preventDefault();
                var $this = $(this);
                $this.addClass('active').parent().siblings().find('a').removeClass('active');
            });
            $('.spr-item').on('click', function (e) {
                e.preventDefault();
                var $this = $(this);
                $this.addClass('active').siblings().removeClass('active');
            });
        };
        popupToggleActive();
        $('.spr-item').on('click', function (e) {
            e.preventDefault();
            var $this = $(this);
            $this.addClass('active').siblings().removeClass('active');
        });
        var $dropdown = $('.search-dropdown');
        $('body').click(function (e) {
            if($(e.target)[0]['localName']!=='input'
                && $(e.target)[0]['localName']!=='i'
                && $(e.target)[0]['className']!=='ht-search-input'
                && $(e.target)[0]['className']!=='searchIco') {
                $('.search-dropdown').removeClass('opened');
                $('.search-dropdown').css({'display': 'none'})
            }
        })
        $('.ht-search-input input').click(function (e) {
            if(!$('.search-dropdown').hasClass('opened')) {
                $('.search-dropdown').addClass('opened');
                $('.search-dropdown').css({'display': 'block'})
            }
        });

        $('.dropdown-top').on('click', function (e) {
            e.stopPropagation();
            e.preventDefault();
            $dropdown.slideUp();
            $('.search-dropdown').removeClass('opened');
            $('.search-dropdown').css({'display': 'none'})
        });

        $(window).on('scroll resize', function () {
            if ($(window).width() > 767) {
                var HeaderTop = 111;
                $(window).scroll(function () {
                    if ($(window).scrollTop() > HeaderTop) {
                        $('#fix-menu').css({position: 'fixed', top: '0px'});
                    } else {
                        $('#fix-menu').css({position: 'absolute', top: '111px'});
                    }
                });
            }
            if ($(window).width() < 767) {
                var HeaderTop = 0;
                $(window).scroll(function () {
                    if ($(window).scrollTop() > HeaderTop) {
                        $('#fix-menu').css({position: 'fixed', top: '0px'});
                    } else {
                        $('#fix-menu').css({position: 'absolute', top: '0px'});
                    }
                });
            }
        });
        $('#h-share').on('click', function (e) {
            e.stopPropagation();
            $(this).toggleClass('active');
            $('.temp-wrap').removeClass('open');
            $('#main-menu , #nav-toggle, #h-menu').removeClass('active');
            $(".blur").removeClass("the-blur");
        });
        $('body').on('click', function () {
            $('#h-share').removeClass('active');
            $('.ht-search-input input').val('')
        });


        function adaptiveWidth(elem, control, n) {
            //elem - ориентир, откуда берем ширину
            //control - чему присваеваем ширину
            var widthContent = $(elem).width();
            $(control).css('width', widthContent - n);
        }

        adaptiveWidth('.page-content', '.tb-slider-wrap', 20);
        $(window).resize(function () {
            adaptiveWidth('.page-content', '.tb-slider-wrap', 20);
        });
    });

    function setIdle(cb, seconds) {
        var timer;
        var interval = seconds * 1000;
        function refresh() {
            clearInterval(timer);
            timer = setTimeout(cb, interval);
        };
        $(document).on('keypress click', refresh);
        refresh();
    }

    setIdle(function() {
        location.href = location.href;
    }, 15 * 60);

}
function activateTab(index) {
    var activeTab = "tab" + index;
    $("#" + activeTab).fadeIn();
    $("ul.tabs li").removeClass("active");
    $(".tabclass" + index).addClass("active");
    $(".tab_drawer_heading").removeClass("d_active");
    $(".tab_drawer_heading[rel^='" + activeTab + "']").addClass("d_active");
}
function activeMenu() {
    var $width = $(document).width();
    if ($width <= 700) {
        if ($('.pn-top ul li:not(:first), .pn-bot ul li').css('display') === 'list-item') {
            $('.pn-top ul li:not(:first), .pn-bot ul li').each(function () {
                $(this).fadeOut("slow");
            })
        } else {
            $('.pn-top ul li:not(:first), .pn-bot ul li').each(function () {
                $(this).css({'display': 'list-item'})
            })
        }
    }
}
//
// function activateTabHourly(index) {
//     var activeTab = "tab" + index;
//     $("#" + activeTab).fadeIn();
//     $(this).siblings().removeClass("active");
//     $(".tabclass" + index).addClass("active");
//     $(".tab_drawer_heading").removeClass("d_active");
//     $(".tab_drawer_heading[rel^='" + activeTab + "']").addClass("d_active");
// }
function onIcoSearch() {
    if(!$('.search-dropdown').hasClass('opened')) {
        $('.search-dropdown').addClass('opened');
        $('.search-dropdown').css({'display': 'block'})
    } else {
        $('.search-dropdown').removeClass('opened');
        $('.search-dropdown').css({'display': 'none'})
    }
}
function changeTimeFormat(str, timeFormat) {

    if( parseInt(timeFormat) === 24 ){
        var option=1;
        var tokens = /([10]?\d):([0-5]\d) ([ap]m)/i.exec(str);
        if (tokens == null) {
            tokens = /([10]?\d) ([ap]m)/i.exec(str)
            option=2;
        }
        if (tokens == null) {
            return str;
        }
        if(option===2){
            if (tokens[2].toLowerCase() === 'pm' && tokens[1] !== '12') {
                tokens[1] = '' + (12 + (+tokens[1]));
            } else if (tokens[2].toLowerCase() === 'am' && tokens[1] === '12') {
                tokens[1] = '00';
            }
            return tokens[1] + ':00';
        }else {
            if (tokens[3].toLowerCase() === 'pm' && tokens[1] !== '12') {
                tokens[1] = '' + (12 + (+tokens[1]));
            } else if (tokens[3].toLowerCase() === 'am' && tokens[1] === '12') {
                tokens[1] = '00';
            }
            return tokens[1] + ':' + tokens[2];
        }


    }else if(str!=undefined && str!=='0:0'){
        var time =  toDate(str,"h:m");

        if (time == 'Invalid Date') { return str; }

        var hours = time.getHours() > 12 ? time.getHours() - 12 : time.getHours();
        var am_pm = time.getHours() >= 12 ? "PM" : "AM";
        hours = hours < 10 ? "0" + hours : hours;
        var minutes = time.getMinutes() < 10 ? "0" + time.getMinutes() : time.getMinutes();

        time = hours + ":" + minutes + " " + am_pm;

        return time;
    }
}

function toDate(dStr,format) {
    var now = new Date();
    if (format == "h:m") {
        now.setHours(dStr.substr(0,dStr.indexOf(":")));
        now.setMinutes(dStr.substr(dStr.indexOf(":")+1));
        now.setSeconds(0);
        return now;
    }else
        return "Invalid Format";
}
