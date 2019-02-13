package com.gzeinnumer.t2dbmakanan.ui;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import com.gzeinnumer.t2dbmakanan.FoodUtama;
import com.gzeinnumer.t2dbmakanan.R;
import com.gzeinnumer.t2dbmakanan.helper.SessionManager;
import com.gzeinnumer.t2dbmakanan.model.ResponseRegister;
import com.gzeinnumer.t2dbmakanan.network.ConfigRetrofit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends SessionManager {

    @BindView(R.id.login_username)
    TextInputEditText edtLoginUsername;
    @BindView(R.id.login_password)
    TextInputEditText edtLoginPassword;
    @BindView(R.id.rg_user_admin_sign)
    RadioButton rgUserAdminSign;
    @BindView(R.id.rg_user_biasa_sign)
    RadioButton rgUserBiasaSign;
    @BindView(R.id.sign_in)
    Button signIn;
    @BindView(R.id.register)
    TextView register;

    String levelUser, userName, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        ButterKnife.bind(this);

        setUpUser();
    }

    private void setUpUser() {
        if (rgUserAdminSign.isChecked()) levelUser = "Admin";
        else levelUser = "User Biasa";
    }

    @OnClick({R.id.rg_user_admin_sign, R.id.rg_user_biasa_sign, R.id.sign_in, R.id.register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rg_user_admin_sign:
                levelUser = "Admin";
                break;
            case R.id.rg_user_biasa_sign:
                levelUser = "User Biasa";
                break;
            case R.id.sign_in:
                //todo 15. buar method
                login();
                break;
            case R.id.register:
                intent(Register.class);
                break;
        }
    }

    //todo 16. isi login method
    private void login() {
        userName = edtLoginUsername.getText().toString().trim();
        password = edtLoginPassword.getText().toString().trim();

        if(TextUtils.isEmpty(userName)){
            edtLoginUsername.requestFocus();
            edtLoginUsername.setError(getString(R.string.isEmpty));
        } else if(TextUtils.isEmpty(password)){
            edtLoginPassword.requestFocus();
            edtLoginPassword.setError(getString(R.string.isEmpty));
        } else {
            //todo 17. proses login
            fetchLogin();
        }
    }

    //todo 18.isi proses login
    private void fetchLogin() {
        showProgressDialog("Loading nih");

        ConfigRetrofit.getInstancee().loginnn(
                userName,password,levelUser
        ).enqueue(new Callback<ResponseRegister>() {
            @Override
            public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                if(response.isSuccessful()){
                    String result =response.body().getResult();
                    String message = response.body().getMsg();
                    String idUser = response.body().getUser().getIdUser();
                    if (result.equals("1")){
                        //Log.d("Login", message);
                        //Toast.makeText(sessionManager, "Login nih!!", Toast.LENGTH_SHORT).show();
                        sessionManager.createSession(userName);
                        sessionManager.setIdUser(idUser);
                        hideProgressDialog();
                        intent(FoodUtama.class);
                        finish();
                    } else {
                        longToast(message);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseRegister> call, Throwable t) {
                Toast.makeText(Login.this, "Password Salah!!!", Toast.LENGTH_SHORT).show();
                intent(Login.class);
            }
        });
    }


}

