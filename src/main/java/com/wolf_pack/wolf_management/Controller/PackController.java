package com.wolf_pack.wolf_management.Controller;

import com.wolf_pack.wolf_management.Entity.Pack;
import com.wolf_pack.wolf_management.Entity.Wolf;
import com.wolf_pack.wolf_management.Exception.ResourceNotFoundException;
import com.wolf_pack.wolf_management.RQ.AddWolvesToPackRQ;
import com.wolf_pack.wolf_management.Repository.PackRepository;
import com.wolf_pack.wolf_management.Repository.WolfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController
public class PackController {

    @Autowired
    PackRepository packRepository;
    @Autowired
    WolfRepository wolfRepository;

    //List all the packs saved
    @GetMapping("/listPack")
    List<Pack> listPack() {
        return packRepository.findAll();
    }

    //Creates a new pack without wolves
    @PostMapping(
            value = "/createPack", consumes = "application/json", produces = "application/json")
    public Pack pack(@Valid @RequestBody Pack pack) {
        return packRepository.save(pack);
    }

    //Add a list of wolves and saves it into the DB
    @PostMapping(
            value = "/addWolfToPack", consumes = "application/json", produces = "application/json")
    public String addWolfToPack(@RequestBody @Valid AddWolvesToPackRQ addWolvesToPackRQ) {
        //Get the pack & check if it exists
        Pack pack = packRepository.findById(addWolvesToPackRQ.getPackId()).orElseThrow(() -> new ResourceNotFoundException("Pack with ID :" + addWolvesToPackRQ.packId + " Not Found!"));

        List<Long> wolfList = new ArrayList<>();
        List<Long> wolfExistList = new ArrayList<>();

        //Loop all the wolves sent by Post
        for (Long id : addWolvesToPackRQ.getWolvesList()) {
            //Look for the wolf in DB
            Wolf wolf = wolfRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException("Wolf with ID :" + id + " Not Found!"));

            //Checks if the wolf exists
            if (pack.getWolves().contains(wolf)) {
                //Adds all existing wolfs
                wolfExistList.add(wolf.getId());
            } else {
                //Adds new wolves
                wolfList.add(wolf.getId());
            }
            pack.getWolves().add(wolf);
        }
        //Saves the new wolf/wolves in the DB
        packRepository.save(pack);

        return "Added wolves:" + wolfList.toString() + ", existing wolves: " + wolfExistList.toString();
    }

    //Removes wolf/wolves from a Pack
    @PostMapping(
            value = "/removeWolfFromPack", consumes = "application/json", produces = "application/json")
    public String removeWolfFromPack(@RequestBody @Valid AddWolvesToPackRQ addWolvesToPackRQ) {
        //Get the pack & check if it exists
        Pack pack = packRepository.findById(addWolvesToPackRQ.getPackId()).orElseThrow(() ->
                new ResourceNotFoundException("Pack with ID :" + addWolvesToPackRQ.packId + " Not Found!"));

        List<Long> wolfList = new ArrayList<>();
        List<Long> wolfExistList = new ArrayList<>();

        //Look for the wolf in DB
        for (Long id : addWolvesToPackRQ.getWolvesList()) {
            //Gets the wolf & check if it exists
            Wolf wolf = wolfRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException("Wolf with ID :" + id + " Not Found!"));

            //Checks if the wolf is in the Pack
            if (pack.getWolves().contains(wolf)) {
                //Adds existing wolves
                wolfExistList.add(wolf.getId());
            } else {
                //Adds removed wolves
                wolfList.add(wolf.getId());
            }
            //Remove wolves from the Pack
            pack.getWolves().remove(wolf);
        }
        //Saves the updated Pack
        packRepository.save(pack);

        return "Removed wolves:" + wolfExistList.toString() + ", wolves not found: " + wolfList.toString();
    }
}