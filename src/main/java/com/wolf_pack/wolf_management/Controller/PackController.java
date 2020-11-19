package com.wolf_pack.wolf_management.Controller;

import com.wolf_pack.wolf_management.Entity.Pack;
import com.wolf_pack.wolf_management.Entity.Wolf;
import com.wolf_pack.wolf_management.Exception.ResourceNotFoundException;
import com.wolf_pack.wolf_management.RQ.AddWolvesToPackRQ;
import com.wolf_pack.wolf_management.RQ.CreatePackRQ;
import com.wolf_pack.wolf_management.RQ.RemoveWolvesFromPackRQ;
import com.wolf_pack.wolf_management.RQ.UpdatePackRQ;
import com.wolf_pack.wolf_management.Repository.PackRepository;
import com.wolf_pack.wolf_management.Repository.WolfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Validated
public class PackController {
    @Autowired
    public PackRepository packRepository;
    @Autowired
    public WolfRepository wolfRepository;

    //List all the packs saved
    @GetMapping("/getPacks")
    public List<Pack> getPacks() {
        return packRepository.findAll();
    }

    //List only one pack
    @GetMapping(value = "/pack/{id}")
    public ResponseEntity<Pack> getPack(@PathVariable("id") @Min(1) long id) {
        Pack pack = packRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Pack with ID: " + id + " Not Found!"));
        //Return one pack
        return ResponseEntity.ok().body(pack);
    }

