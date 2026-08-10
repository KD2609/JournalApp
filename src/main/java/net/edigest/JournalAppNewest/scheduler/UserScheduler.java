package net.edigest.JournalAppNewest.scheduler;

import net.edigest.JournalAppNewest.Service.EmailService;
import net.edigest.JournalAppNewest.Service.SentimentAnalysisService;

import net.edigest.JournalAppNewest.cache.AppCache;
import net.edigest.JournalAppNewest.entity.JournalEntry;
import net.edigest.JournalAppNewest.entity.User;
import net.edigest.JournalAppNewest.repo.UserRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepoImpl userRepo;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    private AppCache appCache;


    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUserAndSaMail(){
        List<User> users = userRepo.getUsersForSA();
        for(User user : users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<String> filteredEntries = journalEntries
                    .stream()
                    .filter(x -> x.getDate()
                            .isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
                    .map(x -> x.getContent())
                    .collect(Collectors.toList());

            String entry = String.join(", ", filteredEntries);
            String sentiment = sentimentAnalysisService.getSentiment(entry);

            emailService.sendEmail(user.getEmail(), "sentiment for last 7 days", sentiment);
        }
    }

    @Scheduled(cron = "0 0/10 */ * *")
    public void clearAppCache(){
        appCache.init();
    }


}
