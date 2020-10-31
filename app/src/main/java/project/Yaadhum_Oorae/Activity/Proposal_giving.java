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

public class Proposal_giving extends AppCompatActivity {
    private EditText quantity,expect,phone;
    private Button done;
    private String Quantity,Except,Phone;
    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposal_giving);
        quantity = findViewById(R.id.pd_name);
        expect = findViewById(R.id.amt);
        phone = findViewById(R.id.mobileno);
        done = findViewById(R.id.nextBtn);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Quantity = quantity.getText().toString();
                Except = expect.getText().toString();
                Phone = phone.getText().toString();

                if(TextUtils.isEmpty(Quantity)||TextUtils.isEmpty(Except)||TextUtils.isEmpty(Phone))
                {
                    Toast.makeText(Proposal_giving.this,"Please fill the details",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!(TextUtils.isEmpty(Quantity)&&TextUtils.isEmpty(Except)&&TextUtils.isEmpty(Phone)))
                {
                    usersRef = FirebaseDatabase.getInstance().getReference("Vendor");
                    uid = mAuth.getCurrentUser().getUid();
                    HashMap<String,Object> result=new HashMap<>();
                    result.put("Quantities",Quantity);
                    result.put("Amount",Except);
                    result.put("Phone",Phone);
                    usersRef.child(uid).updateChildren(result).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(Proposal_giving.this, "Great!", Toast.LENGTH_SHORT).show();
                                Intent mainIntent = new Intent(Proposal_giving.this, activity_proposal_accept.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainIntent);
                            }
                            else
                            {
                                String message = task.getException().getMessage();
                                Toast.makeText(Proposal_giving.this,"Error occurred. "+message, Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    });

                }

            }
        });
    }
}