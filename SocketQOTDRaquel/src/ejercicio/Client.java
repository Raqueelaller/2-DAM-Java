package ejercicio;
import java.io.*;
import java.net.*;

public class Client {
  
	public static void main(String[] args) {
        for (int i = 1; i <= 50; i++) {
            final int id = i;
            new Thread(() -> {
                try (Socket s = new Socket("localhost", 5004);
                     BufferedReader in = new BufferedReader(
                         new InputStreamReader(s.getInputStream()))) {
                    System.out.println("Cliente " + id + ": " + in.readLine());
                } catch (Exception e) {
                    System.err.println("Cliente " + id + " error: " + e.getMessage());
                }
            }).start();
        }
    }
}