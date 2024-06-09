package com.codedecode.order.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "sequence")
//When using MongoDB with Spring Data, you don't use
// the @Entity annotation to define your domain objects (entities).
//Instead, you typically use the @Document annotation
// to indicate that a class is a MongoDB document.
public class Sequence {
    @Id
    private String id;
    private int sequence;
}
