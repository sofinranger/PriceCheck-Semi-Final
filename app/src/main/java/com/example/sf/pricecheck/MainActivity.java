package com.example.sf.pricecheck;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import google.zxing.integration.android.IntentIntegrator;
import google.zxing.integration.android.IntentResult;

public class MainActivity extends Activity implements View.OnClickListener {
//    Context context = this;
//    Button PesanOK;

    private Button scanBtn;
    private TextView  contentTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scanBtn = (Button)findViewById(R.id.BtnScan);
        contentTxt = (TextView)findViewById(R.id.TxtKode);

        scanBtn.setOnClickListener(this);
    }


    public void onClick(View v) {
        if(v.getId()==R.id.BtnScan){
            //scan
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            //we have a result
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();

            contentTxt.setText(scanContent);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    //        // =========================MODULE PESAN OK SAJA
//        PesanOK = (Button) findViewById(R.id.BtnCari);
//        PesanOK.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setTitle(Constants.JUDUL_PESAN);
//                builder.setMessage(Constants.PESAN_1);

//                // Membuat tombol negativ
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//                //Membuat tombol positif
//                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // Bila pilih ok, maka muncul toast
//                        Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                builder.show();
//            }
//        });
//        // =========================END MODULE PESAN OK SAJA

}
