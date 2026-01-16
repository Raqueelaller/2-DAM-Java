package ExamenPSPT3Raquel_Aller.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;



public class ServidorGuardarTxt {
	
	private static final int PUERTO = 6002;
	
	 private static List<String> frases;
	 
	 private static int indiceActual = 0;
	 
	 private static final String ARCHIVO = "entrada.txt";
	 
	public static void main(String[] args) throws IOException {
		
		System.out.println("servidor guardando archivo en entrada.txt");
		System.out.println("puerto: "+ PUERTO);
		
		
		try(ServerSocket server = new ServerSocket(PUERTO)) {
			
			while(true) {
				
				Socket cliente = server.accept();
				
				System.out.println("cliente conectado");
				
				 new Thread(() -> {
					try {
						manejarCliente(cliente);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}).start();
				
			}
			
			
		}catch (IOException e) {
            System.err.println("Error servidor: " + e.getMessage());
        }
	}
	private static void manejarCliente(Socket socket) throws IOException {
		
		try(
	            BufferedReader entrada = new BufferedReader(
	                    new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)
	                );
				PrintWriter salida = new PrintWriter(
		                new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8),
		                true );
				
				BufferedWriter bw = new BufferedWriter(
		                new FileWriter(ARCHIVO, true)
		            )
				
				){
				
			salida.println("OK Envia el nombre del fichero o fin para terminar");
			String linea;
			String texto="";
			boolean bandera=true;
			 while ((linea = entrada.readLine()) != null
					 && bandera ==true ) {
				 if(linea.equalsIgnoreCase("fin")) {
						salida.println("ok, texto guardado");
						bandera=false;
					}
				
	                salida.println("OK guardado");
	                
	                linea = "/"+linea;
	                try {

	                	try (BufferedReader br = new BufferedReader(new FileReader(linea))) {
	        	            String frases; 	
	        	            
	        	            while ((frases = br.readLine()) != null) {
	        	            	
	        	            	 bw.write(frases);
	        		                bw.newLine();
	        		                bw.flush();
	        	            }
	                    
	                	}catch (IOException e) {
	        	            System.err.println("Error al leer el archivo: " + e.getMessage());
	        	            e.printStackTrace();
	        	        }finally {
	        	            
	        	            try {
	        	                socket.close();
	        	            } catch (IOException ignored) {}
	        	        }
	                }finally {
        	            
        	            try {
        	                socket.close();
        	            } catch (IOException ignored) {}
				 
			 }
			
			 
			
	           
			
			 }
			
		}catch (IOException e) {
            System.err.println("Error cliente: " + e.getMessage());
        } finally {
            
            try {
                socket.close();
            } catch (IOException ignored) {}
        }
	
		
	}
	

}
