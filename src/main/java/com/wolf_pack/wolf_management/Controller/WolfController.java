package com.wolf_pack.wolf_management.Controller;

import com.wolf_pack.wolf_management.RQ.UpdateLocationRQ;
import com.wolf_pack.wolf_management.Entity.Wolf;
import com.wolf_pack.wolf_management.Exception.ResourceNotFoundException;
import com.wolf_pack.wolf_management.Repository.WolfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
public class WolfController {

    @Autowired
    WolfRepository wolfRepository;

    //List all thw wolves in DB
    @GetMapping("/listWolves")
    List<Wolf> listWolves() {
        return wolfRepository.findAll();
    }

    //Creates a new wolf
    @PostMapping(
            value = "/createWolf", consumes = "application/json", produces = "application/json")
    public Wolf createWolf(@Valid @RequestBody Wolf wolf) {
        return wolfRepository.save(wolf);
    }

    //Updates a wolf location
    //Expected: wolfId & location
    @PostMapping(
            value = "/updateLocation", consumes = "application/json", produces = "application/json")
    public Wolf updateWolfLocation(@RequestBody @Valid UpdateLocationRQ updateLocationRQ) {
       //Get the wolf & checks if exists
        Wolf wolf = wolfRepository.findById(updateLocationRQ.getWolfId())
                .orElseThrow(() -> new ResourceNotFoundException("Wolf with ID :" + updateLocationRQ.getWolfId() + " Not Found!"));
       //Set wolf new location
        wolf.setLocation(updateLocationRQ.getLocation());
        //Saves wolf
        return wolfRepository.save(wolf);
    }
}