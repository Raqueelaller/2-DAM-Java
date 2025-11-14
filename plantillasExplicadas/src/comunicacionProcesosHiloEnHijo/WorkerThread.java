package comunicacionProcesosHiloEnHijo;

public class WorkerThread implements Runnable {

    private final String mensaje;   // mensaje original
    private String resultado;       // mensaje procesado

    public WorkerThread(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public void run() {
        // Procesamiento: invertir el texto
        resultado = new StringBuilder(mensaje).reverse().toString();
    }

    // Método para recuperar el resultado tras el join()
    public String getResultado() {
        return resultado;
    }
    /*javac procesos\*.java     comando que se usa en la terminal dentro del src*/
    /*java procesos.Parent      comando que ejecuta el proceso del padre*/


}
