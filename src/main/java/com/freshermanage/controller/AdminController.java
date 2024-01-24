package com.freshermanage.controller;

import com.freshermanage.model.dto.UserDTO;
import com.freshermanage.payload.response.MessageResponse;
import com.freshermanage.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/admin")
@Api(tags = "Admin Site")
@PreAuthorize("hasRole('ADMIN') and @customUserDetailService.activeUser")
public class AdminController {

    @Autowired
    private AdminService adminSiteService;

    @GetMapping()
    @ApiOperation(value = "Get All User", notes = "Get All User.")
    public ResponseEntity<?> findAllUser() {
        List<UserDTO> userDTOS = adminSiteService.findAllUser();
        if (!userDTOS.isEmpty()) {
            return ResponseEntity.ok(userDTOS);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("No User Found"));
        }
    }

    @PostMapping("/activateUser/{username}")
    @ApiOperation(value = "Activate User", notes = "Activate User.")
    public ResponseEntity<?> activateUserAccount(@PathVariable String username) {
        try {
            adminSiteService.activateUser(username);
            return ResponseEntity.ok(new MessageResponse("User activated successfully!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Account Not Found"));
        }
    }

    @PostMapping("/lockUser/{username}")
    @ApiOperation(value = "Lock User", notes = "Lock User.")
    public ResponseEntity<?> lockUserAccount(@PathVariable String username) {
        try {
            adminSiteService.lockUser(username);
            return ResponseEntity.ok(new MessageResponse("User locked successfully!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Account Not Found"));
        }
    }

    @GetMapping("/deleteUser/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable long userId) {
        try {
            adminSiteService.deleteUser(userId);
            return ResponseEntity.ok(new MessageResponse("Delete User Successfully!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Account Not Found"));
        }
    }
}
