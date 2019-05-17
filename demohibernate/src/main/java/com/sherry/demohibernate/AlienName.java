package com.sherry.demohibernate;

import javax.persistence.Embeddable;

@Embeddable
public class AlienName {

	private String fname, mname, lname;

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getFname()+" "+this.getMname()+" "+this.getLname();
	}
	
}
