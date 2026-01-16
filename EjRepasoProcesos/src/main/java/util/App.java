package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;


import net.javaguides.hibernate.entity.Cliente;



public class App {
	 private static final int PUERTO = 1234;

	    
	 public static void main(String[] args) {
    	 try (ServerSocket serverSocket = new ServerSocket()) {
             serverSocket.setReuseAddress(true);
             serverSocket.bind(new InetSocketAddress(PUERTO));
             
             System.out.println(" Servidor en puerto: " + PUERTO);
             System.out.println(" MODO: Cada cliente en HILO SEPARADO");
             System.out.println(" Esperando clientes...\n");
             
             int contadorClientes = 0;
             
             while (true) {
                 Socket socketCliente = serverSocket.accept();
                 contadorClientes++;
                 
                 String ipCliente = socketCliente.getInetAddress().getHostAddress();
                 System.out.println(" CLIENTE #" + contadorClientes + " CONECTADO desde " + ipCliente);
                 
                 Thread hiloCliente = new Thread(new ManejadorCliente(socketCliente, contadorClientes));
                 hiloCliente.setName("Cliente-" + contadorClientes);
                 hiloCliente.start();
                 
                 System.out.println("    Hilo creado: " + hiloCliente.getName());
                 System.out.println("    Hilos activos: " + Thread.activeCount());
                 System.out.println("     Volviendo a esperar más clientes...\n");
             }
             
         } catch (IOException e) {
             System.err.println("Error: " + e.getMessage());
         }
     }
     
     static class ManejadorCliente implements Runnable {
         private Socket socketCliente;
         private int idCliente;
         
         
         public ManejadorCliente(Socket socket, int id) {
             this.socketCliente = socket;
             this.idCliente = id;
         }


		 @Override
		 public void run() {
			// TODO Auto-generated method stub
			 System.out.println("[HILO " + idCliente + "] INICIADO");
			 
			 try(InputStream inputStream = socketCliente.getInputStream();
			 PrintWriter salida = new PrintWriter(socketCliente.getOutputStream(), true);
	         BufferedReader reader = new BufferedReader(
	             new InputStreamReader(inputStream, StandardCharsets.UTF_8)
	             
	         )) {

					String mensajeCliente;
		        	salida.println("Dime tu nombre completo");
					mensajeCliente = reader.readLine(); 
					salida.println("El id es: "+nombreBdd(mensajeCliente,salida));
					
					socketCliente.close();
							 	 
					 } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		 }
     }
     private static int nombreBdd(String respuesta, PrintWriter salida) {
    	 Transaction transaction = null;
    	 String respuesta2=respuesta.trim().replaceAll("\\s+", "").toLowerCase();
    	 Session session = HibernateUtil.getSessionFactory().openSession();
    	 int id = -1;
         
    	 try {
    		 
    		 List<Cliente> clientes = session.createQuery("FROM Cliente ORDER BY id", Cliente.class).list();
    		 
    		 boolean bandera =true;
    		 if(respuesta2.isEmpty()) {
        		 salida.println("no has enviado nada");
        	 }else {
        		 for (Cliente cliente : clientes) {
					if(cliente.getNombreCompleto().toLowerCase().trim().replaceAll("\\s+", "").equalsIgnoreCase(respuesta2)) {
						bandera = false;
					}
				}
        		 
        		 if(bandera == true) {
        			
        			 try {
    	                 transaction = session.beginTransaction();
    	                 
    	                 Cliente cliente = new Cliente(respuesta);
    	                 session.persist(cliente);
    	                 
    	                 transaction.commit();
    	                 
    	                 id=cliente.getId();

        			 }catch (Exception e) {
    	                 if (transaction != null) transaction.rollback();
    	                 System.out.println("Error al crear cliente: " + e.getMessage());
    	             }
    	
        		 }
        		 } 
    		 
    	    }catch (Exception e) {
     	        System.out.println( e.getMessage());
       		 
    	 }finally {
 	        session.close();
 	    }
    	 return id;
     }
        		 
    		
    	 
     }
     
     
     
 

