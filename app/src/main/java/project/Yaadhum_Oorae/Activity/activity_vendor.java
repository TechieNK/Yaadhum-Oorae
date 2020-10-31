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

public class activity_vendor extends AppCompatActivity {
    private EditText Fname,FAddress,FContact,FItems;
    private Button next;
    private String name, address,contact,items;
    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor);
        Fname = findViewById(R.id.pd_name);
        FAddress = findViewById(R.id.add);
        FContact = findViewById(R.id.num);
        FItems  = findViewById(R.id.raw);
        next = findViewById(R.id.nextBtn);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = Fname.getText().toString();
                address = FAddress.getText().toString();
                contact = FContact.getText().toString();
                items = FItems.getText().toString();
                if(TextUtils.isEmpty(name)||TextUtils.isEmpty(address)||TextUtils.isEmpty(contact)||TextUtils.isEmpty(items))
                {
                    Toast.makeText(activity_vendor.this,"Please fill the details",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!(TextUtils.isEmpty(name)&&TextUtils.isEmpty(address)&&TextUtils.isEmpty(contact)&&TextUtils.isEmpty(items)))
                {
                    usersRef = FirebaseDatabase.getInstance().getReference("Vendor");
                    uid = mAuth.getCurrentUser().getUid();
                    HashMap<String,Object> result=new HashMap<>();
                    result.put("Name",name);
                    result.put("Address",address);
                    result.put("Contact",contact);
                    result.put("Items",items);
                    usersRef.child(uid).updateChildren(result).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(activity_vendor.this, "Great!", Toast.LENGTH_SHORT).show();
                                Intent mainIntent = new Intent(activity_vendor.this, activity_proposal_accept.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainIntent);
                            }
                            else
                            {
                                String message = task.getException().getMessage();
                                Toast.makeText(activity_vendor.this,"Error occurred. "+message, Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    });
                }
            }
        });

    }
}