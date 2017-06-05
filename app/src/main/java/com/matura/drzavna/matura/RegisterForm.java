package com.matura.drzavna.matura;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.matura.drzavna.matura.models.User;
import com.matura.drzavna.matura.support.DatabaseHelper;
import com.matura.drzavna.matura.support.Upload;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.exceptions.RealmMigrationNeededException;

public class RegisterForm extends Activity {

    EditText user_name, password, email;
    Button prijava;
    Context c;
    LoginButton fb;

    CallbackManager callbackManager;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_register_form);

        c = this;

        Realm.init(this);
        realm = DatabaseHelper.resetRealm();

        fb = (LoginButton) findViewById(R.id.login_button);

        user_name = (EditText)findViewById(R.id.editTextIme);
        password = (EditText)findViewById(R.id.editTextZaporka);
        email = (EditText)findViewById(R.id.editTextemail);
        prijava = (Button) findViewById(R.id.buttonRegistrirajSe);

        List<String> permissionNeeds = Arrays.asList("email", "public_profile");
        fb.setReadPermissions(permissionNeeds);
        fb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                try {
                    AccessToken accessToken = loginResult.getAccessToken();
                    final Profile profile = Profile.getCurrentProfile();
                    Gson gson = new GsonBuilder().setLenient().create();
                    Upload u = new Upload();
                    final User me = new User(profile.getName(), "facebook", "facebook",  "", profile.getId(), true, "Android");
                    final JSONObject response = new JSONObject(u.execute(gson.toJson(me)).get(10000, TimeUnit.MILLISECONDS).trim());
                    if (response.getInt("status") == 200) {
                        Toast.makeText(c, "Successfully Registered", Toast.LENGTH_LONG).show();
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm bgRealm) {
                                User me2 = null;
                                try {
                                    me2 = new User(profile.getName(), "facebook", "facebook", response.getString("token"), profile.getId(), true, "Android");
                                    bgRealm.copyToRealmOrUpdate(me2);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                        Intent i = new Intent(c, StartLoadingScreen.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    } else if (response.getInt("status") == 400) {
                        Toast.makeText(c, "Email already in use", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(c, "Error", Toast.LENGTH_LONG).show();


                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    LoginManager.getInstance().logOut();
                }
            }

            @Override
            public void onCancel() {
                Toast.makeText(c, "Canceled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(c, "Error", Toast.LENGTH_SHORT).show();
                LoginManager.getInstance().logOut();
            }
        });




        prijava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prijava.setBackgroundResource(R.drawable.default_button);
                prijava.setTextColor(Color.WHITE);
                try {
                    if(user_name.getText().toString().equalsIgnoreCase("") || password.getText().toString().equalsIgnoreCase("")
                            || email.getText().toString().equalsIgnoreCase("")) {
                        Toast.makeText(c, "Some fields are empty", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Gson gson = new GsonBuilder().setLenient().create();
                        Upload u = new Upload();
                        final User me = new User(user_name.getText().toString(), email.getText().toString(),password.getText().toString(),"" ,"", false,"Android");
                        System.out.println(gson.toJson(me));
                        final JSONObject response = new JSONObject(u.execute(gson.toJson(me)).get(10000, TimeUnit.MILLISECONDS).trim());
                        if (response.getInt("status") == 200) {
                            Toast.makeText(c, "Successfully Registered", Toast.LENGTH_LONG).show();
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm bgRealm) {
                                    User me2 = null;
                                    try {
                                        me2 = new User(user_name.getText().toString(), email.getText().toString(),password.getText().toString(),response.getString("token") ,"", false,"Android");
                                        bgRealm.copyToRealmOrUpdate(me2);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                            Intent i = new Intent(c, StartLoadingScreen.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                        } else if (response.getInt("status") == 400) {
                            Toast.makeText(c, "Email already in use", Toast.LENGTH_LONG).show();
                        } else
                            Toast.makeText(c, "Error", Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (NumberFormatException e)
                {
                    Toast.makeText(c, "Error", Toast.LENGTH_LONG).show();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
                catch(Exception e)
                {
                    LoginManager.getInstance().logOut();
                    e.printStackTrace();
                }

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
