package com.freshermanage.service;

import com.freshermanage.model.Users;
import com.freshermanage.model.dto.UserDTO;
import com.freshermanage.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminSiteRepository;

    @Autowired
    private UserService userService;


    public List<UserDTO> findAllUser() {
        List<Users> users = adminSiteRepository.findAll();
        return convertToUserDTO(users);
    }

    private List<UserDTO> convertToUserDTO(List<Users> users) {
        return users.stream()
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getUserName(),
                        user.getFullName(),
                        user.getEmail(),
                        user.isUserStatus(),
                        new ArrayList<>(user.getListRole())
                ))
                .collect(Collectors.toList());
    }
    public void deleteUser(long id) {
        adminSiteRepository.deleteById(id);
    }

    public void activateUser(String username) {
        Users user = userService.findByUserName(username);

        user.setUserStatus(true);
        userService.save(user);
    }
    public void lockUser(String username) {
        Users users = userService.findByUserName(username);
        users.setUserStatus(false);
        userService.save(users);
    }
}
