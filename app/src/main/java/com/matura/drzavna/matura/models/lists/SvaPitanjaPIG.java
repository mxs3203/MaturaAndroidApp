package com.matura.drzavna.matura.models.lists;


import com.matura.drzavna.matura.models.Pig_Pitanja;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by mateosokac on 2/27/17.
 */

public class SvaPitanjaPIG extends RealmObject {

    @PrimaryKey
    int id;

    private RealmList<Pig_Pitanja> sva_pitanja;

    public RealmList<Pig_Pitanja> getStrings() {
        return sva_pitanja;
    }

    public void setStrings(RealmList<Pig_Pitanja> sva_pitanja) {
        this.sva_pitanja = sva_pitanja;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
