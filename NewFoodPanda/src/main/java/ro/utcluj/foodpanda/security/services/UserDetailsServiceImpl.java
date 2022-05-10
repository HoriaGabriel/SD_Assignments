package ro.utcluj.foodpanda.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.utcluj.foodpanda.model.User;
import ro.utcluj.foodpanda.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try{
            User user = userRepository.findByUsername(username);
            return UserDetailsImpl.build(user);
        } catch(UsernameNotFoundException e){
             System.out.println("User Not Found with username: " + username);
             return null;
        }
    }

}

