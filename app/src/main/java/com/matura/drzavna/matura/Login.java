package com.matura.drzavna.matura;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
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

public class Login extends Activity {

    EditText user_name, password;
    Button prijava;
    LoginButton fb;
    Context c;
    Realm realm;
    CallbackManager callbackManager;
    AccessToken accessToken;
    AccessTokenTracker accessTokenTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        callbackManager = CallbackManager.Factory.create();
        c = this;


        Realm.init(this);
        realm = DatabaseHelper.resetRealm();


        AccessToken fb_token = AccessToken.getCurrentAccessToken();
        if (fb_token != null) {
            Intent i = new Intent(c, StartLoadingScreen.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
        fb = (LoginButton) findViewById(R.id.login_button);
        List<String> permissionNeeds = Arrays.asList("email", "public_profile");
        fb.setReadPermissions(permissionNeeds);
        fb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            saveFbUserToDb(object);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (TimeoutException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast.makeText(c, "Canceled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(c, "Error", Toast.LENGTH_SHORT).show();
            }
        });


        user_name = (EditText) findViewById(R.id.editTextEmail);
        password = (EditText) findViewById(R.id.editTextPassword);
        prijava = (Button) findViewById(R.id.buttonPrijava);
        prijava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regularLogin();
            }
        });
    }

    private void saveTokenToUser(String username, String password, String token) {
        try {
            User user = realm.where(User.class).equalTo("email", username).findFirst();
            if (user == null) {
                createNewUser(username, password, token);
            } else {
                realm.beginTransaction();
                user.setToken(token);
                realm.commitTransaction();
                realm.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void regularLogin() {
        prijava.setBackgroundColor(Color.parseColor("#000000"));
        prijava.setTextColor(Color.WHITE);
        prijava.setAlpha((float) 0.5);
        JSONObject obj = new JSONObject();
        try {
            if (user_name.getText().toString().equalsIgnoreCase("") || password.getText().toString().equalsIgnoreCase("")) {
                Toast.makeText(c, "Some fields are empty", Toast.LENGTH_SHORT).show();
            } else {
                obj.put("email", user_name.getText());
                obj.put("user_password", password.getText());
                Upload u = new Upload();
                JSONObject response = new JSONObject(u.execute(obj.toString()).get(10000, TimeUnit.MILLISECONDS));
                if (response.getInt("status") == 200) {
                    Toast.makeText(c, "Successfull Login", Toast.LENGTH_SHORT).show();

                    saveTokenToUser(user_name.getText().toString(), password.getText().toString(), response.get("token").toString());

                    Intent i = new Intent(c, StartLoadingScreen.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                } else {
                    Toast.makeText(c, "Error", Toast.LENGTH_SHORT).show();
                }


            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
            Toast.makeText(c, "Error", Toast.LENGTH_SHORT).show();
        }

        prijava.setBackgroundResource(R.drawable.gray_button);
        prijava.setTextColor(Color.DKGRAY);
        prijava.setAlpha((float) 1.0);

    }

    private void createNewUser(final String email, final String password, final String token) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                User me2 = null;
                me2 = new User(user_name.getText().toString(), email, password, token, "", false, "Android");
                bgRealm.copyToRealmOrUpdate(me2);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void saveFbUserToDb(final JSONObject object) throws JSONException, InterruptedException, ExecutionException, TimeoutException {

        if (object.getString("first_name") != null && object.getString("last_name") != null && object.getString("id") != null) {

            Gson gson = new GsonBuilder().setLenient().create();
            Upload u = new Upload();

            final User me = new User(object.getString("first_name") + " " + object.getString("last_name"), "facebook-email", "facebook", "", object.getString("id"), true, "Android");
            System.out.println(gson.toJson(me));
            final JSONObject response = new JSONObject(u.execute(gson.toJson(me)).get(10000, TimeUnit.MILLISECONDS).trim());
            if (response.getInt("status") == 200) {
                Toast.makeText(c, "Successfully Registered", Toast.LENGTH_LONG).show();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm bgRealm) {
                        User me2 = null;
                        try {
                            me2 = new User(object.getString("first_name") + " " + object.getString("last_name"), "facebook-email" + "",
                                    "facebook", response.getString("token"), object.getString("id"), true, "Android");
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
        else
        {
            Intent i = new Intent(c, StartLoadingScreen.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);}
    }

}
