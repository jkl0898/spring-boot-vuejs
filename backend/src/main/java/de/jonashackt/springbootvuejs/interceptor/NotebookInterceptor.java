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

    private static final String KUBEFLOW_USERID = "kubeflow-userid";
    private static final String X_XSRF_TOKEN = "x-xsrf-token";
    private static final String COOKIE = "cookie";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String X_XSRF_TOKEN_VALUE = "13OFr3NKxow33pHABiuO4ZxyjAG5bcAQmJmN0PHzWeo";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        try {
            String kubeflowUserId = getHeader(KUBEFLOW_USERID);
            requestTemplate.header(KUBEFLOW_USERID, kubeflowUserId);
            requestTemplate.header(X_XSRF_TOKEN, X_XSRF_TOKEN_VALUE);
            requestTemplate.header(COOKIE, String.format("XSRF-TOKEN=%s", X_XSRF_TOKEN_VALUE));
            requestTemplate.header(CONTENT_TYPE, "application/json");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getHeader(String key) throws Exception {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw new Exception("Failed to get cookie, when get request attr.");
        }
        HttpServletRequest request = requestAttributes.getRequest();
        if (request == null) {
            throw new Exception("Failed to get cookie, when get request.");
        }
        String headerValue = request.getHeader(key);
        if (headerValue == null) {
            throw new Exception("Failed to get cookie, when get header.");
        }
        LOG.info("Header value:{}", headerValue);
        return  headerValue;
    }
}
