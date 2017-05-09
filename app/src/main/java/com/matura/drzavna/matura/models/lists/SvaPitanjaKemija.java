package com.matura.drzavna.matura.models.lists;

import com.matura.drzavna.matura.models.FizikaPitanje;
import com.matura.drzavna.matura.models.KemijaPitanje;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mateosokac on 4/10/17.
 */

public class SvaPitanjaKemija extends RealmObject {
    @PrimaryKey
    int id;

    private RealmList<KemijaPitanje> sva_pitanja;

    public RealmList<KemijaPitanje> getStrings() {
        return sva_pitanja;
    }

    public void setStrings(RealmList<KemijaPitanje> sva_pitanja) {
        this.sva_pitanja = sva_pitanja;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
