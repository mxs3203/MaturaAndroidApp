package com.matura.drzavna.matura.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mateosokac on 3/8/17.
 */

public class KnjizevnostPitanja extends RealmObject {

    @PrimaryKey
    int PitanjeID;

    String Pitanje;
    String OdgovorA;
    String OdgovorB;
    String OdgovorC;
    String OdgovorD;
    String Tocan;
    String godina;
    String rok;
    String razina;

    public int getPitanjeID() {
        return PitanjeID;
    }

    public void setPitanjeID(int pitanjeID) {
        PitanjeID = pitanjeID;
    }

    public String getPitanje() {
        return Pitanje;
    }

    public void setPitanje(String pitanje) {
        Pitanje = pitanje;
    }

    public String getOdgovorA() {
        return OdgovorA;
    }

    public void setOdgovorA(String odgovorA) {
        OdgovorA = odgovorA;
    }

    public String getOdgovorB() {
        return OdgovorB;
    }

    public void setOdgovorB(String odgovorB) {
        OdgovorB = odgovorB;
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
