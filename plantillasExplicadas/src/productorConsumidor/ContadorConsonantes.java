package productorConsumidor;

public class ContadorConsonantes implements Runnable {

    private final BufferLinea buffer;

    public ContadorConsonantes(BufferLinea buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {

        try {
            while (true) {

                String linea = buffer.obtenerLinea();

                if (linea.equals("__FIN__")) break;

                int consonantes = 0;

                for (char c : linea.toLowerCase().toCharArray()) {
                    if (Character.isLetter(c) && "aeiou".indexOf(c) == -1) {
                        consonantes++;
                    }
                }

                System.out.println("[Consonantes] \"" + linea + "\" -> " + consonantes);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("[Consonantes] terminado.");
    }
}