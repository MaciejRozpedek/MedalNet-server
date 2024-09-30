package com.macroz.medalnetserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.macroz.medalnetserver.exception.MedalNotFoundException;
import com.macroz.medalnetserver.model.Medal;
import com.macroz.medalnetserver.model.MedalHistory;
import com.macroz.medalnetserver.repository.MedalHistoryRepository;
import com.macroz.medalnetserver.repository.MedalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MedalService {
	private final MedalRepository medalRepository;

	private final MedalHistoryRepository medalHistoryRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	public MedalService(MedalRepository medalRepository, MedalHistoryRepository medalHistoryRepository) {
		this.medalRepository = medalRepository;
		this.medalHistoryRepository = medalHistoryRepository;
	}

	public Medal addMedal(Medal medal) {
//		TODO: don't save, when medal number exists - maybe
		saveMedalHistory(medal, "CREATE");
		return medalRepository.save(medal);
	}

	public List<Medal> findAllMedals() {
		return medalRepository.findAll();
	}

	public Medal findMedalById(Long id) {
		return medalRepository.findMedalById(id)
				.orElseThrow(() -> new MedalNotFoundException("Medal by id " + id + " was not found"));
	}

	public Medal updateMedal(Medal medal) {
		if (!medalRepository.existsById(medal.getId())) {
			throw new MedalNotFoundException("Medal by number " + medal.getNumber() + " was not found");
		}
		saveMedalHistory(medal, "UPDATE");
		return medalRepository.save(medal);
	}

	public void deleteMedal(Long id) {
		if (!medalRepository.existsById(id)) {
			throw new MedalNotFoundException("Medal by id " + id + " was not found");
		}
		Medal medal = medalRepository.findMedalById(id)
				.orElseThrow();

		saveMedalHistory(medal, "DELETE");
		medalRepository.deleteById(id);
	}

	public List<Medal> searchMedalsByName(String query) {
		return medalRepository.findMedalsByNameContainingOrSurnameContaining(query, query);
	}

	public List<Medal> searchMedalsByNumber(String query) {
		return medalRepository.findMedalsByNumber(query);
	}

	public Medal searchMedalsByExactNumber(String number) {
		return medalRepository.findMedalByExactNumber(number);
	}

	public List<Medal> findMedalsByUserId(Long id) {
		return medalRepository.findMedalsByUserId(id);
	}

	private void saveMedalHistory(Medal medal, String changeType) {
		MedalHistory medalHistory = new MedalHistory();
		medalHistory.setMedalId(medal.getId());
		String serializedMedal;
		try {
			serializedMedal = objectMapper.writeValueAsString(medal);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		medalHistory.setMedalJson(serializedMedal);
		medalHistory.setModifiedByUserId(medal.getUserId());
		medalHistory.setModifiedAt(LocalDateTime.now());
		medalHistory.setChangeType(changeType);
		medalHistoryRepository.save(medalHistory);
	}
}
