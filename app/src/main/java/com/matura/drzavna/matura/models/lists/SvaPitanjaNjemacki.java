package com.matura.drzavna.matura.models.lists;

import com.matura.drzavna.matura.models.KnjizevnostPovezivanje;
import com.matura.drzavna.matura.models.NjemackiPitanje;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SvaPitanjaNjemacki extends RealmObject{

    @PrimaryKey
    int id;

    private RealmList<NjemackiPitanje> sva_pitanja;

    public RealmList<NjemackiPitanje> getStrings() {
        return sva_pitanja;
    }

    public void setStrings(RealmList<NjemackiPitanje> sva_pitanja) {
        this.sva_pitanja = sva_pitanja;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
