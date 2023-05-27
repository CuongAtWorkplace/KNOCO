package com.example.knoco.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.knoco.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpVerifiicationActivity extends AppCompatActivity {

    private TextView Retry ;
    private Button confirmbtn ;
    private TextView otp0 ;
    private TextView otp1 ;
    private TextView otp2 ;
    private TextView otp3 ;
    private TextView otp4 ;
    private TextView otp5 ;
    private String OTP ;
    String otp ;
    private ProgressBar progress ;
    private FirebaseAuth auth;
  private static final String TAG = RegisterActivity.class.getName();



    public void BindingData(){

        progress = findViewById(R.id.progressBar);
        Retry =findViewById(R.id.retry);
        confirmbtn = findViewById(R.id.confirmbtn);
        otp0 = findViewById(R.id.otp0);
        otp1 = findViewById(R.id.otp1);
        otp2 = findViewById(R.id.otp2);
        otp3 = findViewById(R.id.otp3);
        otp4 = findViewById(R.id.otp4);
        otp5 = findViewById(R.id.otp5);

    }

    public void BindingAction() {

        TextView[] otpTextViews = {otp0, otp1, otp2, otp3, otp4,otp5};


        for(TextView currTextView : otpTextViews){
            currTextView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    nextTextView().requestFocus();
                }

                @Override
                public void afterTextChanged(Editable s) {
                }

                public TextView nextTextView() {
                    int i;
                    for (i = 0; i < otpTextViews.length - 1; i++) {
                        if (otpTextViews[i] == currTextView)
                            return otpTextViews[i + 1];
                    }
                    return otpTextViews[i];
                }
            });
        }

        otp = getIntent().getStringExtra("verification") ;
        final String[] phone = new String[1];//= {getIntent().getStringExtra("phone")};
        Retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {


                    PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                            .setPhoneNumber("+84" + phone[0])       // Phone number to verify
                            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                            .setActivity(OtpVerifiicationActivity.this)
                            // Activity (for callback binding)
                            .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    progress.setVisibility(View.GONE);
                                    Toast.makeText(OtpVerifiicationActivity.this, "not OK", Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    progress.setVisibility(View.GONE);
                                }

                                @Override
                                public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    super.onCodeSent(verificationId, forceResendingToken);
                                    progress.setVisibility(View.GONE);
                                }
                            })          // OnVerificationStateChangedCallbacks
                            .build();
                    PhoneAuthProvider.verifyPhoneNumber(options);
                } catch (Exception e) {
                    Toast.makeText(OtpVerifiicationActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        confirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OTP = otp0.getText().toString() + otp1.getText().toString()
                        + otp2.getText().toString() + otp3.getText().toString()
                        + otp4.getText().toString() + otp5.getText().toString();


                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otp, OTP);
                FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(OtpVerifiicationActivity.this, PhoneVerificationActivity.class);
                                    setResult(RESULT_OK, intent);
                                    finish();
                                } else {
                                    Toast.makeText(OtpVerifiicationActivity.this, "Please re enter OTP", Toast.LENGTH_LONG).show();
                                }
                            }


                        });
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verifiication);
        BindingData();
        BindingAction();
    }
}