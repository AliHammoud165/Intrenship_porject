package com.LMS.LMS.client;

import com.LMS.LMS.dtos.EmailRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "email-sender", url = "${feign.client.email.url}") // adjust port & name as needed
public interface EmailClient {

    @PostMapping("/send")
    String sendEmail(@RequestBody EmailRequest emailRequest);
}
