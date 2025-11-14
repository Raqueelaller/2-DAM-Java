package ejemplo1;


public class PitufoRondas implements Runnable {

    private final String nombre;
    private final int rondas;

    public PitufoRondas(String nombre, int rondas) {
        this.nombre = nombre;
        this.rondas = rondas;
    }

    @Override
    public void run() {
        // Aquí llamo al método principal de comer
        ComerPrincipalRondas.comer(nombre, rondas);
    }
}
