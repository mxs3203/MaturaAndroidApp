package com.matura.drzavna.matura.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mateosokac on 4/10/17.
 */

public class KemijaPitanje extends RealmObject {
    @PrimaryKey
    int pitanje_id;
    boolean slike_u_odgovoru;
    int koliko_pitanja;
    String pitanje1;
    String pitanje2;
    String pitanje3;
    String pitanje4;

    String odgovorA;
    String odgovorB;
    String odgovorC;
    String odgovorD;

    String tocan;
    String slika;
    String rok;
    String godina;
    String razina;


    public int getPitanje_id() {
        return pitanje_id;
    }

    public void setPitanje_id(int pitanje_id) {
        this.pitanje_id = pitanje_id;
    }

    public boolean isSlike_u_odgovoru() {
        return slike_u_odgovoru;
    }

    public void setSlike_u_odgovoru(boolean slike_u_odgovoru) {
        this.slike_u_odgovoru = slike_u_odgovoru;
    }

    public int getKoliko_pitanja() {
        return koliko_pitanja;
    }

    public void setKoliko_pitanja(int koliko_pitanja) {
        this.koliko_pitanja = koliko_pitanja;
    }

    public String getPitanje1() {
        return pitanje1;
    }

    public void setPitanje1(String pitanje1) {
        this.pitanje1 = pitanje1;
    }

    public String getPitanje2() {
        return pitanje2;
    }

    public void setPitanje2(String pitanje2) {
        this.pitanje2 = pitanje2;
    }

    public String getPitanje3() {
        return pitanje3;
    }

    public void setPitanje3(String pitanje3) {
        this.pitanje3 = pitanje3;
    }

    public String getPitanje4() {
        return pitanje4;
    }

    public void setPitanje4(String pitanje4) {
        this.pitanje4 = pitanje4;
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
        return odgovorC;
    }

    public void setOdgovorC(String odgovorC) {
        this.odgovorC = odgovorC;
    }

    public String getOdgovorD() {
        return odgovorD;
    }

    public void setOdgovorD(String odgovorD) {
        this.odgovorD = odgovorD;
    }

    public String getTocan() {
        return tocan;
    }

    public void setTocan(String tocan) {
        this.tocan = tocan;
    }

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getGodina() {
        return godina;
    }

    public void setGodina(String godina) {
        this.godina = godina;
    }

    public String getRazina() {
        return razina;
    }

    public void setRazina(String razina) {
        this.razina = razina;
    }
}
