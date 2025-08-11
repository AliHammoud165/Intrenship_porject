package com.LMS.LMS.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Table(name = "borrower")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Borrower {
    @Id
    @GeneratedValue
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID Id;
    @Column(name = "name" ,nullable = false)
    private String Name;
    @Column(name = "email",nullable = false)
    private String Email;
    @Column(name = "phone_number",nullable = false)
    private String PhoneNumber;


}
