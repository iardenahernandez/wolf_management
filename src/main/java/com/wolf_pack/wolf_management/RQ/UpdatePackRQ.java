package com.wolf_pack.wolf_management.RQ;

import javax.validation.constraints.NotNull;
import java.util.List;

public class UpdatePackRQ {
    //Pack identifier. Mandatory
    @NotNull(message = "Please, insert a valid pack ID.")
    private long packId;

    //Pack name. Optional
    private String packName;

    //List of wolves to save. Optional
    private List<Long> wolvesList;

    public UpdatePackRQ() {
    }

    public long getPackId() {
        return packId;
    }

    public void setPackId(long packId) {
        this.packId = packId;
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
