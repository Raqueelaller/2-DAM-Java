package ejemplo1;



public class Plantilla1 {

    public static void main(String[] args) {

        int NUM_HILOS = 10; // Número de hilos
        Thread[] listaHilos = new Thread[NUM_HILOS];

        // 🔹 Crear todos los hilos
        for (int i = 0; i < NUM_HILOS; i++) {
            String nombre = "Usuario " + (i + 1);

            // 🔹 Creamos la instancia de la clase Runnable
            ClasePlantilla1 tarea = new ClasePlantilla1(nombre);

            // 🔹 Creo el hilo usando la tarea
            listaHilos[i] = new Thread(tarea);
        }

        // 🔹 Arrancar todos los hilos
        for (int i = 0; i < NUM_HILOS; i++) {
            listaHilos[i].start();
        }
    }
}

