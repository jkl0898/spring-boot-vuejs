package de.jonashackt.springbootvuejs.feignClient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author xieyao
 */
@FeignClient(value = "kbProfile", url = "http://profiles-kfam.kubeflow.svc.cluster.local:8081/kfam/v1")
public interface KbProfileClient {

    @GetMapping(value = "/bindings")
    JSON getProfile();

    @Async
    @PostMapping(value = "/profiles")
    void createProfile(@RequestBody JSONObject profileInfo) throws Exception;

    @Async
    @DeleteMapping(value = "/profiles/{namespace}")
    void deleteProfile(@PathVariable("namespace") String namespace) throws Exception;
}
