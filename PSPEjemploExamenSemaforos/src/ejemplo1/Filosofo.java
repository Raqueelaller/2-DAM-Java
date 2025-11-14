package ejemplo1;


//Implemento Runnable para usarlo en un Thread
public class Filosofo implements Runnable {

 private final String nombre;
 private final int posicion;

 public Filosofo(String nombre, int posicion) {
     this.nombre = nombre;
     this.posicion = posicion;
 }

 @Override
 public void run() {
     PrincipalFilosofo.comer(nombre, posicion); // Llamo al método comer
 }
}
