package de.jonashackt.springbootvuejs.feignClient;

import com.alibaba.fastjson.JSON;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "clusterPrivilege", url = "https://kf.lianyirong.com.cn")
//@FeignClient(value = "clusterPrivilege", url = "http://localhost:9098")
public interface NotebookClient {

    @GetMapping(value = "/jupyter/api/namespaces/xieyao/notebooks")
    JSON getUserNotebooks();

//    @GetMapping(value = "/sp/api/hi")
//    String getInfo() ;
}
