package org.ureca.pinggubackend.domain.apply.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ureca.pinggubackend.domain.apply.entity.Apply;

public interface ApplyRepository extends JpaRepository<Apply, Long> {
}
