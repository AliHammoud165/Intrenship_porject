package com.LMS.LMS.Client;

import com.LMS.LMS.DTOs.EmailRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "email-sender", url = "http://localhost:8083/api/email") // adjust port & name as needed
public interface EmailClient {

    @PostMapping("/send")
    String sendEmail(@RequestBody EmailRequest emailRequest);
}
