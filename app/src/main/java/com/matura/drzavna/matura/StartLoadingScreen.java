package com.matura.drzavna.matura;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.crashlytics.android.Crashlytics;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.matura.drzavna.matura.models.DostupniIspiti;
import com.matura.drzavna.matura.models.Ispit;
import com.matura.drzavna.matura.models.User;
import com.matura.drzavna.matura.support.DatabaseHelper;
import com.matura.drzavna.matura.support.Download;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmResults;

public class StartLoadingScreen extends Activity {


    ImageView logo;

    Context c;
    Realm realm;
    RealmList<Ispit> ispitiZaDownload = new RealmList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Fabric.with(this, new Crashlytics());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setContentView(R.layout.start_loading_screen);

        c = this;


        Realm.init(this);
        realm = DatabaseHelper.resetRealm();

        logo = (ImageView) findViewById(R.id.imageView3);
        logo.setImageResource(R.drawable.first_logo);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Gson gson = new GsonBuilder().setLenient().create();
                    String json = "";
                    Download ispiti = new Download(c);
                    System.out.println("My token::" + getMyToken(realm));
                    if (getMyToken(realm) == null)
                        json = ispiti.execute("ispiti", "9b7c27bba058241ccd8e6bb9ec3bc817").get(10000, TimeUnit.MILLISECONDS);
                    else
                        json = ispiti.execute("ispiti", getMyToken(realm) + "").get(10000, TimeUnit.MILLISECONDS);

                    System.out.println("Sa servera svi ispiti:" + json);
                    if (!json.equals("failed") ) {
                        final DostupniIspiti response = gson.fromJson("{\"svi_ispiti\":" + json + "}", DostupniIspiti.class);
                        RealmList<Ispit> ispiti_sa_servera = response.getSvi_ispiti();
                        RealmResults<Ispit> ispiti_u_bazi = realm.where(Ispit.class).findAll();


                        ispiti_sa_servera.add(new Ispit(99,"psihologija_pitanja", 1));
//                        ispiti_sa_servera.add(new Ispit(98,"geografija_pitanja", 1));
                        ispiti_sa_servera.add(new Ispit(97,"likovni_pitanja", 1));
//                        ispiti_sa_servera.add(new Ispit(96,"informatika_pitanja", 1));
//                        ispiti_sa_servera.add(new Ispit(95,"njemacki_pitanja", 1));

                        System.out.println("SIZE u bazi: " + ispiti_u_bazi.size());
                        int i = 0;
                        for (Ispit ispit : ispiti_sa_servera) {
                            if (ispiti_u_bazi.size() > 0) {
                                if (ispiti_u_bazi.size() == ispiti_sa_servera.size()) {
                                    System.out.println(ispit.getIme() + "--- Version na lokalni: " + ispiti_u_bazi.get(i).getDb_version() + "  Version na serveru: " + ispit.getDb_version());

                                    if ((ispit.getDb_version() > ispiti_u_bazi.get(i).getDb_version()) //){//verzija baze veca na serveru
                                        //     || ispit.getDb_version() == 1
                                            ) {
                                        ispitiZaDownload.add(ispit);
                                        System.out.println("Dodao sam za download-> " + ispiti_sa_servera.get(i).getIme());
                                    }
                                } else
                                    ispitiZaDownload.add(ispit);
                            } else //prvi put pali app ili obrise svu datu
                            {
                                ispitiZaDownload.add(ispit);
                            }
                            i++;
                        }
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm bgRealm) {
                                bgRealm.copyToRealmOrUpdate(response);
                            }
                        });


                        for (Ispit ispit : ispitiZaDownload) {
                            Download download = new Download(c);
                            json = download.execute(ispit.getIme(), getMyToken(realm)).get(10000, TimeUnit.MILLISECONDS);
                            System.out.println("Downloadam : " + ispit.getIme());
                            if (!json.equals("failed")) {
                                System.out.println("{\"sva_pitanja\":" + json + "}");
                                Object s = DatabaseHelper.getObjectsForJsonMapping(ispit.getIme());
                                s = gson.fromJson("{\"sva_pitanja\":" + json + "}", DatabaseHelper.getClassesForJsonMapping(ispit.getIme()));
                                final Object finalS = s;
                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm bgRealm) {
                                        bgRealm.copyToRealmOrUpdate((RealmModel) finalS);
                                    }
                                });
                            } else
                                System.out.println("failed");
                        }


                    }
                    else //nije dobar token ili error. vratim te na login
                    {

                        LoginManager.getInstance().logOut();

                        Intent i = new Intent(c, Login.class);
                        finish();
                        startActivity(i);
                        return;
                    }





                } catch (InterruptedException e) {
                    e.printStackTrace();
                    LoginManager.getInstance().logOut();

                    Intent i = new Intent(c, Login.class);
                    finish();
                    startActivity(i);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    LoginManager.getInstance().logOut();

                    Intent i = new Intent(c, Login.class);
                    finish();
                    startActivity(i);
                } catch (TimeoutException e) {
                    e.printStackTrace();
                    LoginManager.getInstance().logOut();

                    Intent i = new Intent(c, Login.class);
                    finish();
                    startActivity(i);

                } catch (Exception e) {
                    e.printStackTrace();
                    LoginManager.getInstance().logOut();

                    Intent i = new Intent(c, Login.class);
                    finish();
                    startActivity(i);

                }

            }
        }, 200);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent i = new Intent(c, FirstInfo.class);
                finish();
                startActivity(i);
            }
        }, 500);
    }


    public String getMyToken(Realm realm)
    {
        ArrayList<User> list = new ArrayList(realm.where(User.class).findAll());
        String token = "";
        for(User s : list)
            token = list.get(0).getToken();

        return token;
    }


}
