package com.wolf_pack.wolf_management.RQ;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UpdateLocationRQ {
    //Wolf name. Mandatory
    @NotNull(message = "Please, insert a valid wolf ID.")
    private Long wolfId;

    //Location: accepts DD (decimal degrees) coordinates. Example: 51.4381, 5.4752. Mandatory
    @Pattern(regexp = "^([-+]?)([\\d]{1,2})(((\\.)(\\d+)(,)))(\\s*)(([-+]?)([\\d]{1,3})((\\.)(\\d+))?)$", message = "Enter a correct GSP coordinate, please.")
    @NotEmpty(message = "Enter a correct GSP coordinate, please.")
    @NotNull(message = "Enter a correct GSP coordinate, please.")
    private String wolfLocation;

    public UpdateLocationRQ() {
    }

    public Long getWolfId() {
        return wolfId;
    }

    public void setWolfId(Long wolfId) {
        this.wolfId = wolfId;
    }

    public String getWolfLocation() {
        return wolfLocation;
    }

    public void setWolfLocation(String wolfLocation) {
        this.wolfLocation = wolfLocation;
    }
}
