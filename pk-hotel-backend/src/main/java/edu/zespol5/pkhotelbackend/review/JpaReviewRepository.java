package edu.zespol5.pkhotelbackend.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
interface JpaReviewRepository extends JpaRepository<Review, Integer>, JpaSpecificationExecutor<Review>, ReviewRepository {

}
