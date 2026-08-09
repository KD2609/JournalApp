package net.edigest.JournalAppNewest.Controller;

import net.edigest.JournalAppNewest.Service.JournalEntryService;
import net.edigest.JournalAppNewest.Service.UserService;
import net.edigest.JournalAppNewest.entity.JournalEntry;
import net.edigest.JournalAppNewest.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.findByUserName(username);
        List<JournalEntry> all = user.getJournalEntries();
        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) {


        try{

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry,username);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);

        }
        catch(Exception e){

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


        }

    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUserName(username);
        List<JournalEntry> collect  = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());

        if(!collect.isEmpty()){
            return new ResponseEntity<>(collect.get(0), HttpStatus.OK);
        }


       return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId myId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        JournalEntry old = journalEntryService.findById(myId);


        if (old != null) {
            journalEntryService.deleteById(myId,username);
            return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Journal not found");
    }

    @PutMapping("id/{myId}")
    public ResponseEntity< JournalEntry>  updateJournalById(@PathVariable ObjectId myId, @RequestBody JournalEntry myEntry){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUserName(username);

        List<JournalEntry> collect  = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());


        if(!collect.isEmpty()){

          JournalEntry journalEntry = collect.get(0);

          if(journalEntry != null){

              journalEntry.setTitle(myEntry.getTitle());
              journalEntry.setContent(myEntry.getContent());
              journalEntryService.saveEntry(journalEntry);
              return new ResponseEntity<>(journalEntry,HttpStatus.OK);
          }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
