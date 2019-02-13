package com.gzeinnumer.t2dbmakanan.ui;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.gzeinnumer.t2dbmakanan.R;
import com.gzeinnumer.t2dbmakanan.helper.MyFunction;
import com.gzeinnumer.t2dbmakanan.model.ResponseRegister;
import com.gzeinnumer.t2dbmakanan.network.ConfigRetrofit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends MyFunction {

    @BindView(R.id.regis_name)
    TextInputEditText edtRegisName;
    @BindView(R.id.regis_alamat)
    TextInputEditText edtRegisAlamat;
    @BindView(R.id.regis_no_tlp)
    TextInputEditText edtRegisNoTlp;
    @BindView(R.id.spin_kelamin)
    Spinner spinKelamin;
    @BindView(R.id.regis_username)
    TextInputEditText edtRegisUsername;
    @BindView(R.id.regis_pass)
    TextInputEditText edtRegisPass;
    @BindView(R.id.regis_confir_pass)
    TextInputEditText edtRegisConfirPass;
    @BindView(R.id.rg_user_admin)
    RadioButton rgUserAdmin;
    @BindView(R.id.rg_user_biasa)
    RadioButton rgUserBiasa;
    @BindView(R.id.sign_up)
    Button signUp;
    @BindView(R.id.login)
    TextView login;

    String jenisKelamin[] = {"Laki - Laki", "Perempuan"};
    String name, alamat, noHp, username, password, conPassword, jenKel, levelUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        setUpUser();
        //todo 10. buat method pengisian Spinner
        setUpJenKel();
    }

    private void setUpUser() {
        if (rgUserAdmin.isChecked()) levelUser = "Admin";
        else levelUser = "User Biasa";
    }

    //todo 11. isi function
    private void setUpJenKel() {
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, jenisKelamin);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinKelamin.setAdapter(adapter);
        spinKelamin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //set JenKel
                jenKel = jenisKelamin[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                /** nothing to do */
            }
        });
    }

    @OnClick({R.id.rg_user_admin, R.id.rg_user_biasa, R.id.sign_up, R.id.login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rg_user_admin:
                levelUser = "Admin";
                break;
            case R.id.rg_user_biasa:
                levelUser = "User Biasa";
                break;
            case R.id.sign_up:
                //todo 12. buat function untuk register
                register();
                break;
            case R.id.login:
                intent(Login.class);
                break;
        }
    }

    //todo 13.isi func register
    private void register() {
        /**
         * seleksi inputan user , patikan tidak ada yang kosong
         * pastikan pass dan c-pass sama
         */

        name= edtRegisName.getText().toString().trim();
        alamat= edtRegisAlamat.getText().toString().trim();
        noHp= edtRegisNoTlp.getText().toString().trim();
        username= edtRegisUsername.getText().toString().trim();
        password= edtRegisPass.getText().toString().trim();
        conPassword= edtRegisConfirPass.getText().toString().trim();
        //jenKel udah di set di atas, pada line 73
        //levelUser sudah diset diatas di line 62

        //pengecekan kondisi edittext
        if (TextUtils.isEmpty(name)) {
            edtRegisName.requestFocus();
            edtRegisUsername.setError(getString(R.string.isEmpty));
        } else if (TextUtils.isEmpty(alamat)) {
            edtRegisAlamat.requestFocus();
            edtRegisUsername.setError(getString(R.string.isEmpty));
        } else if (TextUtils.isEmpty(noHp)) {
            edtRegisNoTlp.requestFocus();
            edtRegisNoTlp.setError(getString(R.string.isEmpty));
        } else if (TextUtils.isEmpty(username)) {
            edtRegisUsername.requestFocus();
            edtRegisUsername.setError(getString(R.string.isEmpty));
        } else if (TextUtils.isEmpty(password)) {
            edtRegisPass.requestFocus();
            edtRegisPass.setError(getString(R.string.isEmpty));
        } else if (password.length() < 6) {
            edtRegisPass.setError(getString(R.string.minimum));
        } else if (TextUtils.isEmpty(conPassword)) {
            edtRegisConfirPass.requestFocus();
            edtRegisConfirPass.setError(getString(R.string.isEmpty));
        } else if (!password.equals(conPassword)) {
            edtRegisPass.setError(getString(R.string.minimum));
        } else {
            fetchRegister();
        }
    }

    //todo 14. isi proses register
    private void fetchRegister() {
        showProgressDialog("Loading nih");
        ConfigRetrofit.getInstancee().registerrr(
                name,
                alamat,
                jenKel,
                noHp,
                username,
                levelUser,
                password)
                .enqueue(new Callback<ResponseRegister>() {
                    @Override
                    public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                        if(response.isSuccessful()){
                            String result = response.body().getResult();
                            String message = response.body().getMsg();
                            if (result.equals("1")){
                                Log.d("TAG",message);
                                Toast.makeText(Register.this, "Insert sukses!!, silahkan login", Toast.LENGTH_SHORT).show();
                                hideProgressDialog();
                                finish();
                                //setelah regitser selesai, lansung ke halaman login
                                intent(Login.class);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseRegister> call, Throwable t) {

                    }
                });

    }


}
