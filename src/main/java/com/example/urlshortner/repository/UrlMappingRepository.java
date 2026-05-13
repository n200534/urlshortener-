package com.example.urlshortner.repository;

import com.example.urlshortner.entity.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.urlshortner.entity.User;
import java.util.List;
import java.util.Optional;

public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {

    Optional<UrlMapping> findByShortKey(String shortKey);

    boolean existsByShortKey(String shortKey);

    @Query(value = "SELECT nextval('url_sequence')", nativeQuery = true)
    Long getNextSequenceValue();

    List<UrlMapping> findByUser(User user);

}
