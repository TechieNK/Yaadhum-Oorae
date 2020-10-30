package project.Yaadhum_Oorae.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class PersonalDetailsActivity extends AppCompatActivity {

    private EditText NameET, MobilenoET,CityET,DistrictET;
    private TextView MaleT, FemaleT, OtherT, DateT,farmerT,driverT,factoryownerT;
    private Button NextButton;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    boolean maleb,femaleb, otherb,farmerb,driverb,factoryownerb;
    String name,gender,date,role,mobileno,city,district;
    int year,month,day;
    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);
        mAuth=FirebaseAuth.getInstance();
        MaleT = findViewById(R.id.male_select);
        FemaleT = findViewById(R.id.female_select);
        OtherT = findViewById(R.id.other_select);
        DateT = findViewById(R.id.pd_dob);
        farmerT = findViewById(R.id.farmer_select);
        factoryownerT = findViewById(R.id.factoryowner_select);
        driverT = findViewById(R.id.driver_select);

        Toast.makeText(PersonalDetailsActivity.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
        DateT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        PersonalDetailsActivity.this,
                        android.R.style.Theme_Material_Light_Dialog,
                        mDateSetListener,
                        day,month,year
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int a, int b, int c) {
                year = a;
                month = b+1;
                day = c;
                date = day + "/" + month + "/" + year;
                DateT.setText(date);
            }
        };

        MaleT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaleT.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                FemaleT.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                OtherT.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                MaleT.setTextColor(getResources().getColor(R.color.colorWhite));
                OtherT.setTextColor(getResources().getColor(R.color.grey));
                FemaleT.setTextColor(getResources().getColor(R.color.grey));
                maleb = true;
                femaleb = false;
                otherb = false;
            }
        });
        FemaleT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaleT.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                FemaleT.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                OtherT.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                FemaleT.setTextColor(getResources().getColor(R.color.colorWhite));
                MaleT.setTextColor(getResources().getColor(R.color.grey));
                OtherT.setTextColor(getResources().getColor(R.color.grey));
                maleb = false;
                femaleb = true;
                otherb = false;

            }
        });
        OtherT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaleT.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                FemaleT.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                OtherT.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                OtherT.setTextColor(getResources().getColor(R.color.colorWhite));
                MaleT.setTextColor(getResources().getColor(R.color.grey));
                FemaleT.setTextColor(getResources().getColor(R.color.grey));
                maleb = false;
                femaleb = false;
                otherb = true;

            }
        });
        farmerT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                farmerT.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                driverT.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                farmerT.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                farmerT.setTextColor(getResources().getColor(R.color.colorWhite));
                driverT.setTextColor(getResources().getColor(R.color.grey));
                factoryownerT.setTextColor(getResources().getColor(R.color.grey));
                farmerb = true;
                driverb = false;
                factoryownerb = false;
            }
        });
        driverT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                farmerT.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                driverT.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                factoryownerT.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                driverT.setTextColor(getResources().getColor(R.color.colorWhite));
                factoryownerT.setTextColor(getResources().getColor(R.color.grey));
                farmerT.setTextColor(getResources().getColor(R.color.grey));
                farmerb = false;
                driverb = true;
                factoryownerb = false;

            }
        });
        factoryownerT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                farmerT.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                driverT.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                factoryownerT.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                factoryownerT.setTextColor(getResources().getColor(R.color.colorWhite));
                farmerT.setTextColor(getResources().getColor(R.color.grey));
                driverT.setTextColor(getResources().getColor(R.color.grey));
                farmerb = false;
                driverb = false;
                factoryownerb = true;

            }
        });



        NameET = findViewById(R.id.pd_name);
        MobilenoET = findViewById(R.id.mobileno);
        CityET = findViewById(R.id.pd_city);
        DistrictET = findViewById(R.id.pd_district);
        NextButton = findViewById(R.id.nextBtn);
        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(maleb || femaleb || otherb)
                {
                    if (maleb)
                        gender = "Male";
                    if(femaleb)
                        gender = "Female";
                    if(otherb)
                        gender = "Other";
                }
                if(farmerb || driverb || factoryownerb)
                {
                    if (farmerb)
                        role = "Farmer";
                    if(driverb)
                        role = "Driver";
                    if(factoryownerb)
                        role = "Factory-Owner";
                }
                name=NameET.getText().toString();
                mobileno = MobilenoET.getText().toString();
                city = CityET.getText().toString();
                district = DistrictET.getText().toString();
                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(date) || TextUtils.isEmpty(gender) || TextUtils.isEmpty(role) ||
                        TextUtils.isEmpty(mobileno) || TextUtils.isEmpty(city) || TextUtils.isEmpty(district))
                {
                    Toast.makeText(PersonalDetailsActivity.this,  "Please fill the details. ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!(TextUtils.isEmpty(name) && TextUtils.isEmpty(date) && TextUtils.isEmpty(gender) && TextUtils.isEmpty(role) &&
                        TextUtils.isEmpty(mobileno) && TextUtils.isEmpty(city) && TextUtils.isEmpty(district)))
                {

                    usersRef = FirebaseDatabase.getInstance().getReference("Users");
                    uid = mAuth.getCurrentUser().getUid();
                    HashMap<String,Object> result=new HashMap<>();
                    result.put("Name",name);
                    result.put("DOB",date);
                    result.put("Gender",gender);
                    result.put("Mobile No",mobileno);
                    result.put("City","city");
                    result.put("District",district);
                    result.put("Role",role);
                    usersRef.child(uid).updateChildren(result).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(PersonalDetailsActivity.this, "Great!", Toast.LENGTH_SHORT).show();
                                if(role == "Driver") {
                                    Intent mainIntent = new Intent(PersonalDetailsActivity.this, driverdetails1.class);
                                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(mainIntent);
                                }
                                else if(role =="Farmer") {
                                    Intent mainIntent = new Intent(PersonalDetailsActivity.this, Farmer1.class);
                                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(mainIntent);
                                }
                                else if(role =="Factory-Owner") {
                                    Intent mainIntent = new Intent(PersonalDetailsActivity.this, activity_vendor.class);
                                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(mainIntent);
                                }

                            }
                            else
                            {
                                String message = task.getException().getMessage();
                                Toast.makeText(PersonalDetailsActivity.this, "Error occurred. "+message, Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    });
                }
            }


        });

    }
}