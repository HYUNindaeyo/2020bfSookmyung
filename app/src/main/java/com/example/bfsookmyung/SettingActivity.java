package com.example.bfsookmyung;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class SettingActivity extends AppCompatActivity {

    private Button button;
    private String str;
    EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);



        button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });


    }
    public void savedata(View view) {
        EditText editTextNumber = (EditText) findViewById(R.id.number);
        String strSaveData = editTextNumber.getText().toString();

        getBaseContext();
        SharedPreferences sharedPref = this.getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.savedata_public_key),strSaveData);
        editor.commit();


        //TextView textView = (TextView) findViewById(R.id.textView5);
        //textView.setText(strSaveData);
    }


}
