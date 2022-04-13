package de.jonashackt.springbootvuejs.feignClient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author xieyao
 */
@FeignClient(value = "kubeflowProfile", url = "http://profiles-kfam.kubeflow.svc.cluster.local:8081/kfam/v1")
public interface KubeflowProfileClient {

    @GetMapping(value = "/bindings")
    JSON getKubeflowProfile();

    @PostMapping(value = "/profiles")
    JSON createKubeflowProfile(@RequestBody JSONObject profileInfo);

    @DeleteMapping(value = "/profiles/{namespace}")
    JSON deleteKubeflowProfile(@PathVariable("namespace") String namespace);
}