    //Creates a new pack with wolves
    @PostMapping(value = "/createPack", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> createPack(@RequestBody @Valid CreatePackRQ createPackRQ) {
        //Create new pack
        Pack newPack = new Pack();
        List<Wolf> wolfList = new ArrayList<>();
        List<Long> wolvesToAddList = new ArrayList<>();
        List<Long> wolvesNotFoundDBList = new ArrayList<>();

        //Loop all the wolves sent by Post
        for (Long id : createPackRQ.getWolvesList()) {
            //Look for the wolf in DB
            Optional<Wolf> wolf = wolfRepository.findById(id);
            if (wolf.isPresent()) {
                //Adds new wolves
                wolvesToAddList.add(wolf.get().getId());
                //Add wolves to the pack
                wolfList.add(wolf.get());
            } else {
                wolvesNotFoundDBList.add(id);
            }
        }

        //Checks if there is any new wolf to add
        if (wolvesToAddList.size() > 0) {
            //Adds wolves to the new pack
            newPack.setWolves(wolfList);
        }

        // Updates name and wolves
        newPack.setName(createPackRQ.getPackName());

        //Saves the new pack in the DB
        packRepository.save(newPack);

        return ResponseEntity.ok().body(
                "New pack created! PackId: " + newPack.getId()
                        + ", PackName: " + newPack.getName()
                        + ", wolves added in the pack : " + wolvesToAddList.toString()
                        + ", wolves not found in DB: " + wolvesNotFoundDBList.toString());
    }

    //Updates a pack
    @PutMapping(value = "/updatePack", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> updatePack(@RequestBody @Valid UpdatePackRQ updatePackRQ) {
        //Get pack
        Pack pack = packRepository.findById(updatePackRQ.getPackId()).orElseThrow(() ->
                new ResourceNotFoundException("Pack with ID: " + updatePackRQ.getPackId() + " Not Found!"));

        List<Wolf> wolfList = new ArrayList<>();
        List<Long> wolvesToAddList = new ArrayList<>();
        List<Long> wolvesNotFoundDBList = new ArrayList<>();

        //Loop all the wolves sent by Post
        for (Long id : updatePackRQ.getWolvesList()) {
            //Look for the wolf in DB
            Optional<Wolf> wolf = wolfRepository.findById(id);
            if (wolf.isPresent()) {
                //Adds wolves to update
                wolvesToAddList.add(wolf.get().getId());
                //Add wolves to the pack
                wolfList.add(wolf.get());
            } else {
                wolvesNotFoundDBList.add(id);
            }
        }

        //Checks if the user sends a pack name
        if (updatePackRQ.getPackName() != null && !updatePackRQ.getPackName().isEmpty()) {
            pack.setName(updatePackRQ.getPackName());
        }

        //Saves updated pack
        packRepository.save(pack);

        //Returns pack id, pack name and wolves response
        return ResponseEntity.ok().body(
                "Pack updated! Pack with ID: " + pack.getId()
                        + ", pack name: " + pack.getName()
                        + ", wolves updated in the pack : " + wolvesToAddList.toString()
                        + ", wolves not found in DB: " + wolvesNotFoundDBList.toString());

    }

    //Add a list of wolves and saves it into the DB
    @PostMapping(value = "/addWolfToPack", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> addWolfToPack(@RequestBody @Valid AddWolvesToPackRQ addWolvesToPackRQ) {
        //Get the pack & check if it exists
        Pack pack = packRepository.findById(addWolvesToPackRQ.getPackId()).orElseThrow(() ->
                new ResourceNotFoundException("Pack with ID :" + addWolvesToPackRQ.getPackId() + " Not Found!"));

        List<Long> wolvesToAddList = new ArrayList<>();
        List<Long> wolvesAlreadyInPackList = new ArrayList<>();
        List<Long> wolvesNotFoundDBList = new ArrayList<>();

        //Loop all the wolves sent by Post
        for (Long id : addWolvesToPackRQ.getWolvesList()) {
            //Look for the wolf in DB
            Optional<Wolf> wolf = wolfRepository.findById(id);
            if (wolf.isPresent()) {
                //Checks if the wolf exists
                if (pack.getWolves().contains(wolf.get())) {
                    //Adds all existing wolfs
                    wolvesAlreadyInPackList.add(wolf.get().getId());
                } else {
                    //Adds new wolves
                    wolvesToAddList.add(wolf.get().getId());
                    //Add wolves to the pack
                    pack.getWolves().add(wolf.get());
                }
            } else {
                wolvesNotFoundDBList.add(id);
            }
        }

        //Checks if there is any new wolf to add
        if (wolvesToAddList.size() > 0) {
            //Saves the new wolf/wolves in the DB
            packRepository.save(pack);
        }

        //Returns added wolves, not found wolves & existing wolves
        return ResponseEntity.ok().body(
                "Added wolves:" + wolvesToAddList.toString()
                        + ", wolves already in the pack : " + wolvesAlreadyInPackList.toString()
                        + ", wolves not found in DB: " + wolvesNotFoundDBList.toString());
    }

    //Removes wolf/wolves from a Pack
    @PostMapping(value = "/removeWolfFromPack", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> removeWolfFromPack(@RequestBody @Valid RemoveWolvesFromPackRQ removeWolvesFromPackRQ) {
        //Get the pack & check if it exists
        Pack pack = packRepository.findById(removeWolvesFromPackRQ.getPackId()).orElseThrow(() ->
                new ResourceNotFoundException("Pack with ID: " + removeWolvesFromPackRQ.getPackId() + " Not Found!"));

        List<Long> removedWolfList = new ArrayList<>();
        List<Long> wolvesNotFoundInPackList = new ArrayList<>();
        List<Long> wolvesNotFoundDBList = new ArrayList<>();

        //Loops all the wolves sent by Post
        for (Long id : removeWolvesFromPackRQ.getWolvesList()) {
            //Looks for the wolf in DB
            Optional<Wolf> wolf = wolfRepository.findById(id);
            if (wolf.isPresent()) {
                //Checks if the wolf exists in the pack
                if (pack.getWolves().contains(wolf.get())) {
                    pack.getWolves().remove(wolf.get());
                    //Adds wolves removed to the pack
                    removedWolfList.add(wolf.get().getId());
                } else {
                    //Adds wolves not found in pack
                    wolvesNotFoundInPackList.add(wolf.get().getId());
                }
            } else {
                wolvesNotFoundDBList.add(id);
            }
        }

        //Checks if there is any new wolf to remove
        if (removedWolfList.size() > 0) {
            //Removes the wolves in the DB
            packRepository.save(pack);
        }

        //Returns removed wolves, not found wolves & existing wolves
        return ResponseEntity.ok().body(
                "Removed wolves:" + removedWolfList.toString()
                        + ", wolves not found in pack: " + wolvesNotFoundInPackList
                        + ", wolves not found in DB: " + wolvesNotFoundDBList.toString());
    }

    //Deletes a pack from DB
    @DeleteMapping(value = "/pack/{id}")
    public ResponseEntity<String> deletePack(@PathVariable("id") @Min(1) long id) {
        //Check if pack exists
        Pack pack = packRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User with ID :" + id + " Not Found!"));

        //Delete pack from DB
        packRepository.deleteById(pack.getId());

        //Return response
        return ResponseEntity.ok().body("Pack with ID  " + pack.getId() + " deleted with success!");
    }
}