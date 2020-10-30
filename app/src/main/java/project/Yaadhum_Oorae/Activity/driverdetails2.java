package project.Yaadhum_Oorae.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class driverdetails2 extends AppCompatActivity {

    private EditText licenseET,chargeET,MobilenoET;
    String license,charge,worktime,mobileno;
    private TextView dayT,nightT,anyT;
    boolean dayb,nightb,anyb;
    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;
    private Button NextButton;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driverdetails2);
        mAuth=FirebaseAuth.getInstance();
        licenseET=findViewById(R.id.dd2_licenseno);
        chargeET=findViewById(R.id.dd2_charge);
        MobilenoET = findViewById(R.id.mobileno);

        dayT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dayT.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                nightT.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                anyT.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                dayT.setTextColor(getResources().getColor(R.color.colorWhite));
                nightT.setTextColor(getResources().getColor(R.color.grey));
                anyT.setTextColor(getResources().getColor(R.color.grey));
                dayb = true;
                nightb = false;
                anyb = false;
            }
        });
        nightT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dayT.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                nightT.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                anyT.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                nightT.setTextColor(getResources().getColor(R.color.colorWhite));
                dayT.setTextColor(getResources().getColor(R.color.grey));
                anyT.setTextColor(getResources().getColor(R.color.grey));
                dayb = false;
                nightb = true;
                anyb = false;

            }
        });
        anyT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dayT.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                nightT.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                anyT.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                anyT.setTextColor(getResources().getColor(R.color.colorWhite));
                dayT.setTextColor(getResources().getColor(R.color.grey));
                nightT.setTextColor(getResources().getColor(R.color.grey));
                dayb = false;
                nightb = false;
                anyb = true;

            }
        });



        NextButton = findViewById(R.id.nextBtn);
        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dayb || nightb || anyb)
                {
                    if (dayb)
                        worktime = "Day";
                    if(nightb)
                        worktime = "Night";
                    if(anyb)
                        worktime = "Any";
                }

                license = licenseET.getText().toString();
                charge = chargeET.getText().toString();
                mobileno = MobilenoET.getText().toString();

                if(TextUtils.isEmpty(license) || TextUtils.isEmpty(charge) || TextUtils.isEmpty(worktime) || TextUtils.isEmpty(mobileno))
                {
                    Toast.makeText(driverdetails2.this,  "Please fill the details. ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!(TextUtils.isEmpty(license) && TextUtils.isEmpty(charge) && TextUtils.isEmpty(worktime) && TextUtils.isEmpty(mobileno) ))
                {
                    usersRef = FirebaseDatabase.getInstance().getReference("Driver Details");
                    uid = mAuth.getCurrentUser().getUid();
                    HashMap<String,Object> result=new HashMap<>();
                    result.put("License No",license);
                    result.put("Mobile No",mobileno);
                    result.put("Wrok-Time",worktime);
                    result.put("Charge_per_hr",charge);
                    usersRef.child(uid).updateChildren(result).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(driverdetails2.this, "Great!", Toast.LENGTH_SHORT).show();
                                Intent mainIntent = new Intent(driverdetails2.this, driverdetails1.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainIntent);
                            }
                            else
                            {
                                String message = task.getException().getMessage();
                                Toast.makeText(driverdetails2.this, "Error occurred. "+message, Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    });
                }
            }
        });

    }
}