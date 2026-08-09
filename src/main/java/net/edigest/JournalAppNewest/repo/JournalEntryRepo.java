package net.edigest.JournalAppNewest.repo;

import net.edigest.JournalAppNewest.entity.JournalEntry;
import net.edigest.JournalAppNewest.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepo extends MongoRepository<JournalEntry, ObjectId>{
}