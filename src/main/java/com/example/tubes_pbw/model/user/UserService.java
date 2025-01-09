package com.example.tubes_pbw.model.user;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean register(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public User login(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);

        if (!user.isEmpty() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user.get();
        }
        return null;
    }

    public List<User> findAllUsers (){
        return userRepository.findAll();
    }
    
    public PenggunaSetlist findInSetlist(String email, int idSetlist){
        Optional<PenggunaSetlist> user = userRepository.findInSetlist(email,idSetlist);

        if (!user.isEmpty()) {
            return user.get();
        }
        return null;
    }

    public void addToPenggunaSetlist(String email, int idSetlist){
        userRepository.addToPenggunaSetlist(email, idSetlist);
    }

    public void removeFromPenggunaSetlist(String email, int idSetlist){
        userRepository.removeFromPenggunaSetlist(email, idSetlist);
    }
}
