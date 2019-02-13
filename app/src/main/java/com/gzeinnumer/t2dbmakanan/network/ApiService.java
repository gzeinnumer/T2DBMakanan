package com.gzeinnumer.t2dbmakanan.network;

import com.gzeinnumer.t2dbmakanan.model.ResponseDataMakanan;
import com.gzeinnumer.t2dbmakanan.model.ResponseKategori;
import com.gzeinnumer.t2dbmakanan.model.ResponseRegister;
import com.gzeinnumer.t2dbmakanan.model.ResponseSession;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

//todo 7. buat Inerface ApiService
public interface ApiService {
    //todo 8. isi fungtion
    @FormUrlEncoded
    @POST("registeruser.php")
    Call<ResponseRegister> registerrr(
            @Field("vsnama") String nama,
            @Field("vsalamat") String vsalamat,
            @Field("vsjenkel") String vsjenkel,
            @Field("vsnotelp") String vsnotelp,
            @Field("vsusername") String vsusername,
            @Field("vslevel") String vslevel,
            @Field("vspassword") String vspassword);

    @FormUrlEncoded
    @POST("loginuser.php")
    Call<ResponseRegister> loginnn(
            @Field("edtusername") String edtusername,
            @Field("edtpassword") String edtpassword,
            @Field("vslevel") String vslevel);

    //todo 19. buat robo dari respon kategori makanan
    @GET("kategorimakanan.php")
    Call<ResponseKategori> getDataKategori();

    //todo 20.
    @FormUrlEncoded
    @POST("getdatamakanan.php")
    Call<ResponseDataMakanan> getDataMakanann(
            @Field("vsiduser") String idUser,
            @Field("vsidkastrkategorimakanan") String dataKategori);

    @FormUrlEncoded
    @POST("ceksession.php")
    Call<ResponseSession> cekSession(
            @Field("vsiduser") String idUser);

}
