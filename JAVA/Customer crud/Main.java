import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static CustomerRepository customerRepository = new CustomerRepository();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            imprimirMenu();
            try {
                int opcion = scanner.nextInt();
                scanner.nextLine(); 
                switch (opcion) {
                    case 1:
                        imprimirTodosLosClientes();
                        break;
                    case 2:
                        buscarClientePorId();
                        break;
                    case 3:
                        agregarNuevoCliente();
                        break;
                    case 4:
                        modificarCliente();
                        break;
                    case 5:
                        eliminarCliente();
                        break;
                    case 6:
                        eliminarTodosLosClientes();
                        break;
                    case 7:
                        System.out.println("Saliendo del programa...");
                        return;
                    default:
                        System.out.println("Opción no válida. Inténtalo de nuevo.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor ingrese un número.");
                scanner.nextLine(); 
            }
        }
    }

    private static void imprimirMenu() {
        System.out.println("\nMenú de Clientes:");
        System.out.println("1. Imprimir todos los clientes");
        System.out.println("2. Encontrar e imprimir un cliente por ID");
        System.out.println("3. Crear un cliente nuevo");
        System.out.println("4. Modificar un cliente por ID");
        System.out.println("5. Eliminar un cliente por ID");
        System.out.println("6. Borrar todos los clientes");
        System.out.println("7. Salir");
        System.out.print("Elige una opción: ");
    }

    private static void imprimirTodosLosClientes() {
        for (Customer customer : customerRepository.obtenerTodosLosClientes()) {
            System.out.println(customer);
        }
    }

    private static void buscarClientePorId() {
        System.out.print("Introduce el ID del cliente: ");
        int id = scanner.nextInt();
        Customer customer = customerRepository.buscarClientePorId(id);
        if (customer != null) {
            System.out.println(customer);
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private static void agregarNuevoCliente() {
        System.out.print("Introduce el ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 

        System.out.print("Introduce el nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Introduce el apellido: ");
        String apellido = scanner.nextLine();

        System.out.print("Introduce el email: ");
        String email = scanner.nextLine();

        System.out.print("Introduce la edad: ");
        int edad = scanner.nextInt();

        Customer nuevoCliente = new Customer(id, nombre, apellido, email, edad);
        customerRepository.agregarCliente(nuevoCliente);
        System.out.println("Cliente creado exitosamente.");
    }

    private static void modificarCliente() {
        System.out.print("Introduce el ID del cliente a modificar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 

        System.out.print("Nuevo nombre: ");
        String nuevoNombre = scanner.nextLine();

        System.out.print("Nuevo apellido: ");
        String nuevoApellido = scanner.nextLine();

        System.out.print("Nuevo email: ");
        String nuevoEmail = scanner.nextLine();

        System.out.print("Nueva edad: ");
        int nuevaEdad = scanner.nextInt();

        boolean modificado = customerRepository.modificarCliente(id, nuevoNombre, nuevoApellido, nuevoEmail, nuevaEdad);
        if (modificado) {
            System.out.println("Cliente modificado exitosamente.");
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private static void eliminarCliente() {
        System.out.print("Introduce el ID del cliente a eliminar: ");
        int id = scanner.nextInt();
        boolean eliminado = customerRepository.eliminarCliente(id);
        if (eliminado) {
            System.out.println("Cliente eliminado.");
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private static void eliminarTodosLosClientes() {
        customerRepository.eliminarTodosLosClientes();
        System.out.println("Todos los clientes han sido eliminados.");
    }
}