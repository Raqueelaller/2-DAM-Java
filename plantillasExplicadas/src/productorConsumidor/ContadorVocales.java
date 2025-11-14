package productorConsumidor;

public class ContadorVocales implements Runnable {

    // Buffer compartido.
    private final BufferLinea buffer;

    public ContadorVocales(BufferLinea buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {

        try {
            while (true) {

                // Consumir una línea del buffer (posible wait() interno)
                String linea = buffer.obtenerLinea();

                // Sentinel que indica que debemos terminar.
                if (linea.equals("__FIN__")) {
                    break;
                }

                // Calcular número de vocales.
                int vocales = 0;

                for (char c : linea.toLowerCase().toCharArray()) {
                    if ("aeiou".indexOf(c) != -1) {
                        vocales++;
                    }
                }

                // Mostrar resultado.
                System.out.println("[Vocales] \"" + linea + "\" -> " + vocales);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("[Vocales] terminado.");
    }

}
