package com.LMS.LMS.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowerRequest {
    private String name;
    private String email;
    private String phoneNumber;
}
