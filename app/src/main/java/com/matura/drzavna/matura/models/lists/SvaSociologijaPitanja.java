package com.matura.drzavna.matura.models.lists;

import com.matura.drzavna.matura.models.KnjizevnostPitanja;
import com.matura.drzavna.matura.models.SocioloijaPitanje;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mateosokac on 3/15/17.
 */

public class SvaSociologijaPitanja extends RealmObject {
    @PrimaryKey
    int id;

    private RealmList<SocioloijaPitanje> sva_pitanja;

    public RealmList<SocioloijaPitanje> getStrings() {
        return sva_pitanja;
    }

    public void setStrings(RealmList<SocioloijaPitanje> sva_pitanja) {
        this.sva_pitanja = sva_pitanja;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
