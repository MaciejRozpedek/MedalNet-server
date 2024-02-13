package com.macroz.medalnetserver;

import com.macroz.medalnetserver.model.Medal;
import com.macroz.medalnetserver.service.MedalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medal")
public class MedalController {
    private final MedalService medalService;

    public MedalController(MedalService medalService) {
        this.medalService = medalService;
    }

@PostMapping("/add")
    public ResponseEntity<Medal> addMedal(@RequestBody Medal medal) {
        Medal newMedal = medalService.addMedal(medal);
        return new ResponseEntity<>(newMedal, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Medal>> getAllMedals() {
        List<Medal> medals = medalService.findAllMedals();
        return  new ResponseEntity<>(medals, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Medal> getMedalById(@PathVariable("id") Long id) {
        Medal medal = medalService.findMedalById(id);
        return  new ResponseEntity<>(medal, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Medal> updateMedal(@RequestBody Medal medal) {
        Medal updateMedal = medalService.updateMedal(medal);
        return new ResponseEntity<>(updateMedal, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMedal(@PathVariable("id") Long id) {
        medalService.deleteMedal(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
