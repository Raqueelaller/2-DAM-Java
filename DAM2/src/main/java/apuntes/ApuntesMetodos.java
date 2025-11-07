package apuntes;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ApuntesMetodos {

    private static final Scanner sc = new Scanner(System.in);

    // ==========================================
    // 🔹 LEER ENTERO
    // ==========================================
    public static int leerEntero(String mensaje) {
        int valor;
        while (true) {
            System.out.print(mensaje);
            try {
                valor = Integer.parseInt(sc.nextLine());
                break; // ✅ valor correcto → salimos del bucle
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Error: introduce un número entero válido.");
            }
        }
        return valor;
    }

    // ==========================================
    // 🔹 LEER DOUBLE
    // ==========================================
    public static double leerDouble(String mensaje) {
        double valor;
        while (true) {
            System.out.print(mensaje);
            try {
                valor = Double.parseDouble(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Error: introduce un número decimal válido (usa punto, no coma).");
            }
        }
        return valor;
    }

    // ==========================================
    // 🔹 LEER BOOLEAN
    // ==========================================
    /*
     * Acepta: true / false / sí / no / s / n / 1 / 0
     */
    public static boolean leerBoolean(String mensaje) {
        while (true) {
            System.out.print(mensaje + " (sí/no): ");
            String entrada = sc.nextLine().trim().toLowerCase();

            if (entrada.equals("true") || entrada.equals("sí") || entrada.equals("si") || entrada.equals("s") || entrada.equals("1"))
                return true;
            else if (entrada.equals("false") || entrada.equals("no") || entrada.equals("n") || entrada.equals("0"))
                return false;
            else
                System.out.println("⚠️ Error: introduce 'sí' o 'no'.");
        }
    }

    // ==========================================
    // 🔹 LEER STRING (NO VACÍO)
    // ==========================================
    public static String leerString(String mensaje) {
        String texto;
        do {
            System.out.print(mensaje);
            texto = sc.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println("⚠️ Error: el texto no puede estar vacío.");
            }
        } while (texto.isEmpty());
        return texto;
    }
}
