package ExamenPSPT3Raquel_Aller.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class ClienteEnviarTexto {

	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		
		String host = "localhost";
		int puerto = 6002;
		
		try(Socket socket = new Socket (host, puerto);
				
				BufferedReader teclado = new BufferedReader(
						new InputStreamReader (System.in)
				);
	            BufferedReader entrada = new BufferedReader(
	                    new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)
	                );
				PrintWriter salida = new PrintWriter(
						new OutputStreamWriter(socket.getOutputStream(),StandardCharsets.UTF_8 ),
						true)
				
					){
			
			System.out.println(entrada.readLine());
			
			String texto;
			boolean bandera =true;
			while(true && bandera ==true) {
				System.out.println("Escribe el nombre del fichero");
				texto = teclado.readLine();
				
				salida.println(texto);
				
				String respuesta = entrada.readLine();
				System.out.println("servidor "+respuesta);
				
				if(texto.equalsIgnoreCase("fin")) {
					bandera=false;
				}
				
			}
			
		}

	}

}
