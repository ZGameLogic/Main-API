package com.zgamelogic.controllers;

import com.zgamelogic.data.database.seaOfThieves.SOTDateAvailable;
import com.zgamelogic.data.database.seaOfThieves.SOTRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sot")
public class SOTController {
    private final SOTRepository sotRepository;

    @Autowired
    public SOTController(SOTRepository sotRepository) {
        this.sotRepository = sotRepository;
    }

    @PostMapping
    private SOTDateAvailable createSOTData(@RequestBody SOTDateAvailable data){
        sotRepository.save(data);
        return data;
    }
}
