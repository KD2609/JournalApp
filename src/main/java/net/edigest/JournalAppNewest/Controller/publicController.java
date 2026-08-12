package net.edigest.JournalAppNewest.Controller;

import lombok.extern.slf4j.Slf4j;
import net.edigest.JournalAppNewest.Service.UserDetailServiceImpl;
import net.edigest.JournalAppNewest.Service.UserService;

import net.edigest.JournalAppNewest.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/public")
public class publicController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailServiceImpl userDetailsService;


    @Autowired
    private net.engineeringdigest.journalApp.utilis.JWTUtils jwtUtils;

    @PostMapping("/signup")
    public void createUser(@RequestBody User user){
        userService.saveNewEntry(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
            String jwt = jwtUtils.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);

        } catch(Exception e){
            log.error("Exception occurred while createAuthentication", e);
            return new ResponseEntity<>("Incorrect username or password",HttpStatus.UNAUTHORIZED);
        }
    }

}
