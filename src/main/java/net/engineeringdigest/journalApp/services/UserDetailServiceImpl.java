package net.engineeringdigest.journalApp.services;

import net.engineeringdigest.journalApp.entity.UserEntry;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntry userEntry= userRepository.findByUserName(username);
        if(userEntry!=null){
            UserDetails userDetails= User.builder().username(userEntry.getUserName())
                    .password(userEntry.getPassword())
                    .roles(userEntry.getRoles().toArray(new String[userEntry.getRoles().size()]))
                    .build();
            return userDetails;
        }
        else {
            throw new UsernameNotFoundException("user not found "+username);
        }
    }
}
