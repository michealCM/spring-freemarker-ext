var $winH = $(window).height();
var $img = $("img");
var $imgH = parseInt($img.height() / 2);

/**
 * 扫描所有的img标签进行lazyImage图片替换。
 *
 * @param target
 * @param timespan
 * @returns
 */
function loadingLazyImage(target,timespan) {
    if(target == null || target == undefined) {
        $img.each(function(i) {
            var $src = $img.eq(i).attr("data-src");
            var $scroTop = $img.eq(i).offset();
            if ($scroTop.top + $imgH >= $(window).scrollTop()
                && $(window).scrollTop() + $winH >= $scroTop.top + $imgH &&  $src != undefined ) {
                if ($img.eq(i).attr("src") != $src) {
                    if(timespan == null || timespan == undefined) {
                        $img.eq(i).attr("src",$src);
                        $img.eq(i).removeAttr("data-src");
                    } else {
                        $img.eq(i).hide();
                        $img.eq(i).removeAttr("data-src");
                        $img.eq(i).attr("src", $src).fadeIn(timespan);
                    }
                }
            }
        })
    } else {
        target.each(function(i) {
            var $src = target.eq(i).attr("data-src");
            if (target.eq(i).attr("src") != $src && $src != undefined) {
                target.eq(i).hide();
                target.eq(i).attr("src", function() { return $src; }).fadeIn(timespan);
            };
            $img.eq(i).removeAttr("data-src");
        });
    }
}

/**
 * 图片加载失败替换使用加载失败图片替换显示。
 * @returns
 */
function loadingError(){
    $img.each(function(index, element) {
        $(element).error(function(){
            var oldsrc=$(element).attr("src");
            $(element).attr("dir","加载图片失败:"+oldsrc);
            $(element).attr("src",loadingErrorImage);
        });
    })
}

//页面加载完成触发
loadingLazyImage();

//界面滚动进行触发
$(window).scroll(function() {
    loadingLazyImage(null, 500);
})

//图片加载失败
loadingError();