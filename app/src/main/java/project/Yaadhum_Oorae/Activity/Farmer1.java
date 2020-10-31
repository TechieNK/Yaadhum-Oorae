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
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;

public class Farmer1 extends AppCompatActivity {
    private EditText crop_name,crop_area,crop_address,soil_type;
    private Button next;
    private String names, areas, address,type;
    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer1);
        crop_name = findViewById(R.id.crops_name);
        crop_area = findViewById(R.id.crops_land);
        crop_address = findViewById(R.id.area);
        soil_type = findViewById(R.id.type_soil);
        next = findViewById(R.id.nextBtn);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 names = crop_name.getText().toString();
                 areas = crop_area.getText().toString();
                 address = crop_address.getText().toString();
                 type = soil_type.getText().toString();
                 if (TextUtils.isEmpty(names) || TextUtils.isEmpty(areas) || TextUtils.isEmpty(address) || TextUtils.isEmpty(type))
                 {
                     Toast.makeText(Farmer1.this,"Please fill the details",Toast.LENGTH_SHORT).show();
                     return;
                 }
                 if(!(TextUtils.isEmpty(names)&&TextUtils.isEmpty(areas)&&TextUtils.isEmpty(address)&&TextUtils.isEmpty(type)))
                 {
                     usersRef = FirebaseDatabase.getInstance().getReference("Farmers");
                     uid = mAuth.getCurrentUser().getUid();
                     HashMap<String,Object> result=new HashMap<>();
                     result.put("Crop name",names);
                     result.put("Crop area",crop_area);
                     result.put("Crop address",crop_address);
                     result.put("Soil Type",soil_type);
                    usersRef.child(uid).updateChildren(result).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(Farmer1.this, "Great!", Toast.LENGTH_SHORT).show();
                                Intent mainIntent = new Intent(Farmer1.this, Proposal_giving.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainIntent);
                            }
                            else
                            {
                                String message = task.getException().getMessage();
                                Toast.makeText(Farmer1.this,"Error occurred. "+message, Toast.LENGTH_SHORT).show();
                                return;
                            }

                        }
                    });
                 }
            }
        });

    }
}