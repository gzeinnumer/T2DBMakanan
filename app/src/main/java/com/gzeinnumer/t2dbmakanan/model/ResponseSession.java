package com.gzeinnumer.t2dbmakanan.model;

import com.google.gson.annotations.SerializedName;

public class ResponseSession{

	@SerializedName("result")
	private String result;

	@SerializedName("kode")
	private int kode;

	public void setResult(String result){
		this.result = result;
	}

	public String getResult(){
		return result;
	}

	public void setKode(int kode){
		this.kode = kode;
	}

	public int getKode(){
		return kode;
	}
}