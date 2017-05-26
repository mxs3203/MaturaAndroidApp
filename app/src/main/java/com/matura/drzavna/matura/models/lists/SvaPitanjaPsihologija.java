package com.matura.drzavna.matura.models.lists;

import com.matura.drzavna.matura.models.Pig_Pitanja;
import com.matura.drzavna.matura.models.PsihologijaPitanje;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SvaPitanjaPsihologija extends RealmObject{


    @PrimaryKey
    int id;

    private RealmList<PsihologijaPitanje> sva_pitanja;

    public RealmList<PsihologijaPitanje> getStrings() {
        return sva_pitanja;
    }

    public void setStrings(RealmList<PsihologijaPitanje> sva_pitanja) {
        this.sva_pitanja = sva_pitanja;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
