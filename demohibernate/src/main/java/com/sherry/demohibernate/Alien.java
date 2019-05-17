package com.sherry.demohibernate;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Cacheable											//Second level cache(caching across sessions) enabled  here.
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)    //Second level cache strategy set
@Entity(name="alien_table")
public class Alien {
	
	@Id
	private int aid;
	private AlienName aname;
	@Column(name="alien_color")
	private String color;
	@Transient    //This makes the following variable not appear as a column 
	private String temp_variable;
	private int damage;
	
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	@Override
	public String toString() {
		return "Alien [aid=" + aid + ", aname=" + aname + ", color=" + color + ", temp_variable=" + temp_variable + "]";
	}
	public String getTemp_variable() {
		return temp_variable;
	}
	public void setTemp_variable(String temp_variable) {
		this.temp_variable = temp_variable;
	}

	public AlienName getAname() {
		return aname;
	}
	public void setAname(AlienName aname) {
		this.aname = aname;
	}
	public int getAid() {
		return aid;
	}
	public void setAid(int aid) {
		this.aid = aid;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
}
