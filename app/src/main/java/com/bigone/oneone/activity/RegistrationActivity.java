package com.bigone.oneone.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bigone.oneone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class RegistrationActivity extends AppCompatActivity {

    private CountryCodePicker cpp;
    private EditText phoneText;
    private EditText codeText;
    private Button continueAndNextBtn;
    private String checker = "", phoneNumber = "";
    private LinearLayout linearLayout;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);

        phoneText = findViewById(R.id.phoneText);
        codeText = findViewById(R.id.codeText);
        continueAndNextBtn = findViewById(R.id.continueNextButton);
        linearLayout = findViewById(R.id.phoneAuth);

        cpp = (CountryCodePicker) findViewById(R.id.ccp);
        cpp.registerCarrierNumberEditText(phoneText);

        continueAndNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (continueAndNextBtn.getText().equals("G???i ??i") || checker.equals("???? g???i m??")) {
                    String verificationCode = codeText.getText().toString();
                    if(verificationCode.equals("")){
                        Toast.makeText(RegistrationActivity.this, "L??m ??n nh???p m?? s??? x??c nh???n c???a b???n", Toast.LENGTH_SHORT).show();

                    }else{
                        loadingBar.setTitle("M?? s??? x??c nh???n");
                        loadingBar.setMessage("Vui l??ng ?????i trong khi ch??ng t??i x??c minh m?? s??? x??c nh???n c???a b???n");
                        loadingBar.setCanceledOnTouchOutside(false);
                        loadingBar.show();

                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verificationCode);
                        signInWithPhoneAuthCredential(credential);
                    }
                }
                else{
                    phoneNumber = cpp.getFullNumberWithPlus();
                    if (!phoneNumber.equals("")) {
                        loadingBar.setTitle("X??c minh s??? ??i???n tho???i");
                        loadingBar.setMessage("Vui l??ng ?????i trong khi ch??ng t??i x??c minh s??? ??i???n tho???i c???a b???n");
                        loadingBar.setCanceledOnTouchOutside(false);
                        loadingBar.show();

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, RegistrationActivity.this, mCallbacks);

                    }
                    else {
                        Toast.makeText(RegistrationActivity.this, "L??m ??n nh???p l???i s??? ??i??n tho???i c???a b???n", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(RegistrationActivity.this,"\n" +
                        "S??? ??i???n tho???i...", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
                linearLayout.setVisibility(View.VISIBLE);
                continueAndNextBtn.setText("Ti???p t???c");
                codeText.setVisibility(View.GONE);

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                mVerificationId = s;
                mResendToken = forceResendingToken;

                linearLayout.setVisibility(View.GONE);
                checker = "???? g???i m??";
                continueAndNextBtn.setText("G???i ??i");
                codeText.setVisibility(View.VISIBLE);
                loadingBar.dismiss();
                Toast.makeText(RegistrationActivity.this,"\n" +
                        "M?? s??? x??c nh???n ???? ???????c g???i, b???n ki???m tra xem", Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null){
            Intent homeintent = new Intent(RegistrationActivity.this, OneOneActivity.class);
            startActivity(homeintent);
            finish();
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            loadingBar.dismiss();
                            Toast.makeText(RegistrationActivity.this,"\n" +
                                    "Ch??c m???ng, b???n ???? ????ng nh???p th??nh c??ng", Toast.LENGTH_SHORT).show();
                            sendUserToOneFaceTimeMainActivity();
                        } else {
                            loadingBar.dismiss();
                            String e = task.getException().toString();
                            Toast.makeText(RegistrationActivity.this,"\n" +
                                    "Error: " +e, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void sendUserToOneFaceTimeMainActivity(){
        Intent intent = new Intent(RegistrationActivity.this, OneOneActivity.class);
        startActivity(intent);
        finish();
    }
}