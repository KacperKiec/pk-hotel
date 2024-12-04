package edu.zespol5.pkhotelbackend.repository.extra;

import edu.zespol5.pkhotelbackend.model.Extra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExtraRepository {
    Extra save(Extra extra);
    Optional<Extra> findExtraById(int id);
    Page<Extra> findAll(Pageable pageable);
    List<Extra> findAll(Specification<Extra> spec);
    void deleteById(int id);

    @Query("SELECT re.extra FROM ReservationExtra re WHERE re.reservation.id = :id")
    List<Extra> findExtrasByReservationId(@Param("id") int id);
}
