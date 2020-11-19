package com.wolf_pack.wolf_management.RQ;

import javax.validation.constraints.NotNull;
import java.util.List;

public class RemoveWolvesFromPackRQ {
    //Pack identifier. Mandatory
    @NotNull(message = "Please, insert a valid pack ID.")
    private Long packId;

    //List of wolves to remove. Mandatory
    @NotNull(message = "You should add at least one wolf to the pack.")
    private List<Long> wolvesList;

    public RemoveWolvesFromPackRQ() {
    }

    public Long getPackId() {
        return packId;
    }

    public void setPackId(Long packId) {
        this.packId = packId;
    }

    public List<Long> getWolvesList() {
        return wolvesList;
    }

    public void setWolvesList(List<Long> wolvesList) {
        this.wolvesList = wolvesList;
    }
}
