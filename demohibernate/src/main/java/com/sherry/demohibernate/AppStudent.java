package com.sherry.demohibernate;

import java.util.Collection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class AppStudent
{
    public static void main( String[] args )
    {
    	Student sherry= new Student();
    	
    	Laptop laptop= new Laptop();
    	
    	laptop.setLid(1);
    	laptop.setLname("Dell");
    	laptop.getStudent().add(sherry);
    	
    	sherry.setName("Sherry");
    	sherry.setRollno(101);
    	sherry.setMarks(82);
    	sherry.getLaptop().add(laptop);
    	
    	Configuration con= new Configuration().configure().addAnnotatedClass(Student.class).addAnnotatedClass(Laptop.class);
    	ServiceRegistry reg= new ServiceRegistryBuilder().applySettings(con.getProperties()).buildServiceRegistry();
    	SessionFactory sf= con.buildSessionFactory(reg);
    	Session session= sf.openSession();
    	Transaction tx= session.beginTransaction();
    	
//    	session.save(laptop);
//    	session.save(sherry);
    	Student s= (Student) session.get(Student.class, 101);
    	tx.commit();
    	System.out.println(s);
    	Collection<Laptop> laps= s.getLaptop();
    	for(Laptop l:laps)
    	{
    		System.out.println(l.getLname());
    	}
    }
} 
