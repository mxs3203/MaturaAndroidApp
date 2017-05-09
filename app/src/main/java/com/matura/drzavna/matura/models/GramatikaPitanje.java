package com.matura.drzavna.matura.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mateosokac on 3/8/17.
 */

public class GramatikaPitanje extends RealmObject {

    @PrimaryKey
    int pitanje_id;

    String pitanje;
    String odgovorA;
    String odgovorB;
    String OdgovorC;
    String OdgovorD;
    String Tocan;
    String godina;
    String rok;
    String razina;

    public int getPitanje_id() {
        return pitanje_id;
    }

    public void setPitanje_id(int pitanje_id) {
        this.pitanje_id = pitanje_id;
    }

    public String getPitanje() {
        return pitanje;
    }

    public void setPitanje(String pitanje) {
        this.pitanje = pitanje;
    }

    public String getOdgovorA() {
        return odgovorA;
    }

    public void setOdgovorA(String odgovorA) {
        this.odgovorA = odgovorA;
    }

    public String getOdgovorB() {
        return odgovorB;
    }

    public void setOdgovorB(String odgovorB) {
        this.odgovorB = odgovorB;
    }

    public String getOdgovorC() {
        return OdgovorC;
    }

    public void setOdgovorC(String odgovorC) {
        OdgovorC = odgovorC;
    }

    public String getOdgovorD() {
        return OdgovorD;
    }

    public void setOdgovorD(String odgovorD) {
        OdgovorD = odgovorD;
    }

    public String getTocan() {
        return Tocan;
    }

    public void setTocan(String tocan) {
        Tocan = tocan;
    }

    public String getGodina() {
        return godina;
    }

    public void setGodina(String godina) {
        this.godina = godina;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getRazina() {
        return razina;
    }

    public void setRazina(String razina) {
        this.razina = razina;
    }
}
