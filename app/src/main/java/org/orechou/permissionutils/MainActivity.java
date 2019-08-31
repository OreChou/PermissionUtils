package org.orechou.permissionutils;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.orechou.permissions.CheckPermission;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnCamera = findViewById(R.id.btn_camera);


        mBtnCamera.setOnClickListener(this::onClick);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_camera:
                openCamera();
                break;
        }
    }

    @CheckPermission(permissions = {Manifest.permission.CAMERA})
    private void openCamera() {
        Toast.makeText(this, "openCamera", Toast.LENGTH_LONG).show();
    }
}
