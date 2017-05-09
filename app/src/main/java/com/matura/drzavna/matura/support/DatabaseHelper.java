package com.matura.drzavna.matura.support;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.TextView;

import com.matura.drzavna.matura.R;
import com.matura.drzavna.matura.main_menu_activities.RjesavanjeIspitaInfoFragments;
import com.matura.drzavna.matura.main_menu_activities.RjesavanjeIspitaRazina;
import com.matura.drzavna.matura.models.BiologijaPitanje;
import com.matura.drzavna.matura.models.GramatikaPitanje;
import com.matura.drzavna.matura.models.GramatikaSredivanje;
import com.matura.drzavna.matura.models.KnjizevnostPitanja;
import com.matura.drzavna.matura.models.KnjizevnostPovezivanje;
import com.matura.drzavna.matura.models.KnjizevnostTekst;
import com.matura.drzavna.matura.models.MatematikaPitanje;
import com.matura.drzavna.matura.models.Pig_Pitanja;
import com.matura.drzavna.matura.models.PovijestPitanja;
import com.matura.drzavna.matura.models.SocioloijaPitanje;
import com.matura.drzavna.matura.models.lists.GramatikaSvaPitanja;
import com.matura.drzavna.matura.models.lists.SvaBiologijaPitanja;
import com.matura.drzavna.matura.models.lists.SvaGramatikaSredivanje;
import com.matura.drzavna.matura.models.lists.SvaKnjizevnostPovezivanje;
import com.matura.drzavna.matura.models.lists.SvaKnjizevnostText;
import com.matura.drzavna.matura.models.lists.SvaMatematikaPitanja;
import com.matura.drzavna.matura.models.lists.SvaPitanjaEngleski;
import com.matura.drzavna.matura.models.lists.SvaPitanjaFizika;
import com.matura.drzavna.matura.models.lists.SvaPitanjaKemija;
import com.matura.drzavna.matura.models.lists.SvaPitanjaKnjizevnost;
import com.matura.drzavna.matura.models.lists.SvaPitanjaPIG;
import com.matura.drzavna.matura.models.lists.SvaPitanjaPovijest;
import com.matura.drzavna.matura.models.lists.SvaSociologijaPitanja;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.IOException;

/**
 * Created by mateosokac on 3/8/17.
 */

public class DatabaseHelper {

    public static Realm resetRealm()
    {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();

        try {
            return Realm.getDefaultInstance();


        } catch (RealmMigrationNeededException e){
            try {
                Realm.deleteRealm(realmConfiguration);
                //Realm file has been deleted.
                return Realm.getInstance(realmConfiguration);
            } catch (Exception ex){
                throw ex;
                //No Realm file to remove.
            }
        }
    }


    //ZA BAZU I JSON
    public static Class getClassesForJsonMapping(String ispit)
    {
        if(ispit.equalsIgnoreCase("gramatika_pitanja"))
            return GramatikaSvaPitanja.class;
        else if(ispit.equalsIgnoreCase("pig_pitanja"))
            return SvaPitanjaPIG.class;
        else if(ispit.equalsIgnoreCase("povijest_pitanja"))
            return SvaPitanjaPovijest.class;
        else if(ispit.equalsIgnoreCase("knjizevnost_pitanja"))
            return SvaPitanjaKnjizevnost.class;
        else if(ispit.equalsIgnoreCase("knjizevnost_povezivanje"))
            return SvaKnjizevnostPovezivanje.class;
        else if(ispit.equalsIgnoreCase("knjizevnost_tekst"))
            return SvaKnjizevnostText.class;
        else if(ispit.equalsIgnoreCase("gramatika_sredivanje"))
            return SvaGramatikaSredivanje.class;
        else if(ispit.equalsIgnoreCase("biologija_pitanja"))
            return SvaBiologijaPitanja.class;
        else if(ispit.equalsIgnoreCase("sociologija_pitanja"))
            return SvaSociologijaPitanja.class;
        else if(ispit.equalsIgnoreCase("matematika_pitanja"))
            return SvaMatematikaPitanja.class;
        else if(ispit.equalsIgnoreCase("fizika_pitanja"))
            return SvaPitanjaFizika.class;
        else if(ispit.equalsIgnoreCase("kemija_pitanja"))
            return SvaPitanjaKemija.class;
        else if(ispit.equalsIgnoreCase("engleski_pitanja"))
            return SvaPitanjaEngleski.class;
        return  null;
    }
    public static Object getObjectsForJsonMapping(String ispit)
    {
        if(ispit.equalsIgnoreCase("gramatika_pitanja"))
            return new GramatikaSvaPitanja();
        else if(ispit.equalsIgnoreCase("pig_pitanja"))
            return new SvaPitanjaPIG();
        else if(ispit.equalsIgnoreCase("povijest_pitanja"))
            return new SvaPitanjaPovijest();
        else if(ispit.equalsIgnoreCase("knjizevnost_pitanja"))
            return new SvaPitanjaKnjizevnost();
        else if(ispit.equalsIgnoreCase("knjizevnost_povezivanje"))
            return new SvaKnjizevnostPovezivanje();
        else if(ispit.equalsIgnoreCase("knjizevnost_tekst"))
            return new SvaKnjizevnostText();
        else if(ispit.equalsIgnoreCase("gramatika_sredivanje"))
            return new SvaGramatikaSredivanje();
        else if(ispit.equalsIgnoreCase("biologija_pitanja"))
            return new SvaBiologijaPitanja();
        else if(ispit.equalsIgnoreCase("matematika_pitanja"))
            return new SvaMatematikaPitanja();
        else if(ispit.equalsIgnoreCase("sociologija_pitanja"))
            return new SvaSociologijaPitanja();
        else if(ispit.equalsIgnoreCase("fizika_pitanja"))
            return new SvaPitanjaFizika();
        else if(ispit.equalsIgnoreCase("kemija_pitanja"))
            return new SvaPitanjaKemija();
        else if(ispit.equalsIgnoreCase("engleski_pitanja"))
            return new SvaPitanjaEngleski();
        return  null;
    }



