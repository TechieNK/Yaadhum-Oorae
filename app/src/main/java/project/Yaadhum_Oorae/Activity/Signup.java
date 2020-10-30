package project.Yaadhum_Oorae.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signup extends AppCompatActivity {

    private EditText EmailET, PasswordET, RePasswordET;
    private Button SignupButton;
    private FirebaseAuth mAuth;
    String email, password, re_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        EmailET = findViewById(R.id.signup_email);
        PasswordET = findViewById(R.id.signup_password);
        RePasswordET = findViewById(R.id.signup_confirm_password);
        SignupButton = findViewById(R.id.signupButton);

        mAuth = FirebaseAuth.getInstance();

        SignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = EmailET.getText().toString();
                password = PasswordET.getText().toString();
                re_password = RePasswordET.getText().toString();
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(re_password))
                {
                    Toast.makeText(Signup.this,"All the fields are mandatory",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    Toast.makeText(Signup.this,"Invalid Email Address",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!(TextUtils.isEmpty(email) && TextUtils.isEmpty(password) && TextUtils.isEmpty(re_password)))
                {
                    if(password.equals(re_password))
                    {
                        mAuth.createUserWithEmailAndPassword(email,password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful())
                                        {
                                            SignupButton.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.green));
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(Signup.this, "Verification Link Has Been Sent", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @SuppressLint("SetTextI18n")
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(Signup.this, "Could not send Verification link. " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            Intent mainIntent = new Intent(Signup.this, Login.class);
                                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(mainIntent);

                                        }
                                        else
                                        {
                                            String message = task.getException().getMessage();
                                            Toast.makeText(Signup.this, "Error occurred. "+message, Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                    }
                    else
                    {
                        Toast.makeText(Signup.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

}
