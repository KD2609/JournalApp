package net.edigest.JournalAppNewest.Controller;

import net.edigest.JournalAppNewest.Service.JournalEntryService;
import net.edigest.JournalAppNewest.Service.UserService;
import net.edigest.JournalAppNewest.entity.JournalEntry;
import net.edigest.JournalAppNewest.entity.User;
import net.edigest.JournalAppNewest.repo.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;



    @Autowired
    private UserRepo userRepo;


    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User userIndb = userService.findByUserName(username);


        userIndb.setPassword(user.getPassword());
        userService.saveNewEntry(userIndb);

        return new  ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserId(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        userRepo.deleteByUserName(username);
        return new  ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
