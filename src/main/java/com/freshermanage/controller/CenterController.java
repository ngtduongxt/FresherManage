package com.freshermanage.controller;

import com.freshermanage.model.Center;
import com.freshermanage.model.Fresher;
import com.freshermanage.model.dto.CenterFresherCountDTO;
import com.freshermanage.model.dto.MoveCenterRequest;
import com.freshermanage.service.CenterService;
import com.freshermanage.service.FresherService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/centers")
@Api(tags = "Center Management")
public class CenterController {
    @Autowired
    private CenterService centerService;

    @Autowired
    private FresherService fresherService;

    @GetMapping
    public List<Center> findAllCenter() {
        return centerService.findAllCenter();
    }

    @GetMapping("/{id}")
    public Optional<Center> findByIdCenterForTree(@PathVariable long id) {
        return centerService.findById(id);
    }


    @PostMapping("/create")
    public ResponseEntity<?> createCenter(@RequestBody Center center) {
        centerService.save(center);
        return ResponseEntity.ok().body("Create Successfully!");
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteCenter(@PathVariable long id) {
        centerService.deleteCenter(id);
        return ResponseEntity.ok().body("Delete Successfully!");
    }

    @PostMapping("/{centerId}/transferFresher/{fresherId}")
    public ResponseEntity<?> transferFresher(@PathVariable long centerId, @PathVariable long fresherId) {
        centerService.transferFresherToCenter(fresherId, centerId);
        return ResponseEntity.ok("Fresher transferred successfully!");
    }

    @GetMapping("/fresher-counts")
    public ResponseEntity<List<CenterFresherCountDTO>> getFresherCountsByCenterWithCenters() {
        try {
            List<Object[]> result = centerService.getFresherCountsByCenterWithCenters();

            List<CenterFresherCountDTO> dtoList = new ArrayList<>();
            for (Object[] row : result) {
                Center center = (Center) row[0];
                Long quantityFresher = (Long) row[1];
                dtoList.add(new CenterFresherCountDTO(center.getNameCenter(), quantityFresher));
            }

            return ResponseEntity.ok(dtoList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }

    @PutMapping("/editCenter/{centerId}")
    public ResponseEntity<?> editCenter(@PathVariable long centerId, @RequestBody Center center) {
        centerService.editCenter(centerId, center);
        return ResponseEntity.ok("Center update successfully!");
    }

    @PutMapping("/{centerId}/moveCenter/{parentCenterId}")
    public ResponseEntity<?> moveCenter(@PathVariable long centerId, @RequestBody MoveCenterRequest request) {
        Long newParentId = request.getNewParentId();
        centerService.moveCenter(centerId, newParentId);
        return ResponseEntity.ok("Move Center Successfully!");
    }
}
