package org.example.user.service;

import jakarta.transaction.Transactional;
import org.example.user.model.entities.Doctor;
import org.example.user.model.entities.Doctor;
import org.example.user.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private DoctorRepository userRepository;  // Repo for user queries

    @Override
    @Transactional  // Ensure transactional for DB ops
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Load user by email (username is email)
        Doctor user = userRepository.findByEmail(username);
        System.out.println("Loading user by username: " + user);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }
        return user;  // User implements UserDetails
    }
}
