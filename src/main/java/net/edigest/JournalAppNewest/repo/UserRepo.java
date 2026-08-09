package net.edigest.JournalAppNewest.repo;

import net.edigest.JournalAppNewest.entity.JournalEntry;
import net.edigest.JournalAppNewest.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<User, ObjectId>{

    User findByUserName(String username);
    void deleteByUserName(String username);
}