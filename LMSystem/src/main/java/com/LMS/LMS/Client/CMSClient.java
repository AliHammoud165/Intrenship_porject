package com.LMS.LMS.Client;

import com.LMS.LMS.DTOs.CMSRequest;
import com.LMS.LMS.DTOs.EmailRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "Cms-Handler", url = "http://localhost:8084/api/transaction")
public interface CMSClient {
    @PostMapping("/create")
    String CmsHandler(@RequestBody CMSRequest cmsRequest);
}
