package com.zgamelogic.controllers;

import com.zgamelogic.data.database.seaOfThieves.SOTDateAvailable;
import com.zgamelogic.data.database.seaOfThieves.SOTRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sot")
public class SOTController {
    private final SOTRepository sotRepository;
    private final String apiKey;

    @Autowired
    public SOTController(SOTRepository sotRepository, @Value("${ghosty.api.key}") String apiKey) {
        this.sotRepository = sotRepository;
        this.apiKey = apiKey;
    }

    @PostMapping
    private ResponseEntity<SOTDateAvailable> createSOTData(
            @RequestBody SOTDateAvailable data,
            @RequestHeader(name = "api-key") String apiKey
    ){
        if(!apiKey.equals(this.apiKey)) return ResponseEntity.badRequest().build();
        sotRepository.save(data);
        return ResponseEntity.ok(data);
    }

    @GetMapping
    private ResponseEntity<List<SOTDateAvailable>> getSOTData() {
        return ResponseEntity.ok(sotRepository.findAll());
    }
}
