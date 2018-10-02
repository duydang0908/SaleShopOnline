package com.example.duyda.onlinesaleshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUpActivity extends AppCompatActivity {

    MaterialEditText edtPhone, edtName, edtPass;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtPhone = findViewById(R.id.edtPhoneNumber);
        edtName = findViewById(R.id.edtName);
        edtPass = findViewById(R.id.edtPassword);

        btnSignUp=findViewById(R.id.btnSignUp);

        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference table_account=database.getReference("Account");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog=new ProgressDialog(SignUpActivity.this);
                mDialog.setMessage("Vui lòng chờ...");
                mDialog.show();

                table_account.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(edtPhone.getText().toString()).exists()){
                            mDialog.dismiss();
                            Toast.makeText(SignUpActivity.this,"SĐT đã được đăng kí!",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            mDialog.dismiss();
                            Account account=new Account(edtName.getText().toString(),edtPass.getText().toString());
                            table_account.child(edtPhone.getText().toString()).setValue(account);
                            Toast.makeText(SignUpActivity.this,"Đăng kí thành công",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(SignUpActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

    }
}
