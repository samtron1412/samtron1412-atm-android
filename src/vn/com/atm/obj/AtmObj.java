package vn.com.atm.obj;

import android.graphics.Bitmap;

public class AtmObj {

	public String atmCode;
	public String bankCode;
	public String address;
	
	public String lat;
	public String longt;
	public Bitmap bm;
	
	public AtmObj(String atmCode, String bankCode, String address, String lat,
			String longt, Bitmap bm) {
		super();
		this.atmCode = atmCode;
		this.bankCode = bankCode;
		this.address = address;
		this.lat = lat;
		this.longt = longt;
		this.bm = bm;
	}
	public String getAtmCode() {
		return atmCode;
	}
	public void setAtmCode(String atmCode) {
		this.atmCode = atmCode;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLongt() {
		return longt;
	}
	public void setLongt(String longt) {
		this.longt = longt;
	}
	public Bitmap getBm() {
		return bm;
	}
	public void setBm(Bitmap bm) {
		this.bm = bm;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
