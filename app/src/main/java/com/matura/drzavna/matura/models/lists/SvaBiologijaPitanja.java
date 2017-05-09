package com.matura.drzavna.matura.models.lists;

import com.matura.drzavna.matura.models.BiologijaPitanje;
import com.matura.drzavna.matura.models.GramatikaPitanje;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mateosokac on 3/12/17.
 */

public class SvaBiologijaPitanja extends RealmObject
{
    @PrimaryKey
    int id;

    private RealmList<BiologijaPitanje> sva_pitanja;

    public RealmList<BiologijaPitanje> getStrings() {
        return sva_pitanja;
    }

    public void setStrings(RealmList<BiologijaPitanje> sva_pitanja) {
        this.sva_pitanja = sva_pitanja;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