    public static String mapRokToPresentationvalue(String rok)
    {
        if(rok.equalsIgnoreCase("jesenski rok"))
            return "Jesenski Rok";
        if(rok.equalsIgnoreCase("ljetni rok"))
            return "Ljetni Rok";
        if(rok.equalsIgnoreCase("zimski rok"))
            return "Zimski Rok";

        return "";
    }

    public static String mapRokValueToTableValue(String rok)
    {
        if(rok.equalsIgnoreCase("Jesenski Rok"))
            return "jesenski rok";
        if(rok.equalsIgnoreCase("Ljetni Rok"))
            return "ljetni rok";
        if(rok.equalsIgnoreCase("Zimski Rok"))
            return "zimski rok";

        return "";
    }



    ///ZA POKAZIVANJE PREDMETA
    public static String mapTableNameToPresentationValue(String tablename)
    {
        if(tablename.equalsIgnoreCase("gramatika_pitanja"))
            return "Hrvatski Jezik";
        else if(tablename.equalsIgnoreCase("pig_pitanja"))
            return "Politika i Gospodarstvo";
        else if(tablename.equalsIgnoreCase("povijest_pitanja"))
            return "Povijest";
        else if(tablename.equalsIgnoreCase("knjizevnost_pitanja"))
            return "Hrvatski Jezik";
        else if(tablename.equalsIgnoreCase("knjizevnost_povezivanje"))
            return "Hrvatski Jezik";
        else if(tablename.equalsIgnoreCase("knjizevnost_tekst"))
            return "Hrvatski Jezik";
        else if(tablename.equalsIgnoreCase("gramatika_sredivanje"))
            return "Hrvatski Jezik";
        else if(tablename.equalsIgnoreCase("biologija_pitanja"))
            return "Biologija";
        else if(tablename.equalsIgnoreCase("sociologija_pitanja"))
            return "Sociologija";
        else if(tablename.equalsIgnoreCase("matematika_pitanja"))
            return "Matematika";
        else if(tablename.equalsIgnoreCase("fizika_pitanja"))
            return "Fizika";
        else if(tablename.equalsIgnoreCase("kemija_pitanja"))
            return "Kemija";
        else if(tablename.equalsIgnoreCase("engleski_pitanja"))
            return "Engleski";
        return  "";
    }
    public static String mapListViewValuesToTableName(String value)
    {
        if(value.equalsIgnoreCase("Hrvatski Jezik"))
            return "knjizevnost_pitanja,gramatika_pitanja,knjizevnost_povezivanje,knjizevnost_tekst,gramatika_sredivanje";
        else if(value.equalsIgnoreCase("Politika i Gospodarstvo"))
            return "pig_pitanja";
        else if(value.equalsIgnoreCase("Povijest"))
            return "povijest_pitanja";
        else if(value.equalsIgnoreCase("Biologija"))
            return "biologija_pitanja";
        else if(value.equalsIgnoreCase("Sociologija"))
            return "sociologija_pitanja";
        else if(value.equalsIgnoreCase("Matematika"))
            return "matematika_pitanja";
        else if(value.equalsIgnoreCase("Fizika"))
            return "fizika_pitanja";
        else if(value.equalsIgnoreCase("Kemija"))
            return "kemija_pitanja";
        else if(value.equalsIgnoreCase("Engleski"))
            return "engleski_pitanja";
        return  "";
    }

