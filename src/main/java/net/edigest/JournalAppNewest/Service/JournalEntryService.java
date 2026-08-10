package net.edigest.JournalAppNewest.Service;

import lombok.extern.slf4j.Slf4j;
import net.edigest.JournalAppNewest.entity.JournalEntry;
import net.edigest.JournalAppNewest.entity.User;
import net.edigest.JournalAppNewest.repo.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;
@Slf4j
@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepo journalEntryRepo;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserService userService;




    public void saveEntry(JournalEntry journalEntry, String userName) {
        try {
            User savedUser = userService.findByUserName(userName);
            System.out.println("DATABASE = " + mongoTemplate.getDb().getName());
            JournalEntry saved = journalEntryRepo.save(journalEntry);
            savedUser.getJournalEntries().add(saved);
            userService.saveEntry(savedUser);
        } catch(Exception e){

            throw new RuntimeException("An Error occured while saving into the database");
        }
    }

    public void saveEntry(JournalEntry journalEntry) {

        journalEntryRepo.save(journalEntry);

    }


    public List<JournalEntry> getAll() {
        return journalEntryRepo.findAll();
    }

    public JournalEntry findById(ObjectId myId){
        return journalEntryRepo.findById(myId).orElse(null);
    }

    public void deleteById(ObjectId myId, String username){
        try {
            User savedUser = userService.findByUserName(username);
            boolean removed = savedUser.getJournalEntries().removeIf(x -> x.getId().equals(myId));

            if (removed) {
                userService.saveEntry(savedUser);
                journalEntryRepo.deleteById(myId);
            }
        }catch(Exception e){
            log.error("An Error occured while deleting from the database",e);
            throw new RuntimeException("An error occured while deleting the entry.",e);

        }

    }

    public List<JournalEntry> findByUserName(String username){
            return null;
    }
}

//controller -> service -> repo ->
