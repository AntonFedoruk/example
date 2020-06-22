package com.example.sweater.service;

import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailSender mailSender;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public boolean addUser(User user){
        User userFromDB = userRepository.findByUsername(user.getUsername());

        //check if user is already exists
        if (userFromDB != null) {
            return false;
        }

        user.setActive(true);
        //Collections.singleton() creates Set with 1 value
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());

        userRepository.save(user);

        //check if email  is empty
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n"+
                            "Welcome to Sweater. Please, visit next link: http://localhost:8080/activate/%s", /* in future link could be replaced to .property */
                    user.getUsername(),
                    user.getActivationCode());
            mailSender.send(user.getEmail(),"Activation code", message);
        }
        return true;
    }

    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);

        //activation failed
        if (user == null) {
            return false;
        }

        user.setActivationCode(null); // it`s mean that user confirmed email
        userRepository.save(user);

        return true;
    }
}

