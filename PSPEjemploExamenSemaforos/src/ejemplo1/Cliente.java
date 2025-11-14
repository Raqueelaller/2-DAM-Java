package ejemplo1;


public class Cliente implements Runnable {

    private final String nombre;

    public Cliente(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        Banco.atenderCliente(nombre);
    }
}
