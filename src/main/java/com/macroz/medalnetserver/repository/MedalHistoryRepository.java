package com.macroz.medalnetserver.repository;

import com.macroz.medalnetserver.model.MedalHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedalHistoryRepository extends JpaRepository<MedalHistory, Long> {
}
