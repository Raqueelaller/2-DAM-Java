package ExamenPSPT3Raquel_Aller.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ServidorGuardarTxt {
	
	private static final int PUERTO = 6000;
	
	private static final String ARCHIVO = "frases.txt";
	
	public static void main(String[] args) throws IOException {
		
		System.out.println("servidor guardando archivo en frases.txt");
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
		
		try(BufferedReader entrada = new BufferedReader(
                new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
				
				PrintWriter salida = new PrintWriter(
		                new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8),
		                true );
				
				BufferedWriter bw = new BufferedWriter( new FileWriter(ARCHIVO, true))
				){
			
			salida.println("OK envia texto (fin para terminar)");
			
			String linea;
			boolean bandera =true;
			while((linea=entrada.readLine())!=null && bandera ==true) {
				if(linea.equalsIgnoreCase("fin")) {
					salida.println("ok, texto guardado");
					bandera=false;
				}
				bw.write(linea);
				bw.newLine();
				bw.flush();
				
				salida.println("vale, guardado");
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
