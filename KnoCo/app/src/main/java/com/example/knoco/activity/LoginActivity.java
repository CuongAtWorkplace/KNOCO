package com.example.knoco.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.knoco.R;
import com.example.knoco.model.Validation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private Button Login ;
    private TextView signin ;
    private TextView userName ;
    private TextView passw ;

    private TextView wrong ;
    private TextView Forgettxt ;

    private FirebaseAuth mAuth ;


    public void BindingObject(){
        Login = findViewById(R.id.loginbtn);
        signin = findViewById(R.id.signin);
        userName = findViewById(R.id.userName);
        passw = findViewById(R.id.pass);
        Forgettxt = findViewById(R.id.forget);
        mAuth = FirebaseAuth.getInstance();
        wrong = findViewById(R.id.wrong);

    }

    public void BindingAction(){
        if(mAuth.getCurrentUser() != null){
            Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(myIntent);
        }

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(myIntent);
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                wrong.setVisibility(View.INVISIBLE);

                if(checkValidation()) {
                    signIn();
                }
            }
        });
        Forgettxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_forget,null);
                EditText emailtxt = dialogView.findViewById(R.id.EmailTxt);
                Toast.makeText(LoginActivity.this, emailtxt.toString() , Toast.LENGTH_SHORT).show();
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();
                dialogView.findViewById(R.id.Sendbtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String email = emailtxt.getText().toString();
                        Validation validation = new Validation();
                        if(TextUtils.isEmpty(email) && validation.CheckEmail(email)<=0 ){
                            Toast.makeText(LoginActivity.this, "Enter wrong format of Email", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(LoginActivity.this, "Check your email", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                                else {
                                    Toast.makeText(LoginActivity.this, "Unable to send", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });


                dialogView.findViewById(R.id.Cancelbtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                if(dialog.getWindow() !=null){
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                dialog.show();
            }

        });
    }

    private boolean checkValidation() {
        String emai = userName.getText().toString();
        String pass = passw.getText().toString();
        Validation v = new Validation();
        if (v.CheckEmail(emai) == -1) {
            userName.setError("Please type your email");
            return false ;
        }
        else if(v.CheckPassword(pass)== -1){
            passw.setError("Please type your Password");
            return false ;
        }else {
            return true ;
        }
    }

    private void signIn() {
        String emai = userName.getText().toString();
        String pass = passw.getText().toString();
        mAuth.signInWithEmailAndPassword(emai , pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    wrong.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        BindingObject();
        BindingAction();
    }
}