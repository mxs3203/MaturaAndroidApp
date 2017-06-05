package com.matura.drzavna.matura.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class GeografijaPitanje extends RealmObject {

    @PrimaryKey
    int pitanje_id;

    int vise_pitanja;
    String pitanje;
    String pitanje2;
    String pitanje3;
    String pitanje4;

    String odgovorA;
    String odgovorB;
    String odgovorC;
    String odgovorD;
    String odgovorE;

    String tocan;
    String tocan2;
    String slika;
    String slika2;
    String godina;
    String rok;
    String razina;

    public int getPitanje_id() {
        return pitanje_id;
    }

    public void setPitanje_id(int pitanje_id) {
        this.pitanje_id = pitanje_id;
    }

    public int getVise_pitanja() {
        return vise_pitanja;
    }

    public void setVise_pitanja(int vise_pitanja) {
        this.vise_pitanja = vise_pitanja;
    }

    public String getPitanje() {
        return pitanje;
    }

    public void setPitanje(String pitanje) {
        this.pitanje = pitanje;
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

    public String getOdgovorE() {
        return odgovorE;
    }

    public void setOdgovorE(String odgovorE) {
        this.odgovorE = odgovorE;
    }

    public String getTocan() {
        return tocan;
    }

    public void setTocan(String tocan) {
        this.tocan = tocan;
    }

    public String getTocan2() {
        return tocan2;
    }

    public void setTocan2(String tocan2) {
        this.tocan2 = tocan2;
    }

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    public String getSlika2() {
        return slika2;
    }

    public void setSlika2(String slika2) {
        this.slika2 = slika2;
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
