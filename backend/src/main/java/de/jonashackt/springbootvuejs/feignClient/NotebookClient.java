package de.jonashackt.springbootvuejs.feignClient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "notebook", url = "http://jupyter-web-app-service.kubeflow.svc.cluster.local")
//@FeignClient(value = "clusterPrivilege", url = "http://localhost:9098")
public interface NotebookClient {

    @GetMapping(value = "/api/namespaces/xieyao/notebooks")
    JSON getUserNotebooks();

    @RequestMapping(method = RequestMethod.POST, value = "/api/namespaces/{namespace}/notebooks")
    JSON createNotebooks(@PathVariable("namespace") String namespace,
                         @RequestBody JSONObject noteBookInfo);
}
