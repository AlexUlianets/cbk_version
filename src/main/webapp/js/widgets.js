function loadScript2() {
    $(document).ready(function () {
        //Activate widget search
        $(function () {
            $('.wg_search_keys').click(function () {
                console.log('1');
                if ($(this).hasClass('show_search')) {
                    $(this).removeClass('show_search');
                    $('.wg_form_resault').removeClass('active_search');
                } else {
                    $(this).addClass('show_search');
                    $('.wg_form_resault').addClass('active_search');
                }

            });

            //Exclude Item
            $('html,body').click(function (e) {
                //e.stopPropagation();

                if (!$(e.target).is(".wg_form_resault") && !$(e.target).parents(".wg_form_resault_wrap").length) {
                    //console.log($(e.target));
                    $('.wg_search_keys').removeClass('show_search');
                    $('.wg_form_resault').removeClass('active_search');
                }
            });


        });

        //Activate styler for widget
        $(function () {
            $('.wg_radio input').styler();
        });

    /*
     ** Widget slider with nav
     */

    $(function () {

        $('#widget_carusel').slick({
            slidesToShow: 4,
            slidesToScroll: 1,
            pauseOnHover: false,
            focusOnSelect: true,
            prevArrow: '<button type="button" class="slick-prev slick-arrow"><</button>',
            nextArrow: '<button type="button" class="slick-next slick-arrow">></button>',
            responsive: [
                {
                    breakpoint: 1025,
                    settings: {
                        slidesToShow: 3
                    }
                },
                {
                    breakpoint: 892,
                    settings: {
                        slidesToShow: 2
                    }
                },
                {
                    breakpoint: 611,
                    settings: {
                        slidesToShow: 1
                    }
                }
            ]

        });

        /*
         ** Вставляем активный виджет в resizable окно
         */
        //При начальной загрузке
        var curruntSlide = $("#widget_carusel").slick("getSlick").$slides[0];
        curruntSlide = $(curruntSlide).html();
        //console.log(curruntSlide);

        var currentWidget = $('.wg_respons_content .widget_nav');
        currentWidget.html(curruntSlide);

        //После смены активного слайда
        $('#widget_carusel').on('afterChange', function (event, slick, currentSlide, nextSlide) {
            var curruntSlide = $(slick.$slides[currentSlide]).html();
            currentWidget.html(curruntSlide);
            //$('.content').hide();
            //$('.content[data-id=' + dataId + ']').show();
        });

    });


    $(function () {

        var maxHeight = 755,
            maxWidth = 755,
            minHeight = 50,
            minWidth = 100,
            startWidth = 300,
            startHeight = 250;

        //задаем начальные параметры виджету
        $('#wg_width').val(startWidth).attr('value', startWidth);
        $('#wg_height').val(startHeight).attr('value', startHeight);
        $('.wg_respons_box').css({
            width: startWidth,
            height: startHeight
        });
        changeWidgetClassWidht('.wg_response_wrap', startWidth);
        changeWidgetClassHeight('.wg_response_wrap', startHeight);

        //widget resizable
        $('.wg_respons_box').resizable({
            maxHeight: maxHeight,
            maxWidth: maxWidth,
            minHeight: minHeight,
            minWidth: minWidth,
            handles: {
                e: '.handles_right',
                s: '.handles_bottom'
            },
            resize: function (event, ui) {
                var widthR = ui.size.width;
                var heightR = ui.size.height;
                //var className =
                $('.response_settings #wg_width').val(widthR);
                $('.response_settings #wg_height').val(heightR);
                changeWidgetClassWidht('.wg_response_wrap', widthR);
                changeWidgetClassHeight('.wg_response_wrap', heightR);
                /*$('.wg_respons_content .wg_nav_item').css({
                 width: widthR,
                 height: heightR
                 });
                 $('.current_widget').slick('resize');*/
            }
        });


        //input change width
        $('.response_settings #wg_width').change(function () {
            var currentSize = $(this).val();

            if (currentSize > maxWidth) {
                $(this).val(maxWidth);
                $(this).attr('value', maxWidth);
                animateWidth('.wg_respons_box', maxWidth);
            }
            else if (currentSize < minWidth) {
                $(this).val(minWidth);
                $(this).attr('value', minWidth);
                animateWidth('.wg_respons_box', minWidth);
            } else {
                $(this).val(currentSize);
                $(this).attr('value', currentSize);
                animateWidth('.wg_respons_box', currentSize);
            }
            changeWidgetClassWidht('.wg_response_wrap', currentSize);
        });

        //input change height
        $('.response_settings #wg_height').change(function () {
            var currentSize = $(this).val();

            if (currentSize > maxHeight) {
                $(this).val(maxHeight);
                $(this).attr('value', maxHeight);
                animateHeight('.wg_respons_box', maxHeight);
            }
            else if (currentSize < minHeight) {
                $(this).val(minHeight);
                $(this).attr('value', minHeight);
                animateHeight('.wg_respons_box', minHeight);
            } else {
                $(this).val(currentSize);
                $(this).attr('value', currentSize);
                animateHeight('.wg_respons_box', currentSize);
            }
            changeWidgetClassHeight('.wg_response_wrap', currentSize);
        });


    });

    function animateWidth(elem, size) {
        $(elem).animate({
            width: size
        }, 500);
    }

    function animateHeight(elem, size) {
        $(elem).animate({
            height: size
        }, 500);
    }

    //функция, добавления класса
    function addClassWild(elem, className) {
        $(elem).addClass(className);
    }

    //функция удаления класа
    // использовать как $('.wg_response_wrap').removeClassWild('wg_width_*');
    $.fn.removeClassWild = function (mask) {
        return this.removeClass(function (index, cls) {
            var re = mask.replace(/\*/g, '\\S+');
            return (cls.match(new RegExp('\\b' + re + '', 'g')) || []).join(' ');
        });
    };

    /*
     ** Функция проверки ширины. Добавляет и удаляет класы
     * в зависимости от ширины виджета с помощью функций addClassWild и removeClassWild
     */
    function changeWidgetClassWidht(element, currentWidth) {
        if (currentWidth < 150) {
            $(element).removeClassWild('wg_width_*');
            addClassWild(element, 'wg_width_100');
        } else if (currentWidth >= 150 && currentWidth < 210) {
            $(element).removeClassWild('wg_width_*');
            addClassWild(element, 'wg_width_150');
        } else if (currentWidth >= 210 && currentWidth < 300) {
            $(element).removeClassWild('wg_width_*');
            addClassWild(element, 'wg_width_210');
        } else if (currentWidth >= 300 && currentWidth < 400) {
            $(element).removeClassWild('wg_width_*');
            addClassWild(element, 'wg_width_300');
        } else if (currentWidth >= 400 && currentWidth <= 600) {
            $(element).removeClassWild('wg_width_*');
            addClassWild(element, 'wg_width_600');
        } else if (currentWidth > 600 && currentWidth <= 728) {
            $(element).removeClassWild('wg_width_*');
            addClassWild(element, 'wg_width_728');
        } else {
            $(element).removeClassWild('wg_width_*');
            addClassWild(element, 'wg_width_755');
        }
    }

    /*
     ** Функция проверки высоты. Добавляет и удаляет класы
     * в зависимости от высоты виджета с помощью функций addClassWild и removeClassWild
     */
    function changeWidgetClassHeight(element, currentHeight) {
        if (currentHeight < 100) {
            $(element).removeClassWild('wg_height_*');
            addClassWild(element, 'wg_height_50');
        } else if (currentHeight >= 100 && currentHeight <= 150) {
            $(element).removeClassWild('wg_height_*');
            addClassWild(element, 'wg_height_100');
        } else if (currentHeight > 150 && currentHeight <= 170) {
            $(element).removeClassWild('wg_height_*');
            addClassWild(element, 'wg_height_170');
        } else if (currentHeight > 170 && currentHeight <= 193) {
            $(element).removeClassWild('wg_height_*');
            addClassWild(element, 'wg_height_193');
        } else if (currentHeight > 193 && currentHeight <= 250) {
            $(element).removeClassWild('wg_height_*');
            addClassWild(element, 'wg_height_250');
        } else if (currentHeight > 250 && currentHeight <= 400) {
            $(element).removeClassWild('wg_height_*');
            addClassWild(element, 'wg_height_350');
        } else if (currentHeight > 400 && currentHeight <= 600) {
            $(element).removeClassWild('wg_height_*');
            addClassWild(element, 'wg_height_600');
        } else {
            $(element).removeClassWild('wg_height_*');
            addClassWild(element, 'wg_height_755');
        }
    }
})
}
// создаём плагин resizer
jQuery.fn.resizer = function () {
    // выполняем плагин для каждого объекта
    return this.each(function () {
        // определяем объект
        var me = jQuery(this);
        // вставляем в после объекта...
        me.after(
            // в нашем случае это наш "ресайзер" и производим обработку события mousedown
            jQuery('<div class="resizehandle"></div>').bind('mousedown', function (e) {
                // определяем высоту textarea
                var h = me.height();
                // определяем кординаты указателя мыши по высоте
                var y = e.clientY;
                // фнкция преобразовывает размеры textarea
                var moveHandler = function (e) {
                    me.height(Math.max(20, e.clientY + h - y));
                };
                // функци прекращает обработку событий
                var upHandler = function (e) {
                    jQuery('html').unbind('mousemove', moveHandler).unbind('mouseup', upHandler);
                };
                // своего рода, инициализация, выше приведённых, функций
                jQuery('html').bind('mousemove', moveHandler).bind('mouseup', upHandler);
            })
        );
    });
}