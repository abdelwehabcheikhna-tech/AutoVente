package com.example.AutoVente.service;

import com.example.AutoVente.entity.User;
import com.example.AutoVente.enums.Role;
import com.example.AutoVente.repository.UserRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé: " + username));
    }

    public User registerNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ACHETEUR);
        user.setEnabled(true);
        return userRepository.save(user);
    }

    public User registerVendeur(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.VENDEUR);
        user.setEnabled(true);
        return userRepository.save(user);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    public void updateUser(Long id, User updatedUser) {
        User user = getUserById(id);
        user.setNom(updatedUser.getNom());
        user.setPrenom(updatedUser.getPrenom());
        user.setTelephone(updatedUser.getTelephone());
        user.setAdresse(updatedUser.getAdresse());
        userRepository.save(user);
    }

    public @Nullable Object count() {
        return null;
    }
}