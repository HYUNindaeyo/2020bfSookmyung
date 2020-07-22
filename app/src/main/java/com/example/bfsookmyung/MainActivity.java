package com.example.bfsookmyung;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {


    public Button button1,button2,button3,button4;
    private final int MY_PERMISSION_REQUEST_SMS=1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)){


                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("info");
                builder.setMessage("this app won't work properly unless you grand SMS permission. ");
                builder.setIcon(android.R.drawable.ic_dialog_info);

                builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS},MY_PERMISSION_REQUEST_SMS);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},MY_PERMISSION_REQUEST_SMS);
            }

        }



        button3=(Button)findViewById(R.id.button3);
        button4=(Button)findViewById(R.id.button4);

        button3.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                SharedPreferences sharedPref = getSharedPreferences("SettingActivity",MODE_PRIVATE);
                String strLoadData = sharedPref.getString(getString(R.string.savedata_public_key),"01028260640");

                String smsText = "SOS TEST";



                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(strLoadData, null, smsText, null, null);
                Toast.makeText(getApplicationContext(),"SOS 전송완료",Toast.LENGTH_LONG).show();

                // 사용자의 OS 버전이 마시멜로우 이상인지 체크한다.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    int permissionResult = checkSelfPermission(Manifest.permission.CALL_PHONE);
                    if (permissionResult == PackageManager.PERMISSION_DENIED) {
                        if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                            dialog.setTitle("권한이 필요합니다.")
                                    .setMessage("이 기능을 사용하기 위해서는 단말기의 \"전화걸기\" 권한이 필요합니다. 계속 하시겠습니까?")
                                    .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                // CALL_PHONE 권한을 Android OS에 요청한다.
                                                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1000);
                                            }
                                        }
                                    })
                                    .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(MainActivity.this, "기능을 취소했습니다", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .create()
                                    .show();
                        }
                        // 최초로 권한을 요청할 때
                        else {
                            // CALL_PHONE 권한을 Android OS에 요청한다.
                            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1000);
                        }
                    }
                    // CALL_PHONE의 권한이 있을 때
                    else {
                        // 즉시 실행
                        sharedPref = getSharedPreferences("SettingActivity",MODE_PRIVATE);
                        strLoadData = sharedPref.getString(getString(R.string.savedata_public_key),"01028260640");
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+strLoadData));

                        startActivity(intent);
                    }
                }
                // 마시멜로우 미만의 버전일 때
                else {
                    // 즉시 실행
                    sharedPref = getSharedPreferences("SettingActivity",MODE_PRIVATE);
                    strLoadData = sharedPref.getString(getString(R.string.savedata_public_key),"01028260640");

                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+strLoadData));

                    startActivity(intent);
                }

            }
        });
    //////오은 새로운 push
        button4.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent4=new Intent(getApplicationContext(),SettingActivity.class);
                startActivity(intent4);
            }
        });
    }
    /*
      권한 요청에 대한 응답을 이곳에서 가져온다.

      @param requestCode 요청코드
      @param permissions 사용자가 요청한 권한들
      @param grantResults 권한에 대한 응답들(인덱스별로 매칭)
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 1000) {

            // 요청한 권한을 사용자가 "허용" 했다면...
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                SharedPreferences sharedPref = getSharedPreferences("SettingActivity",MODE_PRIVATE);
                String strLoadData = sharedPref.getString(getString(R.string.savedata_public_key),"01028260640");

                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+strLoadData));

                startActivity(intent);
                // Add Check Permission
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    startActivity(intent);
                }
            } else {
                Toast.makeText(MainActivity.this, "권한요청을 거부했습니다.", Toast.LENGTH_SHORT).show();
            }

        }
    }
}





