package com.firetera.percyv2.LogIn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firetera.percyv2.R;
import com.firetera.percyv2.RegistrationJavaClass.AskingForEmail;

public class AskingForFullNameLogIn extends AppCompatActivity {

    EditText fullName;
    Button next_Btn;
    public static String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asking_for_full_name_log_in);

        fullName = findViewById(R.id.AFFLIname);
        next_Btn = findViewById(R.id.AFFLInxt_Btn);

        next_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fullName.getText().toString().isEmpty()) {
                    fullName.setError("Enter Full name");
                    return;
                }
                startActivity(new Intent(getApplicationContext(), AskingForEmailLogIn.class));

                name = fullName.getText().toString();
            }
        });
    }
}