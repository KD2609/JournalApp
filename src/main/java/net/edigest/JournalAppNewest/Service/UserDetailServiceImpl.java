package net.edigest.JournalAppNewest.Service;

import net.edigest.JournalAppNewest.entity.User;
import net.edigest.JournalAppNewest.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUserName(username);

        if(user != null){

            System.out.println("USERNAME = " + user.getUserName());
            System.out.println("ROLES FROM DB = " + user.getRoles());

            UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUserName())
                    .password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0]))
                    .build();

            System.out.println("AUTHORITIES = " + userDetails.getAuthorities());

            return userDetails;
        }
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
