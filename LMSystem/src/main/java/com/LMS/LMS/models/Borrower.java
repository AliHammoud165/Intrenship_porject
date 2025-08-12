package com.LMS.LMS.models;

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
    private UUID id;
    @Column(name = "name" ,nullable = false)
    private String name;
    @Column(name = "email",nullable = false)
    private String email;
    @Column(name = "phone_number",nullable = false)
    private String phoneNumber;


}
