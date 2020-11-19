package com.wolf_pack.wolf_management.RQ;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wolf_pack.wolf_management.Enum.Gender;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

public class UpdateWolfRQ {
    //Wolf identifier. Mandatory
    @NotNull(message = "Please, insert a valid pack ID.")
    private long wolfId;

    //Wolf name. Optional
    private String wolfName;

    //Wolf gender. Optional
    @Enumerated(value = EnumType.STRING)
    private Gender wolfGender;

    //Wolf birthdate. Optional
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date wolfBirthDate;

    //Wolf location. Optional
    @Pattern(regexp = "^([-+]?)([\\d]{1,2})(((\\.)(\\d+)(,)))(\\s*)(([-+]?)([\\d]{1,3})((\\.)(\\d+))?)$", message = "Enter a correct GSP coordinate, please.")
    private String wolfLocation;

    public UpdateWolfRQ() {
    }

    public long getWolfId() {
        return wolfId;
    }

    public void setWolfId(long wolfId) {
        this.wolfId = wolfId;
    }

    public String getWolfName() {
        return wolfName;
    }

    public void setWolfName(String wolfName) {
        this.wolfName = wolfName;
    }

    public Gender getWolfGender() {
        return wolfGender;
    }

    public void setWolfGender(Gender wolfGender) {
        this.wolfGender = wolfGender;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getWolfBirthDate() {
        return wolfBirthDate;
    }

    public void setWolfBirthDate(Date wolfBirthDate) {
        this.wolfBirthDate = wolfBirthDate;
    }

    public String getWolfLocation() {
        return wolfLocation;
    }

    public void setWolfLocation(String wolfLocation) {
        this.wolfLocation = wolfLocation;
    }
}
