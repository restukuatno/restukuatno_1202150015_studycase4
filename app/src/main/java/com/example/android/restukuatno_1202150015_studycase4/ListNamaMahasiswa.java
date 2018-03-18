package com.example.android.restukuatno_1202150015_studycase4;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class ListNamaMahasiswa extends AppCompatActivity {

    //Mendeklarasikan Komponen2 yang akan digunakan seperti :

    //ListView
        private ListView rListView;
        // ProgressBar
        private ProgressBar rProgressBar;
        private String[] rMahasiswa = {
                "Aries", "Nirvana", "Cupid", "Kaguya",
                "Sophie", "Maia", "Gatotkaca", "Pocahontas",
                "Layla", "Charlote", "Bari"
        };
        //Listview
        private AddItemToListView rAddItemToListView;
        //Button
        private Button rMulai;

    /*
* Method ini untuk melakukan proses penyimpanan sesuatu pada package
*/
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_list_nama_mahasiswa);

            //melakukan inisiasi untuk private

            rProgressBar = (ProgressBar) findViewById(R.id.progressbar);
            rListView = (ListView) findViewById(R.id.listView);
            rMulai = (Button) findViewById(R.id.mulaiasyn);

            //setup adapternya
            rListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>()));


            //aksi fungsi tombol saat di klik pada asynctask
            rMulai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //prses adapater dengan asyncTask
                    rAddItemToListView = new AddItemToListView();
                    rAddItemToListView.execute();
                }
            });
        }

        //class untuk melakukan prosess asynctask
        public class AddItemToListView extends AsyncTask<Void, String, Void> {

            private ArrayAdapter<String> mAdapter;
            private int counter = 1;
            ProgressDialog mProgressDialog = new ProgressDialog(ListNamaMahasiswa.this);

            /*
        * Method yang dilakukan sebelum aksi dilakukan
        *   Pada aplikasiyang kita buat, tahap ini bertujuan agar dapat menampilkan progress bar dan propertynya
        */
            @Override
            protected void onPreExecute() {
                mAdapter = (ArrayAdapter<String>) rListView.getAdapter();

                //untuk melakukan progress dialog
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setTitle("Loading Data");
                mProgressDialog.setMessage("Tunggu dulu....");
                mProgressDialog.setCancelable(false);
                mProgressDialog.setProgress(0);

                //jika mengklik cancel button
                mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel Process", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        rAddItemToListView.cancel(true);
                        rProgressBar.setVisibility(View.VISIBLE);
                        dialog.dismiss();
                    }
                });
                mProgressDialog.show();
            }

            /*
        * Method yang digunakan saat eksekusi berlangsung
        *   Tahap ini digunakan untuk mengembalikan data input agar menjadi data output (String[])
        */
            @Override
            protected Void doInBackground(Void... params) {
                for (String item : rMahasiswa) {
                    publishProgress(item);
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //Cek Apakah ada sesuatu didalam isCancelled
                    if (isCancelled()) {
                        rAddItemToListView.cancel(true);
                        Intent cancel = new Intent(getApplicationContext(), ListNamaMahasiswa.class);
                        startActivity(cancel);
                    }
                }
                return null;
            }

            /*
        * Method ini terjadi jika proses berlangsung sesuai request yang dijalankan
        *   Tahap ini digunakan untuk melakukan update pada UI nya pada progress bar
        */
            @Override
            protected void onProgressUpdate(String... values) {
                mAdapter.add(values[0]);

                Integer current_status = (int) ((counter / (float) rMahasiswa.length) * 100);
                rProgressBar.setProgress(current_status);

                //set progress saat dijalankan untuk loading horizontal
                mProgressDialog.setProgress(current_status);

                //set pesan yang tidak bekerja ketika menggunakan loading horizontal
                mProgressDialog.setMessage(String.valueOf(current_status + "%"));
                counter++;
            }

            /*
        * Method ini dilakukan setalah proses eksekusi berhasil
        *   Tahap ini digunakan untuk mengisi data input ke dalam ListView pada Adapter
        */
            @Override
            protected void onPostExecute(Void aVoid) {

                rProgressBar.setVisibility(View.GONE);

                //menghilangkan progress dialog
                mProgressDialog.dismiss();
                rListView.setVisibility(View.VISIBLE);
            }
        }
    }
