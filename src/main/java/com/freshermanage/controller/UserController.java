package com.freshermanage.controller;

import com.freshermanage.config.jwt.JwtTokenProvider;
import com.freshermanage.model.ERole;
import com.freshermanage.model.Roles;
import com.freshermanage.model.Users;
import com.freshermanage.model.dto.UserUpdateDTO;
import com.freshermanage.payload.request.LoginRequest;
import com.freshermanage.payload.request.SignupRequest;
import com.freshermanage.payload.response.JwtResponse;
import com.freshermanage.payload.response.MessageResponse;
import com.freshermanage.config.security.CustomUserDetails;
import com.freshermanage.service.RoleService;
import com.freshermanage.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
@Api(tags = "User Management")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/signup")
    @ApiOperation(value = "Register new user", notes = "Allows users to register with the system.")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        if (userService.existsByUserName(signupRequest.getUserName())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Username is already!"));
        }
        if (userService.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Email is already!"));
        }
        Users user = new Users();
        user.setUserName(signupRequest.getUserName());
        user.setPassword(encoder.encode(signupRequest.getPassword()));
        user.setFullName(signupRequest.getFullName());
        user.setEmail(signupRequest.getEmail());
        Set<String> strRole = signupRequest.getListRoles();
        Set<Roles> listRoles = new HashSet<>();

        if (strRole == null || strRole.isEmpty()) {
            Roles userRole = roleService.findByRoleName(ERole.ROLE_MANAGE).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            listRoles.add(userRole);
        } else {
            strRole.forEach(role -> {
                switch (role) {
                    case "admin":
                        Roles adminRole = roleService.findByRoleName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: Role"));
                        listRoles.add(adminRole);
                        break;
                    case "manage":
                        Roles fresherRole = roleService.findByRoleName(ERole.ROLE_MANAGE).orElseThrow(() -> new RuntimeException("Error"));
                        listRoles.add(fresherRole);
                        break;
                }
            });
        }
        user.setListRole(listRoles);
        userService.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/signin")
    @ApiOperation(value = "Login user", notes = "Allows users to log in and receive a JWT token.")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String jwt = tokenProvider.generateToken(customUserDetails);
        List<String> listRole = customUserDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt, customUserDetails.getUsername(), customUserDetails.getEmail(), customUserDetails.isUserStatus(), listRole));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody UserUpdateDTO userUpdateDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();

            Long userId = userService.findByUserName(username).getId();

            userUpdateDTO.setId(userId);

            userService.updateUser(userUpdateDTO);

            return ResponseEntity.ok("User updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @RequestParam String oldPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            Users user = userService.findByUserName(authentication.getName());

            if (!newPassword.equals(confirmPassword)) {
                return ResponseEntity.badRequest().body("New password and confirm password do not match");
            }

            userService.changePassword(user.getId(), oldPassword, newPassword);

            SecurityContextHolder.clearContext();

            return ResponseEntity.ok("Password changed successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}