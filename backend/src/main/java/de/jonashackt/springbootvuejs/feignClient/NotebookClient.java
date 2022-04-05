package de.jonashackt.springbootvuejs.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "clusterPrivilege", url = "https://kf.lianyirong.com.cn")
public interface NotebookClient {

    @GetMapping(value = "/jupyter/api/namespaces/xieyao/notebooks")
    Object getUserNotebooks();
}
