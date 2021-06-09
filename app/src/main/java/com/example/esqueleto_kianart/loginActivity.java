package com.example.esqueleto_kianart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginActivity extends AppCompatActivity {
    TextView lblCrearCuenta;
    EditText txtInputEmail, txtInputPassword;
    Button btnLogin;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private ProgressDialog mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_login);
        txtInputEmail = findViewById(R.id.inputEmail);
        txtInputPassword = findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.btnlogin);
        lblCrearCuenta = findViewById(R.id.txtNotieneCuenta);

        lblCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(loginActivity.this,RegisterActivity.class));
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificarCredenciales();
            }
        });

        mProgressBar = new ProgressDialog(loginActivity.this);
        mAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //redirigir si no es nulo
                if(firebaseAuth.getCurrentUser() != null){
                    Intent intenMainActivity = new Intent(getApplicationContext(), MenuPrincipalActivity.class);
                    intenMainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intenMainActivity);

                }



            }
        };


    }

    public void verificarCredenciales(){
        String email = txtInputEmail.getText().toString();
        String password = txtInputPassword.getText().toString();
        if(email.isEmpty() || !email.contains("@")){
            showError(txtInputEmail, "Email no valido");
        }else if(password.isEmpty()|| password.length()<7){
            showError(txtInputPassword, "Password invalida");
        }else{
            //Mostrar ProgressBar
            mProgressBar.setTitle("Login");
            mProgressBar.setMessage("Iniciando sesión, espere un momento..");
            mProgressBar.setCanceledOnTouchOutside(false);
            mProgressBar.show();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        //ocultar progressBar
                        mProgressBar.dismiss();
                        // cambiar direccion a la que es
                        Intent intent = new Intent(loginActivity.this, MenuPrincipalActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }else {
                        Toast.makeText(getApplicationContext(), "no se puedo iniciar sesion, verifique correo/Contrasena", Toast.LENGTH_LONG).show();
                    }

                }
            });

        }
    }

    private void showError(EditText input, String s){
        input.setError(s);
        input.requestFocus();
    }

    @Override
    protected void onStart() {
        mAuth.addAuthStateListener(mAuthStateListener);
        super.onStart();



    }

}
