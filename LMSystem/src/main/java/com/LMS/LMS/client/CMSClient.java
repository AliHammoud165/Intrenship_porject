package com.LMS.LMS.client;

import com.LMS.LMS.dtos.CMSRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "Cms-Handler", url = "{feign.client.cms.url}")
public interface CMSClient {
    @PostMapping("/create")
    String CmsHandler(@RequestBody CMSRequest cmsRequest);
}
