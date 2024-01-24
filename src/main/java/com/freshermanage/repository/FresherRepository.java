package com.freshermanage.repository;

import com.freshermanage.model.Fresher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FresherRepository extends JpaRepository<Fresher, Long> {
    boolean existsById(long id);

    @Query("SELECT f, c.id, c.nameCenter, c.email, c.phone FROM Fresher f LEFT JOIN f.center c")
    List<Object[]> findAllFresher();

    @Query("SELECT f FROM Fresher f WHERE f.language = :language")
    List<Fresher> findByLanguage(@Param("language") String language);

    @Query("SELECT f from Fresher f WHERE f.email = :email")
    Optional<Fresher> findByEmail(@Param("email") String email);

    @Query("SELECT c, COUNT(f.id) " +
            "FROM Center c LEFT JOIN Fresher f ON c.id = f.center.id " +
            "GROUP BY c.id, c.nameCenter")
    List<Object[]> getFresherCountsByCenterWithCenters();

    long countByAveragePointGreaterThanEqual(float minAveragePoint);

    long countByAveragePointBetween(float minAveragePoint, float maxAveragePoint);

    long countByAveragePointLessThan(float maxAveragePoint);
}
