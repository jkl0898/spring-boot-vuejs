package de.jonashackt.springbootvuejs.feignClient;

import com.alibaba.fastjson.JSON;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "notebook", url = "http://jupyter-web-app-service.kubeflow.svc.cluster.local")
//@FeignClient(value = "clusterPrivilege", url = "http://localhost:9098")
public interface NotebookClient {

    @GetMapping(value = "/api/namespaces/xieyao/notebooks")
    JSON getUserNotebooks();

//    @GetMapping(value = "/sp/api/hi")
//    String getInfo() ;
}
