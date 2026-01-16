package net.javaguides.hibernate.util;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;


import net.javaguides.hibernate.entity.Refrescos;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    Scanner teclado = new Scanner(System.in);
	    String option,nombre;
	    int existencias;
	    
	    do {
	    	
	    	System.out.println("quieres fin?");
	    	option=teclado.nextLine();
	    	
	    	System.out.println("dime el nombre");
	    	nombre=teclado.nextLine();
	    	System.out.println("dime las existencias");
	    	existencias=teclado.nextInt();
	    	
	    	crearRefresco(nombre,existencias);
	    	
	    }while(!option.equalsIgnoreCase("fin"));
  
	    
	
}

	public static void crearRefresco (String nombre, int existencias) {
	    Session session = HibernateUtil.getSessionFactory().openSession();
	    Transaction transaction = null;
		try {
	        
	       
	        	 try {
	                 transaction = session.beginTransaction();
	                 
	                 Refrescos refresco = new Refrescos(0, nombre, existencias);
	                 session.persist(refresco);
	                 
	                 transaction.commit();
	                 System.out.println(" refresco creado exitosamente!");
	              
	                 
	             } catch (Exception e) {
	                 if (transaction != null) transaction.rollback();
	                 System.out.println("Error al crear refresco: " + e.getMessage());
	             }
	
	    } catch (Exception e) {
	        System.out.println( e.getMessage());
	    } finally {
	        session.close();
	    }
	}
}
