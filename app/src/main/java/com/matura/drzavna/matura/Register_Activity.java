package com.matura.drzavna.matura;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.matura.drzavna.matura.models.BiologijaPitanje;
import com.matura.drzavna.matura.models.EngleskiPitanje;
import com.matura.drzavna.matura.models.FizikaPitanje;
import com.matura.drzavna.matura.models.GramatikaPitanje;
import com.matura.drzavna.matura.models.GramatikaSredivanje;
import com.matura.drzavna.matura.models.Ispit;
import com.matura.drzavna.matura.models.KemijaPitanje;
import com.matura.drzavna.matura.models.KnjizevnostPitanja;
import com.matura.drzavna.matura.models.KnjizevnostPovezivanje;
import com.matura.drzavna.matura.models.KnjizevnostTekst;
import com.matura.drzavna.matura.models.MatematikaPitanje;
import com.matura.drzavna.matura.models.Pig_Pitanja;
import com.matura.drzavna.matura.models.PovijestPitanja;
import com.matura.drzavna.matura.models.SocioloijaPitanje;
import com.matura.drzavna.matura.support.DatabaseHelper;
import com.matura.drzavna.matura.support.Upload;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.concurrent.ExecutionException;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.internal.IOException;

public class Register_Activity extends AppCompatActivity {

    TextView imasRacun;
    Button register;
    Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_register);
        c = this;

        imasRacun = (TextView) findViewById(R.id.editTextImasRacun);
        imasRacun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imasRacun.setBackgroundColor(Color.parseColor("#dddddd"));
                Intent i = new Intent(c, Login.class);
                startActivity(i);
                imasRacun.setTextColor(Color.DKGRAY);
            }
        });
        register = (Button) findViewById(R.id.button_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(c, RegisterForm.class);
                startActivity(i);
            }
        });



    }

}
