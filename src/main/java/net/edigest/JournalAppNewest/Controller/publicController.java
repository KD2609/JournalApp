package net.edigest.JournalAppNewest.Controller;

import net.edigest.JournalAppNewest.Service.UserService;
import net.edigest.JournalAppNewest.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class publicController {

    @Autowired
    private UserService userService;


    @PostMapping("/create-user")
    public void createUser(@RequestBody User user){
        userService.saveNewEntry(user);
    }

}
