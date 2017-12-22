package com.example.sf.pricecheck;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import google.zxing.integration.android.IntentIntegrator;
import google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    EditText TBarcode;
    EditText TNama;
    SocketClient sckClient = new SocketClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Connect(null);

        TBarcode = (EditText) findViewById(R.id.TxtKode);
        TNama = (EditText) findViewById(R.id.TxtNama);

        Thread myThread = new Thread(new MyServerThread());
        myThread.start();


        TBarcode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN)
                {
                    //Toast.makeText(getApplicationContext(),TBarcode.getText().toString(),Toast.LENGTH_SHORT).show();
                if (TBarcode.getText().toString() != null) {
                    sckClient.Send(TBarcode.getText().toString());
                }
                    return true;
                }
                return false;
            }
        });

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

            TBarcode.setText(scanContent);
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
        Log.d("MAIN", "" + Integer.parseInt(VarNa.PORT_CONNECT.toString()));
        sckClient.Connect(VarNa.IP_SERVER.toString(), Integer.parseInt(VarNa.PORT_CONNECT.toString()));
    }

    public void Send(View view) {
        sckClient.Send(TBarcode.getText().toString());

    }

    class MyServerThread implements Runnable {
        Socket s;
        ServerSocket ss;
        InputStreamReader isr;
        BufferedReader bufferedReader;
        Handler h = new Handler();

        String message;
        int X = 0;

        @Override
        public void run() {
            try {
                ss = new ServerSocket(Integer.parseInt(VarNa.PORT_SEND.toString()));
                while (true){
                    s = ss.accept();
                    while (!s.isConnected()){
                        String strLoop;
                        X = X +1;
                        strLoop = String.valueOf(X);
                        Log.d("RECEIVE", "LOOP " + strLoop);
                    }
                    isr = new InputStreamReader(s.getInputStream());
                    bufferedReader = new BufferedReader(isr);
                    message = bufferedReader.readLine();

                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            //Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                            TNama.setText(message);
                        }
                    });
                }
            }catch (Exception e){

            }
        }
    }
    //    ======== END KONDING SOCKET ==========================================================================================

}
