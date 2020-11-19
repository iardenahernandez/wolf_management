package com.wolf_pack.wolf_management.RQ;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CreatePackRQ {
    //Pack name. Mandatory
    @NotNull(message = "Please, insert a pack name.")
    private String packName;

    //List of wolves to insert. Mandatory
    @NotNull(message = "You should add at least one wolf to the pack.")
    private List<Long> wolvesList;

    public CreatePackRQ() {
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public List<Long> getWolvesList() {
        return wolvesList;
    }

    public void setWolvesList(List<Long> wolvesList) {
        this.wolvesList = wolvesList;
    }
}
