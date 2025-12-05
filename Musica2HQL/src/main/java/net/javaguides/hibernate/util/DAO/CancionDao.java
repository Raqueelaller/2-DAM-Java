package net.javaguides.hibernate.util.DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import net.javaguides.hibernate.entity.Cancion;
import net.javaguides.hibernate.entity.Cantante;
import net.javaguides.hibernate.util.HibernateUtil;

public class CancionDao {
	 public static void buscarCancion(String nombrCancion) {
	    	 Session session = HibernateUtil.getSessionFactory().openSession();
	         Transaction transaction = null;
	         try {
	             List<Cancion> canciones = session.createQuery("FROM Cancion ORDER BY id", Cancion.class).list();
	             
	             if (canciones.isEmpty()) {
	                 System.out.println(" No hay cantantes");
	             } else {
	                 for (Cancion cancion : canciones) {
	                     if(cancion.getTitulo().equalsIgnoreCase(nombrCancion)) {                     
	                    	 	System.out.println("Cancion: "+cancion.getTitulo()+" Cantante: "+cancion.getCantante().getNombre());
	                    	
	                 }
	             }
	         }
	    }finally {
	        session.close();
	    }
	    
	    }
	 
	  public static void buscarCancionPorPalabra(String nombrCancion) {
	    	 Session session = HibernateUtil.getSessionFactory().openSession();
	         Transaction transaction = null;
	         try {
	             List<Cancion> canciones = session.createQuery("FROM Cancion ORDER BY id", Cancion.class).list();
	             
	             if (canciones.isEmpty()) {
	                 System.out.println(" No hay cantantes");
	             } else {
	                 for (Cancion cancion : canciones) {
	                     if(cancion.getTitulo().contains(nombrCancion)) {                     
	                    	 	System.out.println(cancion.toString());
	                    	
	                 }
	             }
	         }
	    }finally {
	        session.close();
	    }
	    
	    }
	  
	  public static void listadoCancionFechas(int fecha1, int fecha2){
	    	 Session session = HibernateUtil.getSessionFactory().openSession();
	         Transaction transaction = null;
	         try {
	             List<Cancion> canciones = session.createQuery("FROM Cancion ORDER BY id", Cancion.class).list();
	             
	             if (canciones.isEmpty()) {
	                 System.out.println(" No hay cantantes");
	             } else {
	                 for (Cancion cancion : canciones) {
	                 if((cancion.getAnyo()>=fecha1 && cancion.getAnyo()<=fecha2)||(cancion.getAnyo()>=fecha2 && cancion.getAnyo()<=fecha1) ) {
	                	 System.out.println(cancion.toString());
	                 }
	             }
		  
	  }


}finally {
    session.close();
}
}
}