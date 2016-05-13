package com.example.uriel.ordertracker.App.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import com.example.uriel.ordertracker.App.Model.Helpers;
import com.example.uriel.ordertracker.App.Model.SessionInformation;
import com.example.uriel.ordertracker.App.Services.Impl.OrderService;
import com.example.uriel.ordertracker.App.Services.Interface.IOrderService;
import com.example.uriel.ordertracker.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class QRReaderActivity extends AppCompatActivity implements SweetAlertDialog.OnSweetClickListener {

    boolean qrCodeSent;
    private IOrderService orderService;
    private QRReaderActivity context;
    private HashMap<String, String> clientDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrreader);

        orderService = new OrderService();
        context = this;
        qrCodeSent = false;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Lector QR");

        clientDetails = (HashMap<String, String>) getIntent().getExtras().getSerializable("client");
        boolean readOnly = false;

        final SurfaceView cameraView = (SurfaceView) findViewById(R.id.camera_view);
        final TextView barcodeInfo = (TextView) findViewById(R.id.code_info);
        final TextView textView = (TextView) findViewById(R.id.textView);

        BarcodeDetector barcodeDetector =
                new BarcodeDetector.Builder(this)
                        .setBarcodeFormats(Barcode.QR_CODE)
                        .build();

        final CameraSource cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640, 480)
                .build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                try {
                    //checkear si tengo permisos para la camara
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    cameraSource.start(cameraView.getHolder());
                } catch (IOException ie) {
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if ( qrCodeSent == false && barcodes.size() != 0) {

                    findViewById(R.id.qr_request_screen).setVisibility( View.VISIBLE );
                    findViewById(R.id.qr_capture_screen).setVisibility( View.GONE );

                    Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                    vibrator.vibrate(100);

                    final String value = barcodes.valueAt(0).displayValue;
                    qrCodeSent = true;

                    textView.post(new Runnable() {
                        public void run() {
                            try {
                                orderService.sendQR(SessionInformation.getSessionUsername(), SessionInformation.getSessionToken(), clientDetails.get("visit_id"), value, context);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }

    public void validQR(){
        Intent intent = new Intent(context, OrderActivity.class);
        intent.putExtra("readOnly", false);
        intent.putExtra("client", clientDetails);
        startActivity(intent);
    }

    public void handleUnexpectedError(String error){
        SweetAlertDialog dialog = Helpers.getErrorDialog(this, "Error en la lectura del código QR", error);
        dialog.setConfirmClickListener(this);
        dialog.show();
    }


    @Override
    public void onClick(SweetAlertDialog sweetAlertDialog) {
        this.finish();
    }
}
