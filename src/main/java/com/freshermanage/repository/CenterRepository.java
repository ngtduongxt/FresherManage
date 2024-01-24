package com.freshermanage.repository;

import com.freshermanage.model.Center;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CenterRepository extends JpaRepository<Center, Long> {

    @Query("SELECT DISTINCT c FROM Center c LEFT JOIN FETCH c.childCenter WHERE c.parentCenter IS NULL")
    List<Center> findAllParentCentersWithChildren();

}
