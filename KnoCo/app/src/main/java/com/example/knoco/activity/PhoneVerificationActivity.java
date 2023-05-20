package com.example.knoco.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.knoco.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneVerificationActivity extends AppCompatActivity {

    LottieAnimationView phoneAnimation ;
    Button phoneBtn ;
    TextView phoneNum ;
    private FirebaseAuth auth;
   // private static final String TAG = Signin.class.getName();

    private ProgressBar progress ;

    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK){
                        Intent intent = new Intent(PhoneVerificationActivity.this, RegisterActivity.class);
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                }
            });

    public void BindingObject(){
        phoneNum = findViewById(R.id.phoneNum);
        auth = FirebaseAuth.getInstance();
        phoneAnimation = findViewById(R.id.phoneAnimation);
        phoneBtn = findViewById(R.id.phonebtn);
        progress =findViewById(R.id.progressBar);
    }

    public void BindingAction() {
        phoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.setVisibility(View.VISIBLE);
                onClickVerifyPhoneNumber();
            }
        });
    }

    private void onClickVerifyPhoneNumber() {

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber("+84"+ phoneNum.getText().toString())       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        progress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        progress.setVisibility(View.GONE);
                        Toast.makeText(PhoneVerificationActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(verificationId,forceResendingToken);
                        progress.setVisibility(View.GONE);
                        Intent intent = new Intent(PhoneVerificationActivity.this, OtpVerifiicationActivity.class);
                        intent.putExtra("verification",verificationId);
                        intent.putExtra("phone",phoneNum.getText().toString());
                        mActivityResultLauncher.launch(intent);

                    }
                })          // OnVerificationStateChangedCallbacks
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);
        BindingObject();
        BindingAction();
    }
}