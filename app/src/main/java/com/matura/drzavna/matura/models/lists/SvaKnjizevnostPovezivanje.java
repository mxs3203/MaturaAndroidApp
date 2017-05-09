package com.matura.drzavna.matura.models.lists;

import com.matura.drzavna.matura.models.KnjizevnostPovezivanje;
import com.matura.drzavna.matura.models.Pig_Pitanja;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mateosokac on 3/8/17.
 */

public class SvaKnjizevnostPovezivanje extends RealmObject {
    @PrimaryKey
    int id;

    private RealmList<KnjizevnostPovezivanje> sva_pitanja;

    public RealmList<KnjizevnostPovezivanje> getStrings() {
        return sva_pitanja;
    }

    public void setStrings(RealmList<KnjizevnostPovezivanje> sva_pitanja) {
        this.sva_pitanja = sva_pitanja;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
