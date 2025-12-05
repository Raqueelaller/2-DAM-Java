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
import net.javaguides.hibernate.util.DAO.CancionDao;
import net.javaguides.hibernate.util.DAO.CantanteDao;

public class App {
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        
        mostrarMenu();
    }
    
    public static void mostrarMenu() throws InputMismatchException {
        int opcion;
        
        do {
            System.out.println("\n MENÚ PRINCIPAL");
            System.out.println("1.  canciones de un cantante");
            System.out.println("2.  Buscar canciones titulo");
            System.out.println("3.  Contador de canciones por palabra");
            System.out.println("4.  Listado de cantantes");
            System.out.println("5.  Listado de canciones entre dos fechas");
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
                	System.out.println("dime el nombre del cantante a buscar");
                	String nombreCantante = scanner.nextLine();
                	CantanteDao.buscarCantante(null);                  
                	break;
                case 2:
                	System.out.println("dime el nombre de la canción a buscar");
                	String nombreCancion = scanner.nextLine();
                	CancionDao.buscarCancion(nombreCancion);
                	break;
                case 3:
                	System.out.println("dime la palabra que esté en la canción a buscar");
                	 nombreCancion = scanner.nextLine();
                	CancionDao.buscarCancion(nombreCancion);
                    break;
                case 4:
                    CantanteDao.listarCantantes();
                    break;
                case 5:
                	System.out.println("dime la primera fecha");
                	int fecha1=scanner.nextInt();
                	System.out.println("dime la segunda fecha");
                	int fecha2=scanner.nextInt();
                	CancionDao.listadoCancionFechas(fecha1, fecha2);
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
     
    
             
    
    	
    	
   
    
  

}