package com.matura.drzavna.matura.models.lists;

import com.matura.drzavna.matura.models.FizikaPitanje;
import com.matura.drzavna.matura.models.GeografijaPitanje;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SvaPitanjaGeografija extends RealmObject{
    @PrimaryKey
    int id;

    private RealmList<GeografijaPitanje> sva_pitanja;

    public RealmList<GeografijaPitanje> getStrings() {
        return sva_pitanja;
    }

    public void setStrings(RealmList<GeografijaPitanje> sva_pitanja) {
        this.sva_pitanja = sva_pitanja;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
