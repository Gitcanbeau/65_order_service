package com.codedecode.order.service;

import com.codedecode.order.entity.Sequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class SequenceGenerator {

    @Autowired
    private MongoOperations mongoOperations;
    //MongoOperations is a Spring Data MongoDB interface that provides methods for
    // interacting with MongoDB in a more abstract and convenient way than the raw MongoDB driver.

    public int generateNextOrderId(){
        Sequence counter = mongoOperations.findAndModify(
                query(where("_id").is("sequence")),
                new Update().inc("sequence", 1),
                options().returnNew(true).upsert(true),
                Sequence.class);
        return counter.getSequence();

    }
    ///The findAndModify method is used to find a document, modify it, and return the modified document.
    //
    //Query query: Specifies the criteria for selecting the document to modify.
    // Here,it looks for the document with _id set to "sequence".
    //Update update: Specifies the modification to apply to the document.
    // new Update().inc("sequence", 1) increments the sequence field by 1.
    //FindAndModifyOptions options: Specifies additional options for the operation.
    // options().returnNew(true).upsert(true) means it returns the updated document (returnNew(true))
    // and creates a new document if none is found (upsert(true)).
    //Class<T> entityClass: The class type of the document. In this case, it's Sequence.class.

}
