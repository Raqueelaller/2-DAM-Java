package contadorConScynchronized;

public class HiloIncrementador implements Runnable {

    // Referencia al contador compartido.
    private final Contador contador;

    // Nombre del hilo (solo para mostrar por pantalla).
    private final String nombre;

    // Número de incrementos que hará este hilo.
    private final int veces;

    // Constructor que inicializa los atributos.
    public HiloIncrementador(String nombre, Contador contador, int veces) {
        this.nombre = nombre;
        this.contador = contador;
        this.veces = veces;
    }

    // Método que se ejecuta cuando arrancamos el hilo.
    @Override
    public void run() {
        // Incrementamos el contador "veces" veces.
        for (int i = 0; i < veces; i++) {

            // Llamamos al método sincronizado del contador.
            contador.incrementarContador();

            // Mensaje para ver qué hilo está trabajando.
            System.out.println(nombre + " incrementa. Número: " + (i + 1));

            // Pausa pequeña solo para ver entremezclado de hilos.
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                // Si algo interrumpe el hilo, lo señalamos como tal.
                Thread.currentThread().interrupt();
            }
        }

        // Mensaje informativo final.
        System.out.println(nombre + " ha terminado.");
    }

}
