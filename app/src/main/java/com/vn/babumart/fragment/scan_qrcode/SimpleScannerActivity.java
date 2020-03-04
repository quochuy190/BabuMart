package com.vn.babumart.fragment.scan_qrcode;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.zxing.Result;
import com.vn.babumart.R;
import com.vn.babumart.activity.login.Activity_Webview;
import com.vn.babumart.config.Constants;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 04-September-2019
 * Time: 09:40
 * Version: 1.0
 */
public class SimpleScannerActivity extends AppCompatActivity
        implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    private int mCameraId = -1;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_scan_qrcode);
        ViewGroup contentFrame = findViewById(R.id.content_frame);
        mScannerView = new ZXingScannerView(this);
        contentFrame.addView(mScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera(mCameraId);
        //to set flash
//        mScannerView.setFlash(true);
        //to set autoFocus
//        mScannerView.setAutoFocus(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        try {
           /* Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();*/
            Toast.makeText(this, "" + rawResult.getText() + "", Toast.LENGTH_SHORT)
                    .show();
            Intent intent = new Intent(this, Activity_Webview.class);
            intent.putExtra(Constants.KEY_SEND_WEBVIEW_TITLE, "Quét mã QR");
            intent.putExtra(Constants.KEY_SEND_WEBVIEW_OPTION, rawResult.getText());
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
