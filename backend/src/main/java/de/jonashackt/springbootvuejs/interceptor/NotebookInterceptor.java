package de.jonashackt.springbootvuejs.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


public class NotebookInterceptor implements RequestInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(NotebookInterceptor.class);

    @Override
    public void apply(RequestTemplate requestTemplate) {
        try {
            String cookie = getCookie();
            requestTemplate.header("cookie", cookie);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String getCookie () throws Exception {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw new Exception("Failed to get cookie, when get request attr.");
        }
        HttpServletRequest request = requestAttributes.getRequest();
        if (request == null) {
            throw new Exception("Failed to get cookie, when get request.");
        }
        String cookie = request.getHeader("cookie");
        if (cookie == null) {
            throw new Exception("Failed to get cookie, when get header.");
        }
        LOG.info("cookie:{}", cookie);
        return  cookie;
    }
}
