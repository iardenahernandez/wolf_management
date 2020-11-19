package com.wolf_pack.wolf_management.RQ;

import com.wolf_pack.wolf_management.Enum.Gender;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

public class CreateWolfRQ {
    //Wolf name. Only letters accepted
    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[A-Za-z]*$", message = "Please, insert a valid name.")
    private String wolfName;

    //Enum: FEMALE, MALE, UNKNOWN. Default value: UNKNOWN
    @Column(columnDefinition = "varchar(32) default 'UNKNOWN'")
    @Enumerated(value = EnumType.STRING)
    private Gender wolfGender;

    //Date format accepted "yyyy-MM-dd"
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Please, provide a valid data. Format: yyyy-MM-dd.")
    private Date wolfBirthDate;

    //Location: accepts DD (decimal degrees) coordinates. Example: 51.4381, 5.4752
    @Pattern(regexp = "^([-+]?)([\\d]{1,2})(((\\.)(\\d+)(,)))(\\s*)(([-+]?)([\\d]{1,3})((\\.)(\\d+))?)$",
            message = "Enter a correct GSP coordinate, please.")
    @NotEmpty(message = "Enter a correct GSP coordinate, please.")
    @NotNull
    private String wolfLocation;

    public CreateWolfRQ() {
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
