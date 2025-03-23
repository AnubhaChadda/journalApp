package net.engineeringdigest.journalApp.services;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.UserEntry;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Slf4j
@Component
public class JournalEntryService {

    private static final Logger log = LoggerFactory.getLogger(JournalEntryService.class);
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    UserService userService;

    public List<JournalEntry> getAll(){
        return  journalEntryRepository.findAll();
    }
    @Transactional
    public void saveEntry(JournalEntry journalEntry,String userName){
        try {
            UserEntry userEntry=userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved= journalEntryRepository.save(journalEntry);
            userEntry.getJournalEntries().add(saved);
            userService.saveUser(userEntry);
        }
        catch (Exception e){
            System.out.println("error aa gya");
            throw new RuntimeException("an error occured while creating the entry");
        }
    }
    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }
    @Transactional
    public boolean deleteById(ObjectId id,String userName){
        try{
            UserEntry user = userService.findByUserName(userName);
            boolean removed = user.getJournalEntries().removeIf(x->x.getId().equals(id));
            if(removed){
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
            }
            return removed;
        }
        catch (Exception e){
            log.error("Error",e);
            throw new RuntimeException("An error occured while deleting the netry",e);
        }
    }

    public List<JournalEntry> findByUsername(String username){
        UserEntry userEntry=userService.findByUserName(username);
        return userEntry.getJournalEntries();
    }

}
