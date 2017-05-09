package com.matura.drzavna.matura.models.lists;

import com.matura.drzavna.matura.models.EngleskiPitanje;
import com.matura.drzavna.matura.models.MatematikaPitanje;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mateosokac on 4/15/17.
 */

public class SvaPitanjaEngleski extends RealmObject {
    @PrimaryKey
    int id;

    private RealmList<EngleskiPitanje> sva_pitanja;

    public RealmList<EngleskiPitanje> getStrings() {
        return sva_pitanja;
    }

    public void setStrings(RealmList<EngleskiPitanje> sva_pitanja) {
        this.sva_pitanja = sva_pitanja;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
