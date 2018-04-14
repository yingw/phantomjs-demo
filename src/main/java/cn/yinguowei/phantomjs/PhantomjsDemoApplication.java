package cn.yinguowei.phantomjs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

@SpringBootApplication
public class PhantomjsDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhantomjsDemoApplication.class, args);
    }
}

@Controller
class RenderController {
    /**
     * 调试阶段在 target/static 目录，打包后不知道
     */
    private static final String SCRIPT_PATH = new File(RenderController.class.getResource("/static/phantom.js").getFile()).getPath();

    /**
     * 请前往 application.properties 配置服务器安装 phantomjs 的路径
     */
    @Value("${phantomjs.path}")
    String PHANTOMJS_PATH;

    @PostMapping({"", "/"})
    public String render(@RequestParam String siteUrl, Model model) {
        System.out.println("RenderController.render");
        System.out.println("siteUrl = " + siteUrl);

        // 用域名做输出文件名
        String fileName = siteUrl
                .replaceAll("http://", "")
                .replaceAll("https://", "");
        if (fileName.contains("/")) {
            fileName = fileName.substring(0, fileName.indexOf("/"));
        }
        if (fileName.contains(":")) {
            fileName = fileName.substring(0, fileName.indexOf(":"));
        }

        String command = PHANTOMJS_PATH + " " + SCRIPT_PATH + " " + siteUrl + " " + fileName;
        System.out.println("command = " + command);

        try {
            final Process process = Runtime.getRuntime().exec(command);
            new Thread(() -> {
                InputStreamReader reader = new InputStreamReader(process.getInputStream());
                LineNumberReader input = new LineNumberReader(reader);
                String line;
                try {
                    while ((line = input.readLine()) != null) {
                        System.out.println("command output: " + line);
                    }
                    System.out.println("Finished command.");
                } catch (Exception e) {
                    System.err.println("Error: " + e);
                    process.destroy();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "index";
    }

    @GetMapping({"", "/"})
    public String index() {
        System.out.println("RenderController.index");
        return "index";
    }
}