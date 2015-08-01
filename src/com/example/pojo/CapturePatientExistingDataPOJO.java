package com.example.pojo;

import java.io.Serializable;
import java.util.Date;

public class CapturePatientExistingDataPOJO implements Serializable {

	String etFirstName, etLastName, etAdd1, etAdd2, etCity,
	etState, etCountry, etUserName;
	int etContact,etZip;
	Date etDOB;
	public String getEtFirstName() {
		return etFirstName;
	}
	public void setEtFirstName(String etFirstName) {
		this.etFirstName = etFirstName;
	}
	public String getEtLastName() {
		return etLastName;
	}
	public void setEtLastName(String etLastName) {
		this.etLastName = etLastName;
	}
	public String getEtAdd1() {
		return etAdd1;
	}
	public void setEtAdd1(String etAdd1) {
		this.etAdd1 = etAdd1;
	}
	public String getEtAdd2() {
		return etAdd2;
	}
	public void setEtAdd2(String etAdd2) {
		this.etAdd2 = etAdd2;
	}
	public String getEtCity() {
		return etCity;
	}
	public void setEtCity(String etCity) {
		this.etCity = etCity;
	}
	public String getEtState() {
		return etState;
	}
	public void setEtState(String etState) {
		this.etState = etState;
	}
	public String getEtCountry() {
		return etCountry;
	}
	public void setEtCountry(String etCountry) {
		this.etCountry = etCountry;
	}
	public String getEtUserName() {
		return etUserName;
	}
	public void setEtUserName(String etUserName) {
		this.etUserName = etUserName;
	}
	public int getEtContact() {
		return etContact;
	}
	public void setEtContact(int etContact) {
		this.etContact = etContact;
	}
	public int getEtZip() {
		return etZip;
	}
	public void setEtZip(int etZip) {
		this.etZip = etZip;
	}
	public Date getEtDOB() {
		return etDOB;
	}
	public void setEtDOB(Date etDOB) {
		this.etDOB = etDOB;
	}
	
	
}
