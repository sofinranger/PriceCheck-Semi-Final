package com.example.sf.pricecheck;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Context context = this;

    Button PesanOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // =========================MODULE PESAN OK SAJA
        PesanOK = (Button) findViewById(R.id.BtnCari);
        PesanOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(Constants.JUDUL_PESAN);
                builder.setMessage(Constants.PESAN_1);

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
                builder.show();
            }
        });
        // =========================END MODULE PESAN OK SAJA
    }
}
