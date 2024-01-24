package com.freshermanage.service;

import com.freshermanage.model.Center;
import com.freshermanage.model.Fresher;
import com.freshermanage.repository.FresherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class FresherService {
    @Autowired
    FresherRepository fresherRepository;

    @Autowired
    CenterService centerService;

    public List<Fresher> findAllFresher() {
        List<Object[]> result = fresherRepository.findAllFresher();
        List<Fresher> fresherList = new ArrayList<>();

        for (Object[] objects : result) {
            Fresher fresher = (Fresher) objects[0];
            Center center = null;

            if (objects[1] != null) {
                center = new Center();
                center.setId((Long) objects[1]);
                center.setNameCenter((String) objects[2]);
                center.setEmail((String) objects[3]);
                center.setPhone((String) objects[4]);
            }

            fresher.setCenter(center);
            fresherList.add(fresher);
        }

        return fresherList;
    }


    public void createFresher(Fresher fresher) {
        if (fresher.getCenter() != null) {
            Long centerId = fresher.getCenter().getId();
            Optional<Center> centerOptional = centerService.findById(centerId);

            if (centerOptional.isPresent()) {
                Center center = centerOptional.get();
                fresher.setCenter(center);
            } else {
                throw new RuntimeException("Center with id " + centerId + " not found");
            }
        }

        fresherRepository.save(fresher);
    }

    public Optional<Fresher> findFresherById(long id) {
        return fresherRepository.findById(id);
    }

    public void deleteFresher(Long id) {
        fresherRepository.deleteById(id);
    }

    public boolean existByIdFresher(long id) {
        return fresherRepository.existsById(id);
    }

    public Optional<Fresher> findByEmail(String email) {
        return fresherRepository.findByEmail(email);
    }

    public List<Fresher> findByLanguage(String language) {
        return fresherRepository.findByLanguage(language);
    }

    public Fresher findById(long id) {
        Optional<Fresher> optionalFresher = fresherRepository.findById(id);
        return optionalFresher.orElse(null);
    }

    public Float getAveragePoint(long id) {
        Fresher fresher = findById(id);
        if (fresher != null) {
            return fresher.getAveragePoint();
        } else {
            return null;
        }
    }

    public void editFresher(long id, Fresher fresher) {
        Optional<Fresher> optionalFresher = fresherRepository.findById(id);
        if (optionalFresher.isPresent()) {
            Fresher existingFresher = optionalFresher.get();

            existingFresher.setName(fresher.getName());
            existingFresher.setGender(fresher.getGender());
            existingFresher.setAge(fresher.getAge());
            existingFresher.setAddress(fresher.getAddress());
            existingFresher.setLanguage(fresher.getLanguage());
            existingFresher.setEmail(fresher.getEmail());
            existingFresher.setPoint1(fresher.getPoint1());
            existingFresher.setPoint2(fresher.getPoint2());
            existingFresher.setPoint3(fresher.getPoint3());

            Center newCenter = fresher.getCenter();
            if (newCenter != null) {
                Long centerId = newCenter.getId();
                Optional<Center> centerOptional = centerService.findById(centerId);

                if (centerOptional.isPresent()) {
                    Center center = centerOptional.get();
                    existingFresher.setCenter(center);
                } else {
                    throw new RuntimeException("Center with id " + centerId + " not found");
                }
            } else {
                existingFresher.setCenter(null);
            }
            existingFresher.calculateAveragePoint();
            fresherRepository.save(existingFresher);
        } else {
            throw new RuntimeException("Fresher with id " + id + " not found.");
        }
    }
    public Map<String, Long> getFresherCountByPointRanges() {
        Map<String, Long> pointRanges = new HashMap<>();

        long excellentCount = fresherRepository.countByAveragePointGreaterThanEqual(8.0f);
        long goodCount = fresherRepository.countByAveragePointBetween(6.5f, 8.0f);
        long averageCount = fresherRepository.countByAveragePointBetween(5.0f, 6.5f);
        long belowAverageCount = fresherRepository.countByAveragePointBetween(3.5f, 5.0f);
        long weakCount = fresherRepository.countByAveragePointLessThan(3.5f);

        pointRanges.put("Excellent (>= 8.0)", excellentCount);
        pointRanges.put("Good (6.5 - 7.9)", goodCount);
        pointRanges.put("Average (5.0 - 6.4)", averageCount);
        pointRanges.put("Below Average (3.5 - 4.9)", belowAverageCount);
        pointRanges.put("Weak (< 3.5)", weakCount);

        return pointRanges;
    }
}

