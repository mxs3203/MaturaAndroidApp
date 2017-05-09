package com.matura.drzavna.matura.models.lists;

import com.matura.drzavna.matura.models.FizikaPitanje;
import com.matura.drzavna.matura.models.KnjizevnostTekst;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mateosokac on 3/30/17.
 */

public class SvaPitanjaFizika extends RealmObject {
    @PrimaryKey
    int id;

    private RealmList<FizikaPitanje> sva_pitanja;

    public RealmList<FizikaPitanje> getStrings() {
        return sva_pitanja;
    }

    public void setStrings(RealmList<FizikaPitanje> sva_pitanja) {
        this.sva_pitanja = sva_pitanja;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
