package net.engineeringdigest.journalApp.services;

import com.mongodb.client.model.Collation;
import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.UserEntry;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class UserService {
    @Autowired
    UserRepository userRepository;

    public final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<UserEntry> getAll(){
        return  userRepository.findAll();
    }

    public void saveNewUser(UserEntry userEntry){
        try{
        userEntry.setPassword(passwordEncoder.encode(userEntry.getPassword()));
        userEntry.setRoles(Arrays.asList("user"));
        userRepository.save(userEntry);}
        catch (Exception e){
            log.error("error is coming {}",userEntry.getUserName(),e);
            log.info("information dene hai ",userEntry.getUserName(),e);
        }
    }

    public void saveUser(UserEntry userEntry){
        userRepository.save(userEntry);
    }

    public Optional<UserEntry> findById(ObjectId id){
        return userRepository.findById(id);
    }
    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }

    public UserEntry findByUserName(String username){
        return userRepository.findByUserName(username);
    }

    public void saveAdmin(UserEntry userEntry){
        userEntry.setPassword(passwordEncoder.encode(userEntry.getPassword()));
        userEntry.setRoles(Arrays.asList("user","admin"));
        userRepository.save(userEntry);
    }
}
