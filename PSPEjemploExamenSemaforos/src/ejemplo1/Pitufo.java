package ejemplo1;

public class Pitufo implements Runnable {

    private final String nombre;

    public Pitufo(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        ComerPrincipal.comer(nombre);
    }
}

