package org.ureca.pinggubackend.domain.location.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ureca.pinggubackend.domain.location.entity.Club;

import java.util.List;
import java.util.Optional;

public interface ClubRepository extends JpaRepository<Club, Long> {

    List<Club> findByGu(String gu);

    Optional<Club> findById(Long clubId);
}
