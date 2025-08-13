package com.LMS.LMS.models;

<<<<<<< HEAD
import io.swagger.v3.oas.annotations.media.Schema;
=======
>>>>>>> d599a845c61de5c926be53327c33faca6a6cf504
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
<<<<<<< HEAD
@Schema(name = "Author", description = "Represents an author in the library system")
=======
>>>>>>> d599a845c61de5c926be53327c33faca6a6cf504
public class Author {

    @Id
    @GeneratedValue
    @GenericGenerator(name = "uuid", strategy = "uuid2")
<<<<<<< HEAD
    @Schema(description = "Unique identifier of the author", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @Column(name = "name")
    @Schema(description = "Full name of the author", example = "Jane Austen")
    private String name;

    @Column(name = "biography")
    @Schema(description = "Short biography or background of the author", example = "English novelist known for works such as Pride and Prejudice.")
    private String biography;
=======
    private UUID id;
    @Column(name = "name")
    private String name;
    @Column(name = "biography")
    private String biography;

>>>>>>> d599a845c61de5c926be53327c33faca6a6cf504
}
