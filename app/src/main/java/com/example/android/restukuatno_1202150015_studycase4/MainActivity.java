package com.example.android.restukuatno_1202150015_studycase4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void tampilNama(View view) {
        //menggunakan intent untuk pindah ke activity nama ListNamaMahasiswa.class
        Intent ganti = new Intent(this, ListNamaMahasiswa.class);
        //memulai activity ke list nama mahasiswa
        startActivity(ganti);
    }

    public void cariGambar(View view) {
        //menggunakan intent untuk pindah ke activity cari gambar.class
        Intent i = new Intent(this, CariGambar.class);
        //memulai activity cari gambar
        startActivity(i);
    }
}

