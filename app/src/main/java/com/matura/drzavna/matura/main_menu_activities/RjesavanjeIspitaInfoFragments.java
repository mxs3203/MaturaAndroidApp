package com.matura.drzavna.matura.main_menu_activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.matura.drzavna.matura.DrzavnaMaturaMainMenu;
import com.matura.drzavna.matura.R;
import com.matura.drzavna.matura.ispit_controllers.IspitActivity;
import com.matura.drzavna.matura.support.DatabaseHelper;

import io.realm.Realm;

public class RjesavanjeIspitaInfoFragments extends Activity {

    TextView title, pocetna;
    Context c;
    Realm realm;
    ImageView pocni, napomenaSlika;
    static boolean napomenaFormule = false;

    String razina = null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        c = this;


        if(!RjesavanjeIspitaInfoFragments.napomenaFormule) {
            requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
            setContentView(R.layout.activity_rjesavanje_ispita_info);
            getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.my_title_bar);

            pocni = (ImageView) findViewById(R.id.imageViewPocni);
            pocni.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = getIntent();
                    final String table_name = i.getStringExtra("table_name");
                    final String godina = i.getStringExtra("godina");
                    final String rok = i.getStringExtra("rok");
                    if(i.hasExtra("razina"))
                         razina = i.getStringExtra("razina");

                    setupTitleBar(DatabaseHelper.mapTableNameToPresentationValue(table_name));
                    RjesavanjeIspitaInfoFragments.napomenaFormule = true;
                    finish();
                    Intent is = new Intent(c, RjesavanjeIspitaInfoFragments.class);
                    is.putExtra("table_name", table_name);
                    is.putExtra("godina", godina);
                    is.putExtra("rok", rok);
                    if(razina != null)
                        is.putExtra("razina", razina);

                    startActivity(i);

                }
            });

            napomenaSlika = (ImageView) findViewById(R.id.imageViewNapomena);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    pocni.setImageResource(R.drawable.pocni_sa_rjesavanjem);
                    napomenaSlika.setImageResource(R.drawable.napomena);
                }
            }, 50);
        }else
        {
            requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
            setContentView(R.layout.formule_activity);
            getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.my_title_bar);
            Intent i = getIntent();
            final String table_name = i.getStringExtra("table_name");
            final String godina = i.getStringExtra("godina");
            final String rok = i.getStringExtra("rok");
            if(i.hasExtra("razina"))
                razina = i.getStringExtra("razina");
            RjesavanjeIspitaInfoFragments.napomenaFormule = false;
            setupTitleBar(DatabaseHelper.mapTableNameToPresentationValue(table_name));
            pocni = (ImageView) findViewById(R.id.imageViewPocni);
            pocni.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                    Intent is = new Intent(c, IspitActivity.class);
                    is.putExtra("table_name", table_name);
                    is.putExtra("godina", godina);
                    is.putExtra("rok", rok);
                    if(razina != null)
                        is.putExtra("razina", razina);

                    startActivity(is);
                }
            });
            napomenaSlika = (ImageView) findViewById(R.id.imageViewNapomena);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    pocni.setImageResource(R.drawable.pocni_sa_rjesavanjem);
                    napomenaSlika.setImageResource(R.drawable.formule);
                }
            }, 50);
        }
    }

    private void setupTitleBar(String ispit)
    {
        pocetna = (TextView)findViewById(R.id.textViewBarBack);
        pocetna.setText("");
        pocetna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent i = new Intent(c, DrzavnaMaturaMainMenu.class);
                startActivity(i);
            }
        });
        title = (TextView)findViewById(R.id.textViewTitle);
        title.setText(ispit);
    }
}
