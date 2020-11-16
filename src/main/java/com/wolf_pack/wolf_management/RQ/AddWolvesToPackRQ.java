package com.wolf_pack.wolf_management.RQ;

import java.util.List;

public class AddWolvesToPackRQ {
    public Long packId;
    List<Long> wolvesList;

    public AddWolvesToPackRQ() {
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
