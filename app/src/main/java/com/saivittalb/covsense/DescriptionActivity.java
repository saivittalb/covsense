package com.saivittalb.covsense;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

public class DescriptionActivity extends AppCompatActivity {
    private Button btnRegister;
    private int clickCount = 0;
    private Activity activity;
    private Context context;
    private static final String TAG = "DescriptionActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        context = this;
        btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(v -> {
            clickCount++;
            if(clickCount == 1) {
                Utils.checkPermission(activity);
            }
            Toast.makeText(getApplicationContext(), "For optimal results, grant all permissions.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(DescriptionActivity.this, PhoneRegisterActivity.class));

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activity = null;
        context = null;
    }

    // Function to initiate after permissions are given by user
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {

        if (requestCode == Utils.MULTIPLE_PERMISSIONS) {
            if (grantResults.length > 0) {
                Log.d(TAG, "3");

                boolean internetPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                Log.d(TAG, String.valueOf(internetPermission));
                boolean accessFineLocationPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                Log.d(TAG, String.valueOf(accessFineLocationPermission));
                boolean bluetoothPermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                boolean accessBackgroundLocationPermission = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                if (internetPermission && accessFineLocationPermission && bluetoothPermission && accessBackgroundLocationPermission) {
                    Log.d(TAG, "Starting intent");
                    startActivity(new Intent(DescriptionActivity.this, PhoneRegisterActivity.class));
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(
                            new String[]{Manifest.permission
                                    .INTERNET, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.BLUETOOTH, Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                            Utils.MULTIPLE_PERMISSIONS);
                }
            }
        }
    }
}
