package com.sherry.demohibernate;

import java.util.*;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class AlienApp2
{
    public static void main( String[] args )
    {
    	Alien sherry= new Alien();
    	AlienName sherryName= new AlienName();
    	
    	sherryName.setFname("Sehajpreet");
    	sherryName.setMname("Singh");
    	sherryName.setLname("Gill");
    	
    	sherry.setAid(200);
    	sherry.setAname(sherryName);
    	sherry.setColor("Red");
    	
    	Configuration con= new Configuration().configure().addAnnotatedClass(Alien.class);
    	ServiceRegistry reg= new ServiceRegistryBuilder().applySettings(con.getProperties()).buildServiceRegistry();
    	SessionFactory sf= con.buildSessionFactory(reg);
    	Session session= sf.openSession();
    	Transaction tx= session.beginTransaction();
    	session.delete(sherry);
    	session.save(sherry);				//save
    	sherry.setColor("Changed Color");   //Object in Persisted State. Even after you save the object, if you make any changes to the object, they are reflected in the DB.
    										//To 'detach' the object from the DB, either close session or use tx.commit() + session.evict(). Or assign null to  the object
    										//More info at https://youtu.be/ZLeTFBvegfY
    	tx.commit();
    	session.evict(sherry);
    	sherry.setColor("Changed again");  //sherry object is in Detached state
    	tx=session.beginTransaction();
    	
    	
    	/////////////////// HQL QUERIES BELOW /////////////////
    	
    	
//    	Random r= new Random();            //Code to create 99 records in the table 
//    	for(int i=1;i<100;i++)
//    	{
//    		Alien alien= new Alien();
//    		alien.setAid(100+i);
//    		AlienName aName=new AlienName();
//    		aName.setFname("Alien"+i);
//    		alien.setAname(aName);
//    		alien.setDamage(r.nextInt(100));
//    		session.save(alien);
//    	}
    	
    	Query q1= session.createQuery("from alien_table where damage>=97");
    	List<Alien> results=q1.list();
    	
    	for(Alien a:results)
    	{
    		System.out.println(a.getAid()+" : "+a.getAname().getFname()+ " : "+ a.getDamage());
    	}
    	
    	
    	int aidParameter=105;
    	Query q2= session.createQuery("from alien_table where aid=:b");    //value after ':' can be replaced later
    	q2.setParameter("b", aidParameter);     //adding values later
    	Alien a2=(Alien) q2.uniqueResult();
    	System.out.println("UNIQUE RESULT :====>  "+a2.getAid()+" : "+a2.getAname().getFname()+ " : "+ a2.getDamage());
    	
    	// The moment you add 'select' in HQL query, output returned would be a Object[]
    	// In case multiple records are fetched, output returned would be a ListObject[]>
    	Query q3= session.createQuery("select aid,aname,damage from alien_table where aid=112");
    	Object[] a3= (Object[]) q3.uniqueResult();
    	System.out.println("UNIQUE RESULT :====>  "+a3[0]+" : "+a3[1]+" : "+a3[2]);

    	
    	Query q4= session.createQuery("select aid,aname,damage from alien_table at where at.aid<105");   //alias,aggregate functions and everything else from SQL is available in HQL.
    	List<Object[]> a4= (ArrayList<Object[]>)q4.list();
    	
    	for(Object[] o:a4)
    	{
    		System.out.println("UNIQUE RESULT :====>  "+o[0]+" : "+o[1]+" : "+o[2]);
    	}
    	
    	////////////////  NATIVE QUERIES(SQL  QUERIES)  BELOW  ///////////////
    	
    	SQLQuery q5= session.createSQLQuery("select * from alien_table where damage>96");
    	q5.addEntity(Alien.class);
    	List<Alien> aliens2= q5.list();
    	
    	for(Alien a: aliens2)
    	{
    		//Alien ax=(Alien) a;
    		System.out.println(a.getAid()+" : "+a.getAname().getFname()+ " : "+ a.getDamage());
    	}
    	

    	SQLQuery q6= session.createSQLQuery("select aid,fname from alien_table where damage>96");
    	q6.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
    	List aliens3= q6.list();
    	
    	for(Object a: aliens3)
    	{
    		Map m= (Map)a;
    		System.out.println("Mapped Values:: "+m.get("aid")+ " " +m.get("fname"));
    	}
    	tx.commit();
    	session.close();
 	
    }
} 
