# PhantomJS DEMO

演示如何用 [PhantomJS](http://phantomjs.org/) 后台截取 web 屏幕并保存渲染结果

## 环境
下载 https://bitbucket.org/ariya/phantomjs/downloads/phantomjs-2.1.1-windows.zip

服务器上用 Linux 版本：https://bitbucket.org/ariya/phantomjs/downloads/phantomjs-2.1.1-linux-x86_64.tar.bz2

配置 `application.properties` 内参数 `phantomjs.path` 为本机（服务器）phantomjs 的路径

## 运行
- 启动程序 `mvn spring-boot:run`
- 访问 http://localhost:8080
- 输入要截屏的 url，比如 http://www.baidu.com，或 http://localhost:8080/
- 提交

> 输出文件在执行命令的根目录


## 其他

如果要修改输出类型，修改 `resources/staic/phantom.js` 里面 `page.render` 参数文件名的后缀即可。支持
- jpg
- png
- pdf

代码参考：https://github.com/yzhang921/CBoard/blob/ff587a1a676eb8a912fbf3a690cec763032fedfb/src/main/java/org/cboard/services/PersistService.java#L31:15

其他官方文档
- 入门：http://phantomjs.org/quick-start.html
- 截屏：http://phantomjs.org/screen-capture.html
- 其他例子：http://phantomjs.org/examples/index.html