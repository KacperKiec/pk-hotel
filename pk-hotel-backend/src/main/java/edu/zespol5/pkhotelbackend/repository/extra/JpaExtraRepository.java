package edu.zespol5.pkhotelbackend.repository.extra;

import edu.zespol5.pkhotelbackend.model.Extra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
interface JpaExtraRepository extends JpaRepository<Extra, Integer>, JpaSpecificationExecutor<Extra>, ExtraRepository {
}
