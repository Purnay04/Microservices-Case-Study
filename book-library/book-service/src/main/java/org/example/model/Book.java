package org.example.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity public class Book {
    @Id
    private String id;
    private String name;
    private String author;
    private long available_copies;
    private long total_copies;
}
