package ejemplo1;

public class CocheComplicado implements Runnable {

    private String nombre;

    public CocheComplicado(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {

        int intentos = 0;
        boolean entro = false; // controla si logró entrar

        // Intentar hasta 3 veces
        while (intentos < 3 && !entro) {

            synchronized (ParkingComplicado.class) {
                if (ParkingComplicado.plazasLibres > 0) {
                    // Entrar al parking
                    try {
                        ParkingComplicado.semaforo.acquire(); // ocupa plaza
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    ParkingComplicado.plazasLibres--;
                    ParkingComplicado.cochesEntrados++;
                    System.out.println(nombre + " ha entrado. Plazas libres: " + ParkingComplicado.plazasLibres);
                    System.out.println("Total coches entrados: " + ParkingComplicado.cochesEntrados);
                    entro = true; // marca que entró
                }
            }

            if (!entro) {
                // No hay plazas, espera antes de reintentar
                intentos++;
                System.out.println(nombre + " intenta entrar pero el parking está lleno. Intento " + intentos);
                try {
                    Thread.sleep(ParkingComplicado.random.nextInt(2000)); // espera 0-2 segundos
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        // Si entró, simula tiempo aparcado y luego sale
        if (entro) {
            try {
                Thread.sleep(ParkingComplicado.random.nextInt(3000)); // tiempo aparcado
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            synchronized (ParkingComplicado.class) {
                ParkingComplicado.plazasLibres++;
                System.out.println(nombre + " ha salido. Plazas libres: " + ParkingComplicado.plazasLibres);
            }

            ParkingComplicado.semaforo.release(); // libera semáforo
        } else {
            System.out.println(nombre + " no pudo entrar después de 3 intentos.");
        }
    }
}
