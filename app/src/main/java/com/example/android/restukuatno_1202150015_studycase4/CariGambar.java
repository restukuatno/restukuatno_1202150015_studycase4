package com.example.android.restukuatno_1202150015_studycase4;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class CariGambar extends AppCompatActivity {

    /*
    * Langkah pertama yaitu :
    *   Penambahan <uses-permission android:name="android.permission.INTERNET"/> dalam tag <manifest> pada AndroidManifest.xml
    */

    //Deklarasi Komponen yang akan digunakan
    //EditText
    private EditText inputsiGambar;
    //ImageView yaitu gambar
    private ImageView inidiagambar;
    //Button
    private Button button_cari;
    //ProgressDialog saat loading
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_gambar);

        // Memanggil variable yang sudah di inisiasi
        inputsiGambar = (EditText) findViewById(R.id.gmbr);
        inidiagambar = (ImageView) findViewById(R.id.hasilcaridariGambar);
        button_cari = (Button) findViewById(R.id.caari);

    }

    public void cari(View view)  {
        loadImageInit();
    }

    private void loadImageInit(){
        String ImgUrl = inputsiGambar.getText().toString();
        //AsyncTask untuk mencari gambar di internet
        new loadImage().execute(ImgUrl);
    }
    private class loadImage extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Membuat Progress Dialog
            mProgressDialog = new ProgressDialog(CariGambar.this);

            // setting pada progress dialog
            mProgressDialog.setMessage("Loading...");

            // menampilkan Progress Dialog
            mProgressDialog.show();
        }

        /*
        * Method yang digunakan saat eksekusi berlangsung 1x
        *   Tahap ini digunakan untuk mencari gambar dari internet berdasarkan alamat gambar yang telah kita set
        */
        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            try {
                // mendownload gambar dari url
                URL url = new URL(params[0]);
                // mengkonversikan gambar ke bitmap
                bitmap = BitmapFactory.decodeStream((InputStream)url.getContent());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            //mengembalikan nilai bitmap
            return bitmap;
        }

        /*
       * Method yang dilakukan setalah proses berhasil di eksekusi
       *   Tahap ini digunakan untuk update pada gambar pada komponen view yang ada sebelumnya (ImageView)
       *
       */
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            // menampung gambar ke imageView dan menampilkannya
            inidiagambar.setImageBitmap(bitmap);

            // menghilangkan Progress Dialog
            mProgressDialog.dismiss();
        }
    }
}


