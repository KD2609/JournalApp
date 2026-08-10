package net.edigest.JournalAppNewest.repo;

import net.edigest.JournalAppNewest.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserRepoImpl {

    @Autowired
    private MongoTemplate mongoTemplate;


    public List<User> getUsersForSA() {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));

        return mongoTemplate.find(query, User.class);
    }
}
