package com.matura.drzavna.matura.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mateosokac on 3/4/17.
 */

public class Ispit extends RealmObject
{
    @PrimaryKey
    int id;
    String ime_ispita;
    int db_version;

    public int getDb_version() {
        return db_version;
    }

    public void setDb_version(int db_version) {
        this.db_version = db_version;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIme() {
        return ime_ispita;
    }

    public void setIme(String ime) {
        this.ime_ispita = ime;
    }
}
