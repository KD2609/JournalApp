package net.edigest.JournalAppNewest.Service;

import lombok.extern.slf4j.Slf4j;
import net.edigest.JournalAppNewest.entity.JournalEntry;
import net.edigest.JournalAppNewest.entity.User;
import net.edigest.JournalAppNewest.repo.JournalEntryRepo;
import net.edigest.JournalAppNewest.repo.UserRepo;
import org.bson.types.ObjectId;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;



@Component
@Slf4j
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MongoTemplate mongoTemplate;


    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public void saveEntry(User user) {
        System.out.println("DATABASE = " + mongoTemplate.getDb().getName());
        userRepo.save(user);
    }

    public void saveNewEntry(User user) {

        try {

            user.setPassword(encoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("User"));
            userRepo.save(user);
        }

        catch (Exception e) {
            log.error("Error occurred while saving user: {}" ,user.getUserName(),e);
            log.warn("Error occurred while saving user: {}" ,user.getUserName());
            log.info("Error occurred while saving user: {}" ,user.getUserName());
            log.debug("Error occurred while saving user: {}" ,user.getUserName());

        }
    }


    public List<User> getAll() {
        return userRepo.findAll();
    }

    public User findById(ObjectId myId){
        return userRepo.findById(myId).orElse(null);
    }

    public void deleteById(ObjectId myId){
        userRepo.deleteById(myId);
    }

    public User findByUserName(String username) {
        return userRepo.findByUserName(username);
    }

    public void saveNewAdmin(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("ADMIN"));
        userRepo.save(user);
    }

    public void makeAdmin(User user) {
        user.setRoles(Arrays.asList("ADMIN"));
        userRepo.save(user);
    }
}

//controller -> service -> repo ->
