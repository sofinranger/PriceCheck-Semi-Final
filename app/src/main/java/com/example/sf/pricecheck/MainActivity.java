package com.example.sf.pricecheck;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import google.zxing.integration.android.IntentIntegrator;
import google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    EditText TxtBarcode;
    SocketClient sckClient = new SocketClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Connect(null);

        TxtBarcode = (EditText) findViewById(R.id.TxtKode);
    }

    //    ======== KODING BARCODE ===============================================================================================
    public void scanBarcode(View v) {
        if (v.getId() == R.id.bScan) {
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

            TxtBarcode.setText(scanContent);
            sckClient.Send(scanContent);

//            if (TxtBarcode != null) {
//                MainActivity Kirim = new MainActivity();
//                Kirim.Send(TxtBarcode);
//            }

        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
//    ======== END KONDING BARCODE ==========================================================================================

    //    ======== KODING SOCKET ===============================================================================================
    public void Connect(View view) {
        Log.d("MAIN", "" + Integer.parseInt(VarNa.PORT_SERVER.toString()));
        sckClient.Connect(VarNa.IP_SERVER.toString(), Integer.parseInt(VarNa.PORT_SERVER.toString()));
    }

    public void Send(View view) {
        sckClient.Send(TxtBarcode.getText().toString());

    }

    class MyServerThread implements Runnable {
        InputStreamReader isr;
        BufferedReader br;

        String strMessage;
        Handler h = new Handler();

        @Override
        public void run() {
            Log.d("MAIN", "Terima Data OK.1");
            try {
                Log.d("MAIN", "Terima Data OK.");
                while (sckClient.sckClient.isConnected()) {
                    Log.d("MAIN", "Terima Data OK.!!!");
                    isr = new InputStreamReader(sckClient.sckClient.getInputStream());
                    br = new BufferedReader(isr);
                    strMessage = br.readLine();
                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), strMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } catch (Exception e) {
                Log.d("MAIN", "Error Terima Message : " + e.toString());
            }
        }
    }
    //    ======== END KONDING SOCKET ==========================================================================================

}
