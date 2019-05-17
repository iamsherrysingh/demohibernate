package com.sherry.demohibernate;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class AlienApp 
{
    public static void main( String[] args )
    {
    	Alien sherry= new Alien();
    	AlienName sherryName= new AlienName();
    	
    	sherryName.setFname("Sehajpreet");
    	sherryName.setMname("Singh");
    	sherryName.setLname("Gill");
    	
    	sherry.setAid(103);
    	sherry.setAname(sherryName);
    	sherry.setColor("Red");
    	
    	Configuration con= new Configuration().configure().addAnnotatedClass(Alien.class);
    	ServiceRegistry reg= new ServiceRegistryBuilder().applySettings(con.getProperties()).buildServiceRegistry();
    	SessionFactory sf= con.buildSessionFactory(reg);
    	Session session= sf.openSession();
    	Transaction tx= session.beginTransaction();
    	
    	//session.save(sherry);
sherry.setColor("Green");
    	
    	session.update(sherry);
    	Alien fetchedObject= (Alien) session.get(Alien.class, 103);
    	Alien fetchedObject2= (Alien) session.get(Alien.class, 103);   //Query not fired on this one due to first level cache
    	
    	Query q1=session.createQuery("from alien_table where aid=103");
    	q1.setCacheable(true);
    	Alien a1= (Alien) q1.uniqueResult();
    	
    //	session.delete(sherry);
    	
    	
    	tx.commit();
    	session.close();
    	//////////////////////////////// SECOND SESSION ///////////////////////////
    	
    	Session session2= sf.openSession();
    	Transaction tx2= session2.beginTransaction();

    	Alien fetchedObject3= (Alien) session2.get(Alien.class, 103);
    	System.out.println(fetchedObject3);
    	
    	Query q2= session2.createQuery("from alien_table where aid=103");   //Query not fired on this one due to second level cache
    	q2.setCacheable(true);
    	Alien a2= (Alien) q2.uniqueResult();
    	
    	tx2.commit();
    	session2.close();  	
    }
} 
