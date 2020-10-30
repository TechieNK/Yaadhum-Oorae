package project.Yaadhum_Oorae.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class driverdetails1 extends AppCompatActivity {

    private EditText vnameET,vmodelET,regnoET,vinET, enET,holdcapacityET;
    String vname,vmodel,regno,vin,en,holdcapacity;
    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;
    private Button NextButton;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driverdetails1);
        mAuth=FirebaseAuth.getInstance();
        vnameET = findViewById(R.id.dd1_name);
        vmodelET = findViewById(R.id.dd1_model);
        regnoET = findViewById(R.id.dd1_registrationno);
        vinET = findViewById(R.id.dd1_vinno);
        enET = findViewById(R.id.dd1_engineno);
        holdcapacityET = findViewById(R.id.dd1_capacity);


        NextButton = findViewById(R.id.nextBtn1);
        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vname = vnameET.getText().toString();
                vmodel = vmodelET.getText().toString();
                regno = regnoET.getText().toString();
                vin = vinET.getText().toString();
                en = enET.getText().toString();
                holdcapacity = holdcapacityET.getText().toString();

                if(TextUtils.isEmpty(vname) || TextUtils.isEmpty(vmodel) || TextUtils.isEmpty(regno) || TextUtils.isEmpty(vin) ||
                        TextUtils.isEmpty(en) || TextUtils.isEmpty(holdcapacity))
                {
                    Toast.makeText(driverdetails1.this,  "Please fill the details. ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!(TextUtils.isEmpty(vname) && TextUtils.isEmpty(vmodel) && TextUtils.isEmpty(regno) && TextUtils.isEmpty(vin) &&
                    TextUtils.isEmpty(en) && TextUtils.isEmpty(holdcapacity)))
                {

                    usersRef = FirebaseDatabase.getInstance().getReference("Vehicle-Details");
                    uid = mAuth.getCurrentUser().getUid();
                    HashMap<String,Object> result=new HashMap<>();
                    result.put("Vechicle-Name",vname);
                    result.put("Vechicle-Model",vmodel);
                    result.put("Registration No",regno);
                    result.put("Engine No",en);
                    result.put("Capacity",holdcapacity);
                    usersRef.child(uid).updateChildren(result).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(driverdetails1.this, "Great!", Toast.LENGTH_SHORT).show();
                                Intent mainIntent = new Intent(driverdetails1.this, driverdetails2.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainIntent);
                            }
                            else
                            {
                                String message = task.getException().getMessage();
                                Toast.makeText(driverdetails1.this, "Error occurred. "+message, Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    });
                }
            }


        });

    }
}