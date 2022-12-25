package org.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Subscription {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="course_seq")
    @SequenceGenerator(
            name="course_seq",
            sequenceName="course_sequence",
            allocationSize=20
    )
    private Long id;
    private String subscriberName;
    private String book_id;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date date_subscribed;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date date_returned;
}
