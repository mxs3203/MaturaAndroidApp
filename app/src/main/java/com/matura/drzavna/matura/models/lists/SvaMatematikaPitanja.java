package com.matura.drzavna.matura.models.lists;

import com.matura.drzavna.matura.models.KnjizevnostTekst;
import com.matura.drzavna.matura.models.MatematikaPitanje;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mateosokac on 3/17/17.
 */

public class SvaMatematikaPitanja extends RealmObject {
    @PrimaryKey
    int id;

    private RealmList<MatematikaPitanje> sva_pitanja;

    public RealmList<MatematikaPitanje> getStrings() {
        return sva_pitanja;
    }

    public void setStrings(RealmList<MatematikaPitanje> sva_pitanja) {
        this.sva_pitanja = sva_pitanja;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
