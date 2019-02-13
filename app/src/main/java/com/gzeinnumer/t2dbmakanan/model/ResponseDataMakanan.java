package com.gzeinnumer.t2dbmakanan.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

//todo 26.2 Pojokan respon dari
//https://gzeinnumer.000webhostapp.com/db_makananlanjutan/getdatamakanan.php
public class ResponseDataMakanan{

	@SerializedName("DataMakanan")
	private List<DataMakananItem> dataMakanan;

	public void setDataMakanan(List<DataMakananItem> dataMakanan){
		this.dataMakanan = dataMakanan;
	}

	public List<DataMakananItem> getDataMakanan(){
		return dataMakanan;
	}
}