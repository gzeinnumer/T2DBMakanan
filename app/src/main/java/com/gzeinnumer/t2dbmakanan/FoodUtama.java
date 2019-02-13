package com.gzeinnumer.t2dbmakanan;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.gzeinnumer.t2dbmakanan.helper.MyConstant;
import com.gzeinnumer.t2dbmakanan.helper.SessionManager;
import com.gzeinnumer.t2dbmakanan.model.DataKategoriItem;
import com.gzeinnumer.t2dbmakanan.model.DataMakananItem;
import com.gzeinnumer.t2dbmakanan.model.ResponseDataMakanan;
import com.gzeinnumer.t2dbmakanan.model.ResponseKategori;
import com.gzeinnumer.t2dbmakanan.model.ResponseRegister;
import com.gzeinnumer.t2dbmakanan.model.ResponseSession;
import com.gzeinnumer.t2dbmakanan.network.ConfigRetrofit;
import com.gzeinnumer.t2dbmakanan.ui.Login;
import com.gzeinnumer.t2dbmakanan.ui.adapter.FoodAdapter;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodUtama extends SessionManager implements SwipeRefreshLayout.OnRefreshListener, FoodAdapter.onItemClick {

    @BindView(R.id.spin_kategori_utama)
    Spinner spinKategoriUtama;
    @BindView(R.id.list_food)
    RecyclerView listFood;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;

    Dialog dialog, dialog2;
    TextInputEditText edtInsertNameFood, edtUpdateId, edtUpdateNameFood;
    Button btnUploadImagesFood, btnUpload, btnCancel, btnUpdate, btnDelete, btnUpdateImages;
    ImageView ivPreviewInsert, ivPreviewUpdate;
    Spinner spinInsert, spinUpdate;
    String idMakanan, idUser, kategori, path, time, namaMakanan;
    Target target;
    Uri filepath;
    Bitmap bitmap;
    //todo 24. buat List
    List<DataKategoriItem> dataKategori = new ArrayList<>();
    List<DataMakananItem> dataMakananItems = new ArrayList<>();
    private String strIdUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_utama);
        ButterKnife.bind(this);


        //todo 45. cek id apakah ada atau ditak di database
        //cekCurrentSession(sessionManager.getIdUser());

        //todo 34. insert lagi
        insertFoodData();
        //todo 23.
        fetchDataKategori(spinKategoriUtama);
        //todo 33. onrefresh
        refresh.setOnRefreshListener(this);
        //todo 37. permition dulu
        Requestpermission();

    }

    //todo 46. isi
    private void cekCurrentSession(final String idUser) {
        showProgressDialog("Cek User");
        ConfigRetrofit.getInstancee().cekSession(idUser).enqueue(new Callback<ResponseSession>() {
            @Override
            public void onResponse(Call<ResponseSession> call, Response<ResponseSession> response) {
                if (response.body().getKode() != 1) {
                    hideProgressDialog();
                    intent(Login.class);
                    FoodUtama.this.finish();
                } else {
                    hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<ResponseSession> call, Throwable t) {

            }
        });
    }

    //todo 38.
    private void Requestpermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MyConstant.STORAGE_PERMISSION_CODE);

    }

    //todo 39. untuk mendapatkan gambar sesuai index pada storage
    private String getPath(Uri filepath) {
        Cursor cursor = getContentResolver().query(filepath, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    //todo 40. ngecek permit data dan data yang diambil
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MyConstant.REQ_FILE_CHOOSE && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filepath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                ivPreviewInsert.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

    //todo 32.onrefresh, refresh dulu baru, insert
    @Override
    public void onRefresh() {
        refresh.setRefreshing(false);
        fetchDataMakanan(kategori);
    }

    private void insertFoodData() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(FoodUtama.this);
                dialog.setContentView(R.layout.item_add_food);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(false);

                edtInsertNameFood = dialog.findViewById(R.id.edt_input_makanan);
                btnUploadImagesFood = dialog.findViewById(R.id.btn_upload_images);
                spinInsert = dialog.findViewById(R.id.spin_kategori);
                ivPreviewInsert = dialog.findViewById(R.id.image_preview_insert);
                btnUpload = dialog.findViewById(R.id.btn_upload);
                btnCancel = dialog.findViewById(R.id.btn_cancel);

                btnUploadImagesFood.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //todo 35. untuk input gambar
                        showfilechooser(MyConstant.REQ_FILE_CHOOSE);
                    }
                });

                //todo 44. isi spinner pada dialog insert
                fetchDataKategori(spinInsert);
                btnUpload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //todo 43. cek nama dan image ketika di upload

                        namaMakanan = edtInsertNameFood.getText().toString().trim();

                        if (TextUtils.isEmpty(namaMakanan)) {
                            edtInsertNameFood.setError(getString(R.string.isEmpty));
                        } else if (ivPreviewInsert.getDrawable() == null) {
                            shortToast("image harus ada");
                        } else {
                            fetchInsertData(kategori);
                            dialog.dismiss();
                        }

                        //todo 41. isi data dari dialog button
                        //fetchInsertData(kategori);
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    private void fetchInsertData(String kategori) {
        //mengambil path dari gmbar yang d i upload
        try {
            path = getPath(filepath);
            strIdUser = sessionManager.getIdUser();
//            MaxSizeImage(strpath);

        } catch (Exception e) {
            longToast("gambar terlalu besar \n silahkan pilih gambar yang lebih kecil");
            e.printStackTrace();
        }
        /**
         * Sets the maximum time to wait in milliseconds between two upload attempts.
         * This is useful because every time an upload fails, the wait time gets multiplied by
         * {@link UploadService#BACKOFF_MULTIPLIER} and it's not convenient that the value grows
         * indefinitely.
         */
        time = getCurentDate();
        try {
            //todo 42. proses pengiriman foto ada disini
            new MultipartUploadRequest(c, MyConstant.UPLOAD_URL)
                    .addFileToUpload(path, "image")
                    .addParameter("vsiduser", strIdUser)
                    .addParameter("vsnamamakanan", namaMakanan)
                    .addParameter("vstimeinsert", time)
                    .addParameter("vskategori", kategori)
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload();

            fetchDataMakanan(kategori);

        } catch (MalformedURLException e) {
            e.printStackTrace();
            longToast(e.getMessage());
        } catch (FileNotFoundException e) {
            longToast(e.getMessage());
            e.printStackTrace();
        }

    }

    //todo 36. untuk memasukan
    private void showfilechooser(int reqFileChoose) {
        Intent intentgalery = new Intent(Intent.ACTION_PICK);
        intentgalery.setType("image/*");
        intentgalery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intentgalery, "select Pictures"), reqFileChoose);

    }

    //todo 25. pengisian
    private void fetchDataKategori(final Spinner spin) {
        showProgressDialog("Mengambil Data");
        ConfigRetrofit.getInstancee().getDataKategori().enqueue(new Callback<ResponseKategori>() {
            @Override
            public void onResponse(Call<ResponseKategori> call, Response<ResponseKategori> response) {
                if (response.isSuccessful()) {
                    dataKategori = response.body().getDataKategori();
                    //deklarasi array
                    String[] id = new String[dataKategori.size()];
                    String[] namaKategori = new String[dataKategori.size()];

                    //pengisian array
                    for (int i = 0; i < dataKategori.size(); i++) {
                        id[i] = dataKategori.get(i).getIdKategori();
                        namaKategori[i] = dataKategori.get(i).getNamaKategori();
                    }

                    //pengisian ke spinner
                    ArrayAdapter adapter = new ArrayAdapter(
                            getApplicationContext(), android.R.layout.simple_spinner_item,
                            namaKategori);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin.setAdapter(adapter);
                    spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            kategori = parent.getItemAtPosition(position).toString();
                            //debug datakategori = respond.body yang ada di atas

                            //todo 30. filter datasesuai kategori
                            fetchDataMakanan(kategori);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<ResponseKategori> call, Throwable t) {

            }
        });
    }

    //todo 31. proses filter
    private void fetchDataMakanan(String kategori) {
        String idUser = sessionManager.getIdUser();
        ConfigRetrofit.getInstancee().getDataMakanann(idUser, kategori).enqueue(new Callback<ResponseDataMakanan>() {
            @Override
            public void onResponse(Call<ResponseDataMakanan> call, Response<ResponseDataMakanan> response) {
                if (response.isSuccessful()) {
                    dataMakananItems = response.body().getDataMakanan();
                    String[] id = new String[dataMakananItems.size()];
                    String[] name = new String[dataMakananItems.size()];
                    String[] time = new String[dataMakananItems.size()];
                    String[] image = new String[dataMakananItems.size()];

                    for (int i = 0; i < dataMakananItems.size(); i++) {
                        id[i] = dataMakananItems.get(i).getIdMakanan();
                        name[i] = dataMakananItems.get(i).getMakanan();
                        time[i] = dataMakananItems.get(i).getInsertTime();
                        image[i] = dataMakananItems.get(i).getFotoMakanan();
                        strIdUser = id[i];
                    }
                    FoodAdapter adapter = new FoodAdapter(FoodUtama.this, dataMakananItems);
                    listFood.setLayoutManager(new LinearLayoutManager(FoodUtama.this));
                    listFood.setHasFixedSize(true);
                    listFood.setAdapter(adapter);

                    //todo 53. setonclik yang dampak nya da pada todo 52
                    adapter.setOnClickListener(FoodUtama.this);
                }
            }

            @Override
            public void onFailure(Call<ResponseDataMakanan> call, Throwable t) {

            }
        });
    }


    @Override
    public void onItemClick(int position) {
        dialog2 = new Dialog(FoodUtama.this);
        dialog2.setContentView(R.layout.item_update_food);
        dialog2.setTitle(getString(R.string.data_food));
        dialog2.setCancelable(true);
        dialog2.setCanceledOnTouchOutside(false);
        dialog2.show();

        edtUpdateId = dialog2.findViewById(R.id.edt_id_makanan);
        edtUpdateNameFood = dialog2.findViewById(R.id.edt_input_makanan_update);
        btnUpdateImages = dialog2.findViewById(R.id.btn_update_images);
        spinUpdate = dialog2.findViewById(R.id.spin_kategori_update);
        ivPreviewInsert = dialog2.findViewById(R.id.image_preview_update);
        btnUpdate = dialog2.findViewById(R.id.btn_update);
        btnDelete = dialog2.findViewById(R.id.btn_delete);
        spinUpdate = dialog2.findViewById(R.id.spin_kategori_update);

        //todo 47.
        target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                ivPreviewInsert.setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

        btnUpdateImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 48.
                showfilechooser(MyConstant.REQ_FILE_CHOOSE);
            }
        });

        //todo 49.
        Picasso.with(c)
                .load(MyConstant.IMAGE_URL + dataMakananItems.get(position).getFotoMakanan())
                .into(target);
        //  imgpreview.setImageBitmap();

        fetchDataKategori(spinUpdate);
        //isidata
        edtUpdateNameFood.setText(dataMakananItems.get(position).getMakanan());
        edtUpdateId.setText(dataMakananItems.get(position).getIdMakanan());
        btnUpdateImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showfilechooser(MyConstant.REQ_FILE_CHOOSE);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idMakanan = edtUpdateId.getText().toString().trim();
                fetchUpdate();
            }
        });
    }

    //todo 50.
    private void fetchUpdate() {
        try {
            path = getPath(filepath);
            strIdUser = sessionManager.getIdUser();
        } catch (Exception e) {
//                    myToast("gambar terlalu besar \n silahkan pilih gambar yang lebih kecil");
            e.printStackTrace();
        }

        namaMakanan = edtUpdateNameFood.getText().toString();
        idMakanan = edtUpdateId.getText().toString();
        if (TextUtils.isEmpty(namaMakanan)) {
            edtUpdateNameFood.setError("nama makanan tidak boleh kosong");
            edtUpdateNameFood.requestFocus();
        } else if (ivPreviewInsert.getDrawable() == null) {
            longToast("gambar harus dipilih");
        } else if (path == null) {
            longToast("gambar harus diganti");
        } else {
            /**
             * Sets the maximum time to wait in milliseconds between two upload attempts.
             * This is useful because every time an upload fails, the wait time gets multiplied by
             * {@link UploadService#BACKOFF_MULTIPLIER} and it's not convenient that the value grows
             * indefinitely.
             */

            try {
                new MultipartUploadRequest(c, MyConstant.UPLOAD_UPDATE_URL)
                        .addFileToUpload(path, "image")
                        .addParameter("vsidmakanan", idMakanan)
                        .addParameter("vsnamamakanan", namaMakanan)
                        .addParameter("vsidkategori", kategori)
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(2)
                        .startUpload();

            } catch (MalformedURLException e) {
                e.printStackTrace();
                longToast(e.getMessage());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                longToast(e.getMessage());
            }

            dialog2.dismiss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_food_utama, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
