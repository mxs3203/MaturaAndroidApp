package com.matura.drzavna.matura;



import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.matura.drzavna.matura.main_menu_activities.Natjecanja;
import com.matura.drzavna.matura.main_menu_activities.OApplikaciji;
import com.matura.drzavna.matura.main_menu_activities.RjesavanjeIspita;
import com.matura.drzavna.matura.main_menu_activities.UvjetiKoristenja;
import com.matura.drzavna.matura.models.BiologijaPitanje;
import com.matura.drzavna.matura.models.EngleskiPitanje;
import com.matura.drzavna.matura.models.FizikaPitanje;
import com.matura.drzavna.matura.models.GramatikaPitanje;
import com.matura.drzavna.matura.models.GramatikaSredivanje;
import com.matura.drzavna.matura.models.KemijaPitanje;
import com.matura.drzavna.matura.models.KnjizevnostPitanja;
import com.matura.drzavna.matura.models.KnjizevnostPovezivanje;
import com.matura.drzavna.matura.models.KnjizevnostTekst;
import com.matura.drzavna.matura.models.MatematikaPitanje;
import com.matura.drzavna.matura.models.Pig_Pitanja;
import com.matura.drzavna.matura.models.PovijestPitanja;
import com.matura.drzavna.matura.models.SocioloijaPitanje;
import com.matura.drzavna.matura.models.Statistika;
import com.matura.drzavna.matura.support.DatabaseHelper;

import io.github.kexanie.library.MathView;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class DrzavnaMaturaMainMenu extends Activity implements View.OnClickListener
{

    Button rjesavanje, o_app,natjecanja,uvjeti;
    MathView formula_two;
    Realm realm;

    String tex =
            "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_drzavna_matura_menu);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.my_title_bar);

        rjesavanje = (Button)findViewById(R.id.buttonRjesavanje);
        rjesavanje.setOnClickListener(this);
        o_app = (Button)findViewById(R.id.buttonOApp);
        o_app.setOnClickListener(this);
        uvjeti = (Button)findViewById(R.id.buttonUvjeti);
        uvjeti.setOnClickListener(this);




        Realm.init(this);
        Realm realm = DatabaseHelper.resetRealm();


        RealmResults<Statistika> s = realm.where(Statistika.class).findAll();
        System.out.println("Statistika pitanje "+s.size());

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(Statistika.class);
            }
        });



        RealmResults<PovijestPitanja> ispiti_u_bazi = realm.where(PovijestPitanja.class).findAll();
        System.out.println("Povijest broj pitanja: "+ispiti_u_bazi.size());

        RealmResults<Pig_Pitanja> ispiti_u_bazi2 = realm.where(Pig_Pitanja.class).findAll();
        System.out.println("PIG broj pitanja: "+ispiti_u_bazi2.size());

        RealmResults<GramatikaPitanje> ispiti_u_bazi3 = realm.where(GramatikaPitanje.class).findAll();
        System.out.println("Gramatika broj pitanja: "+ispiti_u_bazi3.size());

        RealmResults<KnjizevnostPitanja> ispiti_u_bazi4 = realm.where(KnjizevnostPitanja.class).findAll();
        System.out.println("Knjizevnost pitanja broj pitanja: "+ispiti_u_bazi4.size());

        RealmResults<KnjizevnostTekst> ispiti_u_bazi5 = realm.where(KnjizevnostTekst.class).findAll();
        System.out.println("knjizevnost tekst broj pitanja: "+ispiti_u_bazi5.size());

        RealmResults<KnjizevnostPovezivanje> ispiti_u_bazi6 = realm.where(KnjizevnostPovezivanje.class).findAll();
        System.out.println("knjizevnost povezivanje broj pitanja: "+ispiti_u_bazi6.size());

        RealmResults<GramatikaSredivanje> ispiti_u_bazi7 = realm.where(GramatikaSredivanje.class).findAll();
        System.out.println("Gramatika sredivanje broj pitanja: "+ispiti_u_bazi7.size());

        RealmResults<BiologijaPitanje> ispiti_u_bazi8 = realm.where(BiologijaPitanje.class).findAll();
        System.out.println("Biologija broj pitanja: "+ispiti_u_bazi8.size());

        RealmResults<MatematikaPitanje> ispiti_u_bazi9 = realm.where(MatematikaPitanje.class).findAll();
        System.out.println("Mat broj pitanja: "+ispiti_u_bazi9.size());

        RealmResults<SocioloijaPitanje> ispiti_u_bazi10 = realm.where(SocioloijaPitanje.class).findAll();
        System.out.println("Sociologija broj pitanja: "+ispiti_u_bazi10.size());


        RealmResults<FizikaPitanje> ispiti_u_bazi11 = realm.where(FizikaPitanje.class).findAll();
        System.out.println("Fizika broj pitanja: "+ispiti_u_bazi11.size());

        RealmResults<KemijaPitanje> ispiti_u_bazi12 = realm.where(KemijaPitanje.class).findAll();
        System.out.println("Kemija broj pitanja: "+ispiti_u_bazi12.size());

        RealmResults<EngleskiPitanje> ispiti_u_bazi13 = realm.where(EngleskiPitanje.class).findAll();
        System.out.println("Engleski broj pitanja: "+ispiti_u_bazi13.size());


    }

    @Override
    public void onClick(View view) {
        Intent i = null;
        if(view.getId() == R.id.buttonRjesavanje)
            i = new Intent(this, RjesavanjeIspita.class);
        else if(view.getId() == R.id.buttonOApp)
            i = new Intent(this, OApplikaciji.class);
        else if(view.getId() == R.id.buttonUvjeti)
            i = new Intent(this, UvjetiKoristenja.class);


        startActivity(i);
    }
}
