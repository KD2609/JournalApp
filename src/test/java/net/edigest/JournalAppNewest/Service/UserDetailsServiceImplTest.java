package net.edigest.JournalAppNewest.Service;

import net.edigest.JournalAppNewest.repo.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.edigest.JournalAppNewest.entity.User;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ActiveProfiles("dev")
public class UserDetailsServiceImplTest {

    @InjectMocks
    private UserDetailServiceImpl userDetailServiceImpl;

    @Mock
    private UserRepo userRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void loadUserByUsername() {

        when(userRepo.findByUserName(ArgumentMatchers.anyString()))
                .thenReturn(
                        User.builder()
                                .userName("Ram")
                                .password("jbja")
                                .roles(new ArrayList<>())
                                .build()
                );

        UserDetails user =
                userDetailServiceImpl.loadUserByUsername("Ram");
    }
}
