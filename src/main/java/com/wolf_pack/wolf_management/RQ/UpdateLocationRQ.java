package com.wolf_pack.wolf_management.RQ;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UpdateLocationRQ {
    public Long wolfId;
    @Pattern(regexp = "^([-+]?)([\\d]{1,2})(((\\.)(\\d+)(,)))(\\s*)(([-+]?)([\\d]{1,3})((\\.)(\\d+))?)$", message = "Enter a correct GSP coordinate, please.")
    @NotEmpty(message = "Enter a correct GSP coordinate, please.")
    @NotNull
    private String location;

    public UpdateLocationRQ() {
    }

    public Long getWolfId() {
        return wolfId;
    }

    public void setWolfId(Long wolfId) {
        this.wolfId = wolfId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
