package com.ejemplo.park_bs;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class GoogleUsuarioifo extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private ImageView imv_photoImageView;
    private TextView tv_name_user_google;
    private TextView tv_email_user_google;
    private TextView id_tv_googleus;

    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_usuarioifo);

        initComponents();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void initComponents() {
        imv_photoImageView = (ImageView)findViewById(R.id.imv_photoImageView);
        tv_name_user_google =(TextView)findViewById(R.id.tv_name_user_google);
        tv_email_user_google =(TextView)findViewById(R.id.tv_email_user_google);
        id_tv_googleus =(TextView)findViewById(R.id.id_tv_googleus);
    }

    @Override
    protected void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);

        if (opr.isDone()){
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        }else{
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if(result.isSuccess()){

            GoogleSignInAccount account = result.getSignInAccount();

            tv_name_user_google.setText(account.getDisplayName());
            tv_email_user_google.setText(account.getEmail());
            id_tv_googleus.setText(account.getId());

            //Log.d("MIAPP", account.getPhotoUrl().toString());
            Glide.with(this).load(account.getPhotoUrl()).into(imv_photoImageView);
        }else{
            iraPantallaLogin();
        }
    }

    private void iraPantallaLogin() {
        Intent intent = new Intent(this, ActivityLogin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void logOutOnClick(View v){
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()){
                    iraPantallaLogin();
                }else{
                    Toast.makeText(getApplicationContext(), "No se pudo cerrar sesión", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void revokeOnClick(View v){
        Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if(status.isSuccess()){
                    iraPantallaLogin();
                }else{
                    Toast.makeText(getApplicationContext(), "No se pudo revocar el acceso", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
