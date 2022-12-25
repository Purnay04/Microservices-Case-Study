package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookView implements Cloneable, Serializable {
    private String id;
    private String name;
    private String author;
    private long available_copies;
    private long total_copies;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