    public static ArrayList<TextView> getVrsteIspita(Context c, String tablename)
    {
        ArrayList<TextView> views = new ArrayList<TextView>();
        /*
        if(tablename.equalsIgnoreCase("gramatika_pitanja") ||
                tablename.equalsIgnoreCase("knjizevnost_povezivanje") ||
                tablename.equalsIgnoreCase("knjizevnost_pitanja") ||
                tablename.equalsIgnoreCase("knjizevnost_tekst") ||
                tablename.equalsIgnoreCase("gramatika_sredivanje") ||
                tablename.equalsIgnoreCase("engleski_pitanja") ){
            TextView v1 = new TextView(c);
            v1.setBackgroundResource(R.drawable.ispit_citanja);
            views.add(v1);
            TextView v2 = new TextView(c);
            v2.setBackgroundResource(R.drawable.ispit_pisanja);
            views.add(v2);
            TextView v3 = new TextView(c);
            v3.setBackgroundResource(R.drawable.ispit_slusanja);
            views.add(v3);
            TextView v4 = new TextView(c);
            v4.setBackgroundResource(R.drawable.svi_ispiti);
            views.add(v4);
            return views;
        }
        else if(tablename.equalsIgnoreCase("pig_pitanja") || tablename.equalsIgnoreCase("povijest_pitanja") ||
                tablename.equalsIgnoreCase("biologija_pitanja") || tablename.equalsIgnoreCase("sociologija_pitanja")
                || tablename.equalsIgnoreCase("matematika_pitanja") || tablename.equalsIgnoreCase("fizika_pitanja")
                || tablename.equalsIgnoreCase("kemija_pitanja")){
            TextView v1 = new TextView(c);
            v1.setBackgroundResource(R.drawable.svi_ispiti);
            views.add(v1);
            return views;
        }
        */

        TextView v1 = new TextView(c);
        v1.setBackgroundResource(R.drawable.svi_ispiti);
        views.add(v1);

        return views;
    }

    public static Class getClassForIntent(String tablename) {
        if(tablename.equalsIgnoreCase("gramatika_pitanja") ||
                tablename.equalsIgnoreCase("knjizevnost_povezivanje") ||
                tablename.equalsIgnoreCase("knjizevnost_pitanja") ||
                tablename.equalsIgnoreCase("knjizevnost_tekst") ||
                tablename.equalsIgnoreCase("gramatika_sredivanje") ||
                tablename.equalsIgnoreCase("matematika_pitanja") ||
                tablename.equalsIgnoreCase("engleski_pitanja") ){

            return RjesavanjeIspitaRazina.class;
        }
        else if(tablename.equalsIgnoreCase("pig_pitanja") || tablename.equalsIgnoreCase("povijest_pitanja") ||
                tablename.equalsIgnoreCase("biologija_pitanja") || tablename.equalsIgnoreCase("sociologija_pitanja")
                || tablename.equalsIgnoreCase("fizika_pitanja")  || tablename.equalsIgnoreCase("kemija_pitanja")){

            return RjesavanjeIspitaInfoFragments.class;
        }

        return null;
    }

    public static String mapRazinaToColumnName(String razina, String predmet)
    {
        if(predmet.equalsIgnoreCase("gramatika_pitanja") ||
                predmet.equalsIgnoreCase("knjizevnost_povezivanje") ||
                predmet.equalsIgnoreCase("knjizevnost_pitanja") ||
                predmet.equalsIgnoreCase("knjizevnost_tekst") ||
                predmet.equalsIgnoreCase("gramatika_sredivanje")) {
            if(razina.equalsIgnoreCase("Viša razina (A)"))
                return "viša razina";
            else if (razina.equalsIgnoreCase("Niža razina (B)"))
                return "niža razina";
        }
        else if(predmet.equalsIgnoreCase("matematika_pitanja") || predmet.equalsIgnoreCase("engleski_pitanja") )
            if(razina.equalsIgnoreCase("Viša razina (A)"))
                return "viša";
            else if (razina.equalsIgnoreCase("Niža razina (B)"))
                return "osnovna";

        return "";
    }


    //POSLATI SI MAIL SA BAZOM,DEBUG
    public static void exportFileTomail(String s, Context c)
    {
        File file = null;
        try {
            // get or create an "export.realm" file
            file = new File(c.getExternalCacheDir(), "pig.txt");
            try{
                PrintWriter writer = new PrintWriter(file, "UTF-8");
                writer.write(s);
                writer.close();
            } catch (IOException e) {
                // do something
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // init email intent and add export.realm as attachment
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, "info.over.under@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Realm DB");
        intent.putExtra(Intent.EXTRA_TEXT, "baza.");
        Uri u = Uri.fromFile(file);
        intent.putExtra(Intent.EXTRA_STREAM, u);

        // start email intent
        c.startActivity(Intent.createChooser(intent, "sta god"));

    }


}
