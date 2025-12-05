package net.javaguides.hibernate.util.DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import net.javaguides.hibernate.entity.Cancion;
import net.javaguides.hibernate.entity.Cantante;
import net.javaguides.hibernate.util.HibernateUtil;

public class CantanteDao {
	
	public static void listarCantantes() {
   	 Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            List<Cantante> cantantes = session.createQuery("FROM Cantante ORDER BY id", Cantante.class).list();
            
            if (cantantes.isEmpty()) {
                System.out.println(" No hay cantantes");
            } else {
           	 System.out.println(" ");
                for (Cantante cantante : cantantes) {                 
                   	 System.out.println(cantante.toString()); 
                   	 
                }
            }
        }finally {
            session.close();
        }
   }
            
	  public static void buscarCantante(String nombreCantante) {
	    	 Session session = HibernateUtil.getSessionFactory().openSession();
	         Transaction transaction = null;
	         try {
	             List<Cantante> cantantes = session.createQuery("FROM Cantante ORDER BY id", Cantante.class).list();
	             
	             if (cantantes.isEmpty()) {
	                 System.out.println(" No hay cantantes");
	             } else {
	                 for (Cantante cantante : cantantes) {
	                     if(cantante.getNombre().equalsIgnoreCase(nombreCantante)) {                     
	                    	 System.out.println("canciones: ");
	                    	for (Cancion cancion : cantante.getCanciones()) {
								System.out.println(cancion.getTitulo());						}
	                    	 
	                     }
	                 }
	             }
	         }finally {
	             session.close();
	         }
	    }
	  

}
