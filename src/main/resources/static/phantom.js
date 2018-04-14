/**
 * Created by yingu on 2018/4/14.
 */
"use strict";

var page = require('webpage').create(),
    system = require('system'),
    t, address, filename;

// 设置屏幕分辨率，必须
page.viewportSize = {width: 1440, height: 900};
// 裁剪区域，可以靠这个定位图表位置
page.clipRect = { top: 0, left: 0, width: 1024, height: 768 };

if (system.args.length === 1) {
    console.log('Usage: loadspeed.js <some URL>');
    phantom.exit();
}

t = Date.now();
address = system.args[1];
filename = system.args[2];
console.log("[PhantomJS] Opening Url:", address);
page.open(address, function (status) {
    if (status !== 'success') {
        console.log('FAIL to load the address');
    } else {
        console.log('Saving to file: ' + filename);
        // 改后缀名就可以切换输出类型，支持 png、pdf、jpg、jpeg。gif 生成失败
        // 复杂页面用 jpg，简单页面用 png，加密 pdf
        // 参考 http://phantomjs.org/screen-capture.html
        page.render(filename + '.png');
        t = Date.now() - t;
        console.log('Loading time ' + t + ' msec');
        // TODO: 防止超时，有些页面加载会超时，由于另起线程，程序没反应，不报错。但是此超时判断失败
/*        waitFor(function () {
            return page.evaluate(function () {
                return $(".persistFinish").length > 0;
            });
        }, function () {
            phantom.exit();
        }, 120000);*/
    }
    phantom.exit();
});