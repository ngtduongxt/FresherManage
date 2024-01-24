package com.freshermanage.service;

import com.freshermanage.model.Center;
import com.freshermanage.model.Fresher;
import com.freshermanage.model.dto.CenterFresherCountDTO;
import com.freshermanage.repository.CenterRepository;
import com.freshermanage.repository.FresherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CenterService {

    @Autowired
    private CenterRepository centerRepository;

    @Autowired
    private FresherRepository fresherRepository;

    public List<Center> findAllCenter() {
        return centerRepository.findAllParentCentersWithChildren();
    }

    public Optional<Center> findById(Long id) {
        return centerRepository.findById(id);
    }

    public void save(Center center) {
        centerRepository.save(center);
    }

    public void deleteCenter(long id) {
        centerRepository.deleteById(id);
    }

    public void transferFresherToCenter(long fresherId, long newCenterId) {
        Fresher fresher = fresherRepository.findById(fresherId).orElse(null);
        Center newCenter = centerRepository.findById(newCenterId).orElse(null);

        if (fresher != null && newCenter != null) {
            fresher.setCenter(newCenter);
            fresherRepository.save(fresher);
        }
    }

    public List<Object[]> getFresherCountsByCenterWithCenters() {
        return fresherRepository.getFresherCountsByCenterWithCenters();
    }

    public void editCenter(long centerId, Center center) {
        Optional<Center> optionalCenter = centerRepository.findById(centerId);
        if (optionalCenter.isPresent()) {
            Center existingCenter = optionalCenter.get();

            existingCenter.setNameCenter(center.getNameCenter());
            existingCenter.setPhone(center.getPhone());
            existingCenter.setEmail(center.getEmail());

            centerRepository.save(existingCenter);
        } else
            throw new RuntimeException("Center with id " + centerId + " not found");
    }

    public void moveCenter(long centerId, Long newParentId) {
        Optional<Center> optionalCenter = centerRepository.findById(centerId);
        if (!optionalCenter.isPresent()) {
            throw new RuntimeException("Center with id " + centerId + " not found");
        }

        Center newParent = null;
        if (newParentId != null) {
            Optional<Center> optionalNewParent = centerRepository.findById(newParentId);
            if (!optionalNewParent.isPresent()) {
                throw new RuntimeException("New parent with id " + newParentId + " not found");
            }
            newParent = optionalNewParent.get();
        }

        Center centerToMove = optionalCenter.get();

        centerToMove.setParentCenter(newParent);

        centerRepository.save(centerToMove);
    }
}
