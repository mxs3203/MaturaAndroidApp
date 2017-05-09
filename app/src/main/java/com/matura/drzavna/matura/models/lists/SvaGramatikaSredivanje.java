package com.matura.drzavna.matura.models.lists;

import com.matura.drzavna.matura.models.GramatikaPitanje;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mateosokac on 3/8/17.
 */

public class SvaGramatikaSredivanje extends RealmObject {
    @PrimaryKey
    int id;

    private RealmList<GramatikaPitanje> sva_pitanja;

    public RealmList<GramatikaPitanje> getStrings() {
        return sva_pitanja;
    }

    public void setStrings(RealmList<GramatikaPitanje> sva_pitanja) {
        this.sva_pitanja = sva_pitanja;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
