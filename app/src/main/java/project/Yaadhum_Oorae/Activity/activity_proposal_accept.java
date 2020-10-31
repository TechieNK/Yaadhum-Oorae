package project.Yaadhum_Oorae.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class activity_proposal_accept extends AppCompatActivity {
   private TextView show1,show2,show3;
   private Button next;
    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposal_accept);
        show3 = findViewById(R.id.mobileno);
        show1 = findViewById(R.id.pd_name);
        show2 = findViewById(R.id.pd_name);
        next = findViewById(R.id.nextBtn);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usersRef= FirebaseDatabase.getInstance().getReference().child("Vendor");
                usersRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        String q = snapshot.child("Quantities").getValue().toString();
                        String a = snapshot.child("Amount").getValue().toString();
                        String p = snapshot.child("Phone").getValue().toString();
                        show1.setText(a);
                        show2.setText(q);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {

                    }
                });
            }
        });
    }
}