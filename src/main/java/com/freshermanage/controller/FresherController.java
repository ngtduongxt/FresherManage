package com.freshermanage.controller;

import com.freshermanage.model.Fresher;
import com.freshermanage.payload.response.MessageResponse;
import com.freshermanage.service.FresherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/freshers")
@Api(tags = "Fresher Management")
public class FresherController {
    @Autowired
    private FresherService fresherService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGE') and @customUserDetailService.activeUser")
    @ApiOperation(value = "Get All Fresher", notes = "Get All Fresher.")
    public ResponseEntity<?> getAllFresher() {
        List<Fresher> freshers = fresherService.findAllFresher();
        if (freshers == null || freshers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Fresher list is empty"));
        } else {
            return ResponseEntity.ok(freshers);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGE') and @customUserDetailService.activeUser")
    @ApiOperation(value = "Get fresher by ID", notes = "Get a fresher by ID.")
    public ResponseEntity<?> findFresherById(@PathVariable("id") @ApiParam(value = "Fresher ID", required = true) long id) {
        Optional<Fresher> fresher = fresherService.findFresherById(id);
        if (fresher.isPresent()) {
            return ResponseEntity.ok(fresher.get());
        } else {
            return ResponseEntity.status(404).body(new MessageResponse("Fresher Not Found"));
        }
    }

    @GetMapping("/delete/{fresherId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGE') and @customUserDetailService.activeUser")
    @ApiOperation(value = "Delete fresher by ID", notes = "Delete a fresher by ID.")
    public ResponseEntity<?> deleteFresher(@PathVariable @ApiParam long fresherId) {
        try {
            if (!fresherService.existByIdFresher(fresherId)) {
                return ResponseEntity.status(404).body(new MessageResponse("Fresher Not Found"));
            } else {
                fresherService.deleteFresher(fresherId);
            }
            return ResponseEntity.ok(new MessageResponse("Fresher delete successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new MessageResponse("Error delete fresher"));
        }
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGE') and @customUserDetailService.activeUser")
    @ApiOperation(value = "Create a fresher", notes = "Create a fresher.")
    public ResponseEntity<?> createFresher(@RequestBody Fresher fresher) {
        fresherService.createFresher(fresher);
        return ResponseEntity.ok().body("Create Successfully!");
    }

    @GetMapping("/searchEmail")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGE') and @customUserDetailService.activeUser")
    @ApiOperation(value = "Find By Mail", notes = "Find By Mail.")
    public ResponseEntity<?> findByEmail(@RequestParam String email) {
        Optional<Fresher> result = fresherService.findByEmail(email);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("No fresher found with email: " + email));
    }

    @GetMapping("/searchLanguage")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGE') and @customUserDetailService.activeUser")
    @ApiOperation(value = "Find By Language", notes = "Find By Language.")
    public ResponseEntity<?> findByLanguage(@RequestParam String language) {
        List<Fresher> result = fresherService.findByLanguage(language);
        if (!result.isEmpty()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("No fresher found with language: " + language));
        }
    }

    @GetMapping("/getAveragePoint/{fresherId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGE') and @customUserDetailService.activeUser")
    @ApiOperation(value = "Average Point Fresher", notes = "Average Point Fresher")
    public ResponseEntity<?> getAveragePoint(@PathVariable @ApiParam long fresherId) {
        Float averagePoint = fresherService.getAveragePoint(fresherId);
        if (averagePoint != null) {
            return ResponseEntity.ok("Average Point for Fresher ID " + fresherId + ": " + averagePoint);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Fresher not found by ID " + fresherId));
        }
    }

    @PutMapping("/{fresherId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGE') and @customUserDetailService.activeUser")
    @ApiOperation(value = "Edit Fresher", notes = "Edit Fresher")
    public ResponseEntity<String> editFresher(@PathVariable("fresherId") @ApiParam long fresherId, @RequestBody Fresher fresher) {
        try {
            fresherService.editFresher(fresherId, fresher);
            return new ResponseEntity<>("Fresher with ID " + fresherId + " has been successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/point-ranges/count")
    public ResponseEntity<Map<String, Long>> getFresherCountByPointRanges() {
        Map<String, Long> pointRanges = fresherService.getFresherCountByPointRanges();
        return ResponseEntity.ok(pointRanges);
    }
}
