package com.matura.drzavna.matura.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mateosokac on 3/4/17.
 */

public class DostupniIspiti extends RealmObject
{
    @PrimaryKey
    int id;

    private RealmList<Ispit> svi_ispiti;

    public RealmList<Ispit> getSvi_ispiti() {
        return svi_ispiti;
    }

    public void setStrings(RealmList<Ispit> svi_ispiti) {
        this.svi_ispiti = svi_ispiti;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
