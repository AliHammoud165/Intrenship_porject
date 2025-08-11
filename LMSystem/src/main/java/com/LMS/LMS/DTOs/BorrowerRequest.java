package com.LMS.LMS.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowerRequest {
    private String Name;
    private String Email;
    private String PhoneNumber;
}
