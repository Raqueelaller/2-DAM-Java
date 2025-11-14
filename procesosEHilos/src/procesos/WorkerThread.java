package procesos;

public class WorkerThread implements Runnable {
	  private final String mensajeOriginal;
	    private String resultado;

	    // Recibe el mensaje del proceso hijo
	    public WorkerThread(String mensajeOriginal) {
	        this.mensajeOriginal = mensajeOriginal;
	    }

	    // Aquí va el "trabajo" del hilo
	    @Override
	    public void run() {
	        // Procesamiento: invertimos la cadena
	        String procesado = new StringBuilder(mensajeOriginal).reverse().toString();

	        // Guardamos el resultado para que el hijo lo recupere
	        resultado = "Procesado por hilo → " + procesado;
	    }

	    // Método para recuperar el resultado una vez termina el hilo
	    public String getResultado() {
	        return resultado;
	    }

}

/*javac procesos\*.java     comando que se usa en la terminal dentro del src*/
/*java procesos.Parent      comando que ejecuta el proceso del padre*/

