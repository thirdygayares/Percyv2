package com.firetera.percyv2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

public class SignIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

    //calling master

        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);

        Button signinbtn = (Button) findViewById(R.id.signinbtn);
        Button googlesigninbtn = (Button) findViewById(R.id.googlesigninbtn);
        CheckBox showpassword = (CheckBox) findViewById(R.id.showpw);

        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals("ptc.jomaridanao@gmail.com") && password.getText().toString().equals("password")){
                    Toast.makeText(SignIn.this, "LOG IN SUCCESFULL", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignIn.this, PrecyHomePage.class);
                    startActivity(intent);

                }
                else {
                    Toast.makeText(SignIn.this, "LOG IN FAILED", Toast.LENGTH_SHORT).show();
                }
            }
        });

        password.setTransformationMethod(new PasswordTransformationMethod());
        showpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    password.setTransformationMethod(null);
                }
                else {
                    password.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });



    }
}