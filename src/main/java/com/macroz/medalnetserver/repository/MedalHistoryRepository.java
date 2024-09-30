package com.macroz.medalnetserver.repository;

import com.macroz.medalnetserver.model.MedalHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedalHistoryRepository extends JpaRepository<MedalHistory, Long> {

	List<MedalHistory> findByMedalIdOrderByModifiedAtDesc(Long medalId);
}
