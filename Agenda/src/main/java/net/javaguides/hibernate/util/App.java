package net.javaguides.hibernate.util;

import net.javaguides.hibernate.entity.Persona;
import net.javaguides.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Scanner;

public class App {
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println(" AGENDA DE PERSONAS - HIBERNATE");
        System.out.println("===================================");
        
        mostrarMenu();
    }
    
    public static void mostrarMenu() {
        int opcion;
        
        do {
            System.out.println("\n MENÚ PRINCIPAL");
            System.out.println("1.  Crear nueva persona");
            System.out.println("2.  Listar todas las personas");
            System.out.println("3.  Buscar persona por código");
            System.out.println("4.  Actualizar persona");
            System.out.println("5.  Eliminar persona");
            System.out.println("0.  Salir");
            System.out.print("Selecciona una opción: ");
            
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            
            switch (opcion) {
                case 1:
                    crearPersona();
                    break;
                case 2:
                    listarPersonas();
                    break;
                case 3:
                    buscarPersonaPorId();
                    break;
                case 4:
                    actualizarPersona();
                    break;
                case 5:
                    eliminarPersona();
                    break;
                case 0:
                    System.out.println(" ¡Hasta pronto!");
                    HibernateUtil.shutdown();
                    break;
                default:
                    System.out.println(" Opción no válida");
            }
        } while (opcion != 0);
        
        scanner.close();
    }
    
    public static void crearPersona() {
        System.out.println("\n CREAR NUEVA PERSONA");
        System.out.println("======================");
        
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();
        
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        System.out.print("Teléfono: ");
        int telefono = scanner.nextInt();
        
        System.out.print("Altura (cm): ");
        int altura = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        
        try {
            transaction = session.beginTransaction();
            
            Persona persona = new Persona(0, nombre, direccion, email, telefono, altura);
            session.persist(persona);
            
            transaction.commit();
            System.out.println(" Persona creada exitosamente!");
            System.out.println("Código asignado: " + persona.getCodigo());
            
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error al crear persona: " + e.getMessage());
        } finally {
            session.close();
        }
    }
    
    public static void listarPersonas() {
        System.out.println("\n LISTA DE PERSONAS");
        System.out.println("====================");
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        try {
            List<Persona> personas = session.createQuery("FROM Persona ORDER BY codigo", Persona.class).list();
            
            if (personas.isEmpty()) {
                System.out.println(" No hay personas registradas");
            } else {
                System.out.println("Total de personas: " + personas.size());
                System.out.println("----------------------------------------");
                for (Persona persona : personas) {
                    System.out.println(persona);
                }
            }
            
        } catch (Exception e) {
            System.out.println(" Error al listar personas: " + e.getMessage());
        } finally {
            session.close();
        }
    }
    
    public static void buscarPersonaPorId() {
        System.out.println("\n BUSCAR PERSONA POR CÓDIGO");
        System.out.println("============================");
        
        System.out.print("Ingresa el código de la persona: ");
        int codigo = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        try {
            Persona persona = session.get(Persona.class, codigo);
            
            if (persona != null) {
                System.out.println(" Persona encontrada:");
                System.out.println(persona);
            } else {
                System.out.println(" No se encontró persona con código: " + codigo);
            }
            
        } catch (Exception e) {
            System.out.println(" Error al buscar persona: " + e.getMessage());
        } finally {
            session.close();
        }
    }
    
    public static void actualizarPersona() {
        System.out.println("\n ACTUALIZAR PERSONA");
        System.out.println("====================");
        
        System.out.print("Ingresa el código de la persona a actualizar: ");
        int codigo = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        
        try {
            Persona persona = session.get(Persona.class, codigo);
            
            if (persona != null) {
                System.out.println("Persona actual:");
                System.out.println(persona);
                System.out.println("\nIngresa los nuevos datos (deja vacío para mantener el valor actual):");
                
                System.out.print("Nombre [" + persona.getName() + "]: ");
                String nombre = scanner.nextLine();
                if (!nombre.isEmpty()) persona.setName(nombre);
                
                System.out.print("Dirección [" + persona.getDireccion() + "]: ");
                String direccion = scanner.nextLine();
                if (!direccion.isEmpty()) persona.setDireccion(direccion);
                
                System.out.print("Email [" + persona.getEmail() + "]: ");
                String email = scanner.nextLine();
                if (!email.isEmpty()) persona.setEmail(email);
                
                System.out.print("Teléfono [" + persona.getTelefono() + "]: ");
                String telefonoStr = scanner.nextLine();
                if (!telefonoStr.isEmpty()) persona.setTelefono(Integer.parseInt(telefonoStr));
                
                System.out.print("Altura [" + persona.getAltura() + "]: ");
                String alturaStr = scanner.nextLine();
                if (!alturaStr.isEmpty()) persona.setAltura(Integer.parseInt(alturaStr));
                
                transaction = session.beginTransaction();
                session.merge(persona);
                transaction.commit();
                
                System.out.println(" Persona actualizada exitosamente!");
            } else {
                System.out.println(" No se encontró persona con código: " + codigo);
            }
            
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println(" Error al actualizar persona: " + e.getMessage());
        } finally {
            session.close();
        }
    }
    
    public static void eliminarPersona() {
        System.out.println("\n ELIMINAR PERSONA");
        System.out.println("==================");
        
        System.out.print("Ingresa el código de la persona a eliminar: ");
        int codigo = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        
        System.out.print("¿Estás seguro de eliminar esta persona? (s/n): ");
        String confirmacion = scanner.nextLine();
        
        if (!confirmacion.equalsIgnoreCase("s")) {
            System.out.println(" Eliminación cancelada");
            return;
        }
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        
        try {
            transaction = session.beginTransaction();
            
            Persona persona = session.get(Persona.class, codigo);
            if (persona != null) {
                session.remove(persona);
                transaction.commit();
                System.out.println(" Persona eliminada exitosamente!");
            } else {
                System.out.println(" No se encontró persona con código: " + codigo);
            }
            
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println(" Error al eliminar persona: " + e.getMessage());
        } finally {
            session.close();
        }
    }
    
    

}