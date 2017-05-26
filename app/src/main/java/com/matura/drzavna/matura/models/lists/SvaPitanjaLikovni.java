package com.matura.drzavna.matura.models.lists;

import com.matura.drzavna.matura.models.LikovniPitanje;
import com.matura.drzavna.matura.models.Pig_Pitanja;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SvaPitanjaLikovni extends RealmObject {

    @PrimaryKey
    int id;

    private RealmList<LikovniPitanje> sva_pitanja;

    public RealmList<LikovniPitanje> getStrings() {
        return sva_pitanja;
    }

    public void setStrings(RealmList<LikovniPitanje> sva_pitanja) {
        this.sva_pitanja = sva_pitanja;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
