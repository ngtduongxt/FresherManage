package com.freshermanage.service;

import com.freshermanage.model.Users;
import com.freshermanage.model.dto.UserUpdateDTO;
import com.freshermanage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Users findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }


    public boolean existsByUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void save(Users user) {
        userRepository.save(user);
    }

    public void updateUser(UserUpdateDTO userUpdateDTO) {
        Users user = userRepository.findById(userUpdateDTO.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (userUpdateDTO.getFullName() != null) {
            user.setFullName(userUpdateDTO.getFullName());
        }

        if (userUpdateDTO.getEmail() != null) {
            user.setEmail(userUpdateDTO.getEmail());
        }

        userRepository.save(user);
    }

    public void changePassword(Long userId, String oldPassword, String newPassword) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        String hashedPassword = passwordEncoder.encode(newPassword);

        user.setPassword(hashedPassword);
        userRepository.save(user);
    }
}