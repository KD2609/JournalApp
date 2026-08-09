package net.edigest.JournalAppNewest.Service;

import net.edigest.JournalAppNewest.entity.User;
import net.edigest.JournalAppNewest.repo.UserRepo;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest  {

    @Autowired
    private UserRepo userRepo;

    @ParameterizedTest
    @ValueSource(strings= {
            "Ram",
            "vipul"
    })
    public void testFindByUserName(String userName) {

        User user = userRepo.findByUserName(userName);

        System.out.println("USER = " + user);

        assertNotNull(user, "Failed for " + userName);
    }

    @Disabled
    @CsvSource({
            "1,1,2",
            "2,10,12",
            "3,3,3"

    })
    public void test(int a, int b, int expected) {
        assertEquals(expected, a + b);
    }

}
