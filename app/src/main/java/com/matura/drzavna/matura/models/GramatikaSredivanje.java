package com.matura.drzavna.matura.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mateosokac on 3/8/17.
 */

public class GramatikaSredivanje extends RealmObject {

    @PrimaryKey
    int PitanjeID;

    String Tekst;
    String OdgovorA1;
    String OdgovorA2;
    String OdgovorB1;
    String OdgovorB2;
    String OdgovorC1;
    String OdgovorC2;
    String OdgovorD1;
    String OdgovorD2;
    String OdgovorE1;
    String OdgovorE2;
    String OdgovorF1;
    String OdgovorF2;
    String OdgovorG1;
    String OdgovorG2;
    String OdgovorH1;
    String OdgovorH2;
    String OdgovorI1;
    String OdgovorI2;
    String OdgovorJ1;
    String OdgovorJ2;

    String TocanA;
    String TocanB;
    String TocanC;
    String TocanD;
    String TocanE;
    String TocanF;
    String TocanG;
    String TocanH;
    String TocanI;
    String TocanJ;

    String godina;
    String rok;
    String razina;

    public int getPitanjeID() {
        return PitanjeID;
    }

    public void setPitanjeID(int pitanjeID) {
        PitanjeID = pitanjeID;
    }

    public String getTekst() {
        return Tekst;
    }

    public void setTekst(String tekst) {
        Tekst = tekst;
    }

    public String getOdgovorA1() {
        return OdgovorA1;
    }

    public void setOdgovorA1(String odgovorA1) {
        OdgovorA1 = odgovorA1;
    }

    public String getOdgovorA2() {
        return OdgovorA2;
    }

    public void setOdgovorA2(String odgovorA2) {
        OdgovorA2 = odgovorA2;
    }

    public String getOdgovorB1() {
        return OdgovorB1;
    }

    public void setOdgovorB1(String odgovorB1) {
        OdgovorB1 = odgovorB1;
    }

    public String getOdgovorB2() {
        return OdgovorB2;
    }

    public void setOdgovorB2(String odgovorB2) {
        OdgovorB2 = odgovorB2;
    }

    public String getOdgovorC1() {
        return OdgovorC1;
    }

    public void setOdgovorC1(String odgovorC1) {
        OdgovorC1 = odgovorC1;
    }

    public String getOdgovorC2() {
        return OdgovorC2;
    }

    public void setOdgovorC2(String odgovorC2) {
        OdgovorC2 = odgovorC2;
    }

    public String getOdgovorD1() {
        return OdgovorD1;
    }

    public void setOdgovorD1(String odgovorD1) {
        OdgovorD1 = odgovorD1;
    }

    public String getOdgovorD2() {
        return OdgovorD2;
    }

    public void setOdgovorD2(String odgovorD2) {
        OdgovorD2 = odgovorD2;
    }

    public String getOdgovorE1() {
        return OdgovorE1;
    }

    public void setOdgovorE1(String odgovorE1) {
        OdgovorE1 = odgovorE1;
    }

    public String getOdgovorE2() {
        return OdgovorE2;
    }

    public void setOdgovorE2(String odgovorE2) {
        OdgovorE2 = odgovorE2;
    }

    public String getOdgovorF1() {
        return OdgovorF1;
    }

    public void setOdgovorF1(String odgovorF1) {
        OdgovorF1 = odgovorF1;
    }

    public String getOdgovorF2() {
        return OdgovorF2;
    }

    public void setOdgovorF2(String odgovorF2) {
        OdgovorF2 = odgovorF2;
    }

    public String getOdgovorG1() {
        return OdgovorG1;
    }

    public void setOdgovorG1(String odgovorG1) {
        OdgovorG1 = odgovorG1;
    }

    public String getOdgovorG2() {
        return OdgovorG2;
    }

    public void setOdgovorG2(String odgovorG2) {
        OdgovorG2 = odgovorG2;
    }

    public String getOdgovorH1() {
        return OdgovorH1;
    }

    public void setOdgovorH1(String odgovorH1) {
        OdgovorH1 = odgovorH1;
    }

    public String getOdgovorH2() {
        return OdgovorH2;
    }

    public void setOdgovorH2(String odgovorH2) {
        OdgovorH2 = odgovorH2;
    }

    public String getOdgovorI1() {
        return OdgovorI1;
    }

    public void setOdgovorI1(String odgovorI1) {
        OdgovorI1 = odgovorI1;
    }

    public String getOdgovorI2() {
        return OdgovorI2;
    }

    public void setOdgovorI2(String odgovorI2) {
        OdgovorI2 = odgovorI2;
    }

    public String getOdgovorJ1() {
        return OdgovorJ1;
    }

    public void setOdgovorJ1(String odgovorJ1) {
        OdgovorJ1 = odgovorJ1;
    }

    public String getOdgovorJ2() {
        return OdgovorJ2;
    }

    public void setOdgovorJ2(String odgovorJ2) {
        OdgovorJ2 = odgovorJ2;
    }

    public String getTocanA() {
        return TocanA;
    }

    public void setTocanA(String tocanA) {
        TocanA = tocanA;
    }

    public String getTocanB() {
        return TocanB;
    }

    public void setTocanB(String tocanB) {
        TocanB = tocanB;
    }

    public String getTocanC() {
        return TocanC;
    }

    public void setTocanC(String tocanC) {
        TocanC = tocanC;
    }

    public String getTocanD() {
        return TocanD;
    }

    public void setTocanD(String tocanD) {
        TocanD = tocanD;
    }

    public String getTocanE() {
        return TocanE;
    }

    public void setTocanE(String tocanE) {
        TocanE = tocanE;
    }

    public String getTocanF() {
        return TocanF;
    }

    public void setTocanF(String tocanF) {
        TocanF = tocanF;
    }

    public String getTocanG() {
        return TocanG;
    }

    public void setTocanG(String tocanG) {
        TocanG = tocanG;
    }

    public String getTocanH() {
        return TocanH;
    }

    public void setTocanH(String tocanH) {
        TocanH = tocanH;
    }

    public String getTocanI() {
        return TocanI;
    }

    public void setTocanI(String tocanI) {
        TocanI = tocanI;
    }

    public String getTocanJ() {
        return TocanJ;
    }

    public void setTocanJ(String tocanJ) {
        TocanJ = tocanJ;
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
