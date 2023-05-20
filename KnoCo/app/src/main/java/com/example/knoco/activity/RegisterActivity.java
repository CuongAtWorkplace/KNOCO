package com.example.knoco.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.knoco.R;
import com.example.knoco.model.Validation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ktx.Firebase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {


    private TextView Email ;
    private TextView userName ;
    private TextView passw ;
    private TextView repassw ;
    private Button SignUp ;
    private TextView signin ;
    private TextView wrongtxt ;
    private FirebaseAuth mAuth ;
    DatabaseReference dbRef ;

    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {


                    if(result.getResultCode() == RESULT_OK){
                        String mUserEmail = Email.getText().toString();
                        String mPassword = passw.getText().toString();

                        mAuth = FirebaseAuth.getInstance();

                        mAuth.createUserWithEmailAndPassword(mUserEmail, mPassword)
                                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        if (!task.isSuccessful()) {

                                            try {
                                                throw task.getException();
                                            } catch (FirebaseAuthUserCollisionException e) {
                                                Toast.makeText(RegisterActivity.this, e.toString(),
                                                        Toast.LENGTH_SHORT).show();

                                            } catch (FirebaseNetworkException e) {
                                                Toast.makeText(RegisterActivity.this, e.toString(),
                                                        Toast.LENGTH_SHORT).show();
                                            } catch (Exception e) {
                                                // log error here
                                                Toast.makeText(RegisterActivity.this, e.toString(),
                                                        Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    }
                                });
                        RegisterFirebase();

                    }
                }
            });

    private void RegisterFirebase() {

        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        String Uid = firebaseUser.getUid();
        dbRef = FirebaseDatabase.getInstance().getReference("User").child(Uid);
        HashMap<String , String> hashMap = new HashMap<>();
        hashMap.put("id", Uid );
        hashMap.put("username",userName.getText().toString());

        dbRef.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(RegisterActivity.this,"Register failed",Toast.LENGTH_LONG);
                }

            }
        });


    }

    public void BindingObject(){
        Email = findViewById(R.id.Email) ;
        userName = findViewById(R.id.userName) ;
        SignUp = findViewById(R.id.SignUp) ;
        signin = findViewById(R.id.signin) ;
        wrongtxt = findViewById(R.id.wrongtxt);
        repassw = findViewById(R.id.repass);
        passw = findViewById(R.id.pass);
    }

    public void BindingAction(){




        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(myIntent);
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrongtxt.setVisibility(View.INVISIBLE);
                if(checkValidation()) {
                onClickGotoPhone();
                }
            }
        });

    }

    private boolean checkValidation() {
        String email = Email.getText().toString();
        String pass =  passw.getText().toString();
        String repass = repassw.getText().toString() ;
        String user = userName.getText().toString();

        Validation v = new Validation();

        if (v.CheckEmail(email) == -1) {
            Email.setError("Please type your email");
            return false ;
        }
        else if (v.CheckEmail(email) == 0) {
            Email.setError("Wrong Email Format");
            return false ;
        }
        else if(v.CheckPassword(pass)== -1){
            passw.setError("Please type your Password");
            return false ;
        }
        else if (v.CheckPassword(pass) == 0) {
            passw.setError("Wrong Password Format");
            return false ;
        }
        else if(!pass.equals(repass)){
            wrongtxt.setText("Pass and confirm pass are not matching");
            return false ;
        }
        else if(user.length()<4){
            userName.setError("Username must have more than 4 character");
            return false ;
        }
        else {
            return true ;
        }
    }

    private void onClickGotoPhone() {
        Intent myIntent = new Intent(RegisterActivity.this, PhoneVerificationActivity.class);
        mActivityResultLauncher.launch(myIntent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        BindingObject();
        BindingAction();
    }
}