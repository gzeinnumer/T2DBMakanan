package com.gzeinnumer.t2dbmakanan.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

//todo 22. pojo
public class ResponseKategoriMakanan{

	@SerializedName("DataKategori")
	private List<DataKategoriItem> dataKategori;

	public void setDataKategori(List<DataKategoriItem> dataKategori){
		this.dataKategori = dataKategori;
	}

	public List<DataKategoriItem> getDataKategori(){
		return dataKategori;
	}
}