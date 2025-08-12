package com.LMS.LMS.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Table(name = "author")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Author {

    @Id
    @GeneratedValue
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;
    @Column(name = "name")
    private String name;
    @Column(name = "biography")
    private String biography;

}
