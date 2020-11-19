package com.wolf_pack.wolf_management.Controller;

import com.wolf_pack.wolf_management.Entity.Wolf;
import com.wolf_pack.wolf_management.Exception.ResourceNotFoundException;
import com.wolf_pack.wolf_management.RQ.CreateWolfRQ;
import com.wolf_pack.wolf_management.RQ.UpdateLocationRQ;
import com.wolf_pack.wolf_management.RQ.UpdateWolfRQ;
import com.wolf_pack.wolf_management.Repository.WolfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class WolfController {

    @Autowired
    private WolfRepository wolfRepository;

    //List all thw wolves in DB
    @GetMapping("/getWolves")
    List<Wolf> getWolves() {
        return wolfRepository.findAll();
    }

    //List only one pack
    @GetMapping(value = "/wolf/{id}")
    public ResponseEntity<Wolf> getWolf(@PathVariable("id") @Min(1) long id) {
        Wolf wolf = wolfRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Wolf with ID: " + id + " Not Found!"));
        //Return one wolf
        return ResponseEntity.ok().body(wolf);
    }

    //Creates a new wolf
    @PostMapping(value = "/createWolf", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> createWolf(@Valid @RequestBody CreateWolfRQ createWolfRQ) {
        //Creates new wolf
        Wolf newWolf = new Wolf();
        newWolf.setName(createWolfRQ.getWolfName());
        newWolf.setLocation(createWolfRQ.getWolfLocation());
        newWolf.setBirthDate(createWolfRQ.getWolfBirthDate());
        newWolf.setGender(createWolfRQ.getWolfGender());

        //Saves new wolf
        wolfRepository.save(newWolf);

        //Return new wolf id & name
        return ResponseEntity.ok().body(
                "New wolf created! WolfId: " + newWolf.getId()
                        + ", WolfName: " + newWolf.getName());
    }

    //Updates a wolf
    @PutMapping(value = "/updateWolf", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> updateWolf(@RequestBody @Valid UpdateWolfRQ updateWolfRQ) {
        //Gets wolf
        Wolf wolf = wolfRepository.findById(updateWolfRQ.getWolfId()).orElseThrow(() ->
                new ResourceNotFoundException("Wolf with ID: " + updateWolfRQ.getWolfId() + " Not Found!"));

        //Checks if the user sends a wolf name
        if (updateWolfRQ.getWolfName() != null && !updateWolfRQ.getWolfName().isEmpty()) {
            wolf.setName(updateWolfRQ.getWolfName());
        }
        //Checks if the user sends a wolf gender
        if (updateWolfRQ.getWolfGender() != null) {
            wolf.setGender(updateWolfRQ.getWolfGender());
        }
        //Checks if the user sends a wolf birth day
        if (updateWolfRQ.getWolfBirthDate() != null) {
            wolf.setBirthDate(updateWolfRQ.getWolfBirthDate());
        }
        //Checks if the user sends a wolf location
        if (updateWolfRQ.getWolfLocation() != null) {
            wolf.setLocation(updateWolfRQ.getWolfLocation());
        }
        //Saves updated wolf
        wolfRepository.save(wolf);

        //Returns wolf id & wolf name
        return ResponseEntity.ok().body(
                "Wolf updated! Wolf with ID: " + wolf.getId()
                        + ", wolf name: " + wolf.getName());

    }

    //Updates a wolf location
    @PutMapping(value = "/updateLocation", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> updateLocation(@RequestBody @Valid UpdateLocationRQ updateLocationRQ) {
        //Gets the wolf & checks if exists
        Wolf wolf = wolfRepository.findById(updateLocationRQ.getWolfId()).orElseThrow(() ->
                new ResourceNotFoundException("Wolf with ID :" + updateLocationRQ.getWolfId() + " Not Found!"));

        //Set wolf new location
        wolf.setLocation(updateLocationRQ.getWolfLocation());

        //Saves wolf
        wolfRepository.save(wolf);

        //Returns wolf updated location & id
        return ResponseEntity.ok().body("Location Updated! WolfId: " + wolf.getId()
                + ", new location:" + wolf.getLocation());
    }

    //Deletes wolf from DB
    @DeleteMapping(value = "/wolf/{id}")
    public ResponseEntity<String> deleteWolf(@PathVariable("id") @Min(1) long wolfId) {
        //Gets the wolf & checks if exists
        Wolf wolf = wolfRepository.findById(wolfId).orElseThrow(() ->
                new ResourceNotFoundException("Wolf with ID :" + wolfId + " Not Found!"));

        //Deletes wolf from DB
        wolfRepository.delete(wolf);

        //Returns deleted wolf id
        return ResponseEntity.ok().body("Wolf with Id: " + wolfId + " deleted with success!");
    }
}