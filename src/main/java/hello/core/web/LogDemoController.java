package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
//    private final ObjectProvider<MyLogger> myLoggerProvider; //MyLogger를 찾을수 있는, Dependecy Lookup을 할 수 있는 애가 주입
    private final MyLogger myLogger;

    @RequestMapping("log-demo")
    @ResponseBody // 뷰 화면없이 문자열 반환
    public String logDemo(HttpServletRequest request) {
        String requestURL = request.getRequestURI().toString();
//        MyLogger myLogger = myLoggerProvider.getObject();
        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");
        logDemoService.logic("testId");

        return "OK";
    }

}
