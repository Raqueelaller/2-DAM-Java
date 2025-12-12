package net.javaguides.hibernate.util;

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
import org.hibernate.query.Query;

import net.javaguides.hibernate.entity.Pedidos;
import net.javaguides.hibernate.entity.Refrescos;

public class App {

    private static final int PUERTO = 5009;
    private static int indiceActual = 0;
    
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
				 
				 boolean bandera = true;
				// CREAR UNA SOLA SESIÓN para todo el ciclo del cliente
			        Session session = HibernateUtil.getSessionFactory().openSession();
				 
					 String mensajeCliente;
					 listarRefrescos(salida,session);
		        	 salida.println("Si quieres pedir, el formato es: Pide (el refresco que quieras) (el nombre del cliente)");
		        	 salida.println("Si quieres recargar, el formato es: Regcarga (id del refresco) (cantidad) (codigo_admin)");
		        	 salida.println("Si quieres terminar, pon 'fin'");
					 while (bandera &&(mensajeCliente = reader.readLine()) != null) {
						 mensajeCliente=mensajeCliente.trim();
						 if(mensajeCliente.equalsIgnoreCase("fin")|| mensajeCliente.equalsIgnoreCase("")) {
							 
							 bandera=false;
						 }else {
							 
							 actualizarMaquina(mensajeCliente,salida,session);
							 listarRefrescos(salida,session);
				        	 salida.println("Si quieres pedir, el formato es: Pide (el refresco que quieras) (el nombre del cliente)");
				        	 salida.println("Si quieres recargar, el formato es: Regcarga (id del refresco) (cantidad) (codigo_admin)");
				        	 salida.println("Si quieres terminar, pon 'fin'");
							 
						 }
					 }
				 
					 if (!socketCliente.isClosed()) {
						    socketCliente.close();
						}
					 
		 } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }
     
		 
     
    
    }
    
     private static void actualizarMaquina(String respuesta, PrintWriter salida, Session session) {
    	 
         Transaction transaction = null;
    	 respuesta=respuesta.trim().toLowerCase();
    	 final String codigo_admin="123";
    	 
         
         try {
        	 List<Refrescos> refrescos = session.createQuery("FROM Refrescos ORDER BY id", Refrescos.class).list();

        	String[] respuestita = respuesta.split(" ");
        	
        	 if(respuesta.isEmpty()) {
        		 salida.println("no has enviado nada");
        	 }else if(respuestita[0].equalsIgnoreCase("pide")) {
        		 boolean bandera=false;
        		 String refresco1 = respuestita[1].toLowerCase();
        		 String nombreCliente = respuestita[2];
        		 
        		 for (Refrescos refresco : refrescos) {
					if(refresco.getNombre().equalsIgnoreCase(refresco1)) {
						transaction=session.beginTransaction();
						bandera = true;
						 // Disminuir existencia
			            refresco.setExistencia(refresco.getExistencia() - 1);
			            
			            // Hacer merge para sincronizar con BD
			            Refrescos refrescoActualizado = (Refrescos) session.merge(refresco);
			    
			            Pedidos pedido = new Pedidos(0,nombreCliente,refresco);
			            session.persist(pedido);
			            transaction.commit();
				        
					}
				}
        		 if(bandera == false) {
        			 salida.println("No existe ese refresco");
        		 }
                    	
                 
             }else if(respuestita[0].equalsIgnoreCase("recarga")) {
            	 if(respuestita[3].equalsIgnoreCase(codigo_admin)) {
            		 int idRefresco = Integer.parseInt(respuestita[1]);
            		 int cantidad = Integer.parseInt(respuestita[2]);
            		 boolean bandera1=false;
            		 if (cantidad <0) {
            			 salida.println("no se puede recargar esa cantidad");
            			 cantidad=0;
            		 }
            		 for (Refrescos refresco : refrescos) {
     					if(refresco.getId() == idRefresco) {
     						transaction=session.beginTransaction();
     						bandera1 = true;
     						 // aumentar existencia
     						int nuevaExistencia = refresco.getExistencia() + cantidad;
     			            refresco.setExistencia(nuevaExistencia);
     			            
     			            // Hacer merge para sincronizar con BD
     			           session.merge(refresco);
     			           transaction.commit();
     				        
     					}
     				} if (bandera1 == false) {
     					salida.println("No se encuentra ese refresco en la máquina");
     				}
            		 
            	 }else {
            		 salida.println("Código de admin equivocado");
            	 }
             }
         
    }finally {
        session.close();
    }
    
    	
    		 
    		 
    	 }
     
     public static void listarRefrescos(PrintWriter salida, Session session) {

         Transaction transaction = null;
         try {
        	 
        	 session.clear();
        	 
             List<Refrescos> refrescos = session.createQuery("FROM Refrescos ORDER BY id", Refrescos.class).list();
             

            	 System.out.println(" ");
                 for (Refrescos refresco : refrescos) {                 
                    	 salida.println(refresco.toString()); 
                    	 
                 
             }
         }finally {
             session.close();
         }
     }
     
    	 
     }
     
     

    
             
    
    	
    	
   
    
  

