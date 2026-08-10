package net.edigest.JournalAppNewest.Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    EmailService emailService;

    @Test
    public void testEmailService() {
        emailService.sendEmail("kandpaldipesh@gmail.com","Testing java mail Sender","Hi app kese hai");
    }
}
