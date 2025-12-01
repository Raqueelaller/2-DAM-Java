package net.javaguides.hibernate.util;

import net.javaguides.hibernate.entity.Cancion;
import net.javaguides.hibernate.entity.Cantante;
import net.javaguides.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class App {
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        
        mostrarMenu();
    }
    
    public static void mostrarMenu() throws InputMismatchException {
        int opcion;
        
        do {
            System.out.println("\n MENÚ PRINCIPAL");
            System.out.println("1.  Insertar nuevo cantante");
            System.out.println("2.  Insertar canción");
            System.out.println("3.  Buscar Cantante");
            System.out.println("4.  Listado de cantantes");
            System.out.println("0.  Salir");
            System.out.print("Selecciona una opción: ");
            try {
            opcion = scanner.nextInt();
            }catch(IllegalStateException x) {
            	System.out.println(x.getMessage());
            	opcion=7;
            }catch(NoSuchElementException x) {
            	System.out.println(x.getMessage());
            	opcion=7;
            }
            
            
            scanner.nextLine();     
            
            switch (opcion) {
                case 1:
                    insertarCantante();
                    break;
                case 2:
                    insertarCancion();
                    break;
                case 3:
                    buscarCantante();
                    break;
                case 4:
                    listarCantantes();
                    break;
                case 0:
                    System.out.println(" ¡Hasta pronto!");
                    HibernateUtil.shutdown();
                    break;
                default:
                    System.out.println(" Opción no válida");
            }
        } while (opcion != 0);
        
        scanner.close();
    }
    
    public static void insertarCantante() {
        System.out.println("\n CREAR NUEVO CANTANTE");
        System.out.println("======================");
        
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        String nombre1=nombre;
        System.out.print("Pais: ");
        String pais = scanner.nextLine();
        
        boolean bandera = true;
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        
        
        try {
            List<Cantante> cantantes = session.createQuery("FROM Cantante ORDER BY id", Cantante.class).list();
            
            if (cantantes.isEmpty()) {
            	 try {
                     transaction = session.beginTransaction();
                     
                     Cantante cantante = new Cantante(0, nombre, pais);
                     session.persist(cantante);
                     
                     transaction.commit();
                     System.out.println(" cantante creada exitosamente!");
                     System.out.println("Código asignado: " + cantante.getId());
                     
                 } catch (Exception e) {
                     if (transaction != null) transaction.rollback();
                     System.out.println("Error al crear cantante: " + e.getMessage());
                 }
            } else {
                for (Cantante cantante1 : cantantes) {
                    if(!cantante1.getNombre().equalsIgnoreCase(nombre)) {
                    	bandera=false;
                    	 
                    }
                }if (bandera == false) {
                
                try {
                    transaction = session.beginTransaction();
                    
                    Cantante cantante = new Cantante(0, nombre, pais);
                    session.persist(cantante);
                    
                    transaction.commit();
                    System.out.println(" cantante creada exitosamente!");
                    System.out.println("Código asignado: " + cantante.getId());
                    
                } catch (Exception e) {
                    if (transaction != null) transaction.rollback();
                    System.out.println("Error al crear cantante: " + e.getMessage());
                }
                }else {
                	System.out.println("Ya existe un cantante con ese nombre");
                }
            }
            
        } catch (Exception e) {
            System.out.println( e.getMessage());
        } finally {
            session.close();
        }
        

    }
    public static void insertarCancion() {
        System.out.println("\n CREAR NUEVA CANCION");
        System.out.println("======================");
        
        System.out.print("titulo: ");
        String titulo = scanner.nextLine();
        
        System.out.print("año: ");
        int anyo = scanner.nextInt();
        scanner.nextLine();
        
        System.out.println("nombre del cantante");
        String nombreCantante = scanner.nextLine();
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        Cantante cantanteo;
        try {
            List<Cantante> cantantes = session.createQuery("FROM Cantante ORDER BY id", Cantante.class).list();
            
            if (cantantes.isEmpty()) {
                System.out.println(" No hay cantantes");
            } else {
                for (Cantante cantante : cantantes) {
                    if(cantante.getNombre().equalsIgnoreCase(nombreCantante)) {
                    	cantanteo=cantante;
                    	   try {
                               transaction = session.beginTransaction();
                               
                                Cancion cancion = new Cancion(0, titulo, anyo,cantanteo );
                               session.persist(cancion);
                               
                               transaction.commit();
                               System.out.println(" Cancion creada exitosamente!");
                               System.out.println("Código asignado: " + cancion.getId());
                               
                           } catch (Exception e) {
                               if (transaction != null) transaction.rollback();
                               System.out.println("Error al crear Cancion: " + e.getMessage());
                           }
                    
                    }
                }
            }
            
        } catch (Exception e) {
            System.out.println( e.getMessage());
        } finally {
            session.close();
        }
        
        scanner.nextLine();
    }
    
    public static void buscarCantante() {
    	System.out.println("Dime el nombre del cantante: ");
    	String nombreCantante = scanner.nextLine();
    	 Session session = HibernateUtil.getSessionFactory().openSession();
         Transaction transaction = null;
         try {
             List<Cantante> cantantes = session.createQuery("FROM Cantante ORDER BY id", Cantante.class).list();
             
             if (cantantes.isEmpty()) {
                 System.out.println(" No hay cantantes");
             } else {
                 for (Cantante cantante : cantantes) {
                     if(cantante.getNombre().equalsIgnoreCase(nombreCantante)) {                     
                    	 cantante.toString();
                    	 System.out.println(" ");
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
             
    	
    	
    	
    


    
  
    
    

}