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
                        agregarCliente();
                        break;
                    case 2:
                        buscarClientePorId();
                        break;
                    case 3:
                        actualizarCliente();
                        break;
                    case 4:
                        eliminarCliente();
                        break;
                    case 5:
                        imprimirTodosLosClientes();
                        break;
                    case 6:
                        eliminarTodosLosClientes();
                        break;
                    case 7:
                        System.out.println("Saliendo del programa...");
                        return;
                    default:
                        System.out.println("Opción inválida, intente de nuevo.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.nextLine(); 
            }
        }
    }

    private static void imprimirMenu() {
        System.out.println("\nMenú:");
        System.out.println("1. Agregar cliente");
        System.out.println("2. Buscar cliente por ID");
        System.out.println("3. Actualizar cliente");
        System.out.println("4. Eliminar cliente");
        System.out.println("5. Ver todos los clientes");
        System.out.println("6. Eliminar todos los clientes");
        System.out.println("7. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void agregarCliente() {
        System.out.print("Introduce el ID: ");
        long id = scanner.nextLong();
        scanner.nextLine(); 
        System.out.print("Introduce el nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Introduce el apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("Introduce el email: ");
        String email = scanner.nextLine();

        Customer customer = new Customer(id, nombre, apellido, email);
        customerRepository.save(customer);
        System.out.println("Cliente agregado exitosamente.");
    }

    private static void buscarClientePorId() {
        System.out.print("Introduce el ID del cliente a buscar: ");
        long id = scanner.nextLong();
        Customer customer = customerRepository.findById(id);
        if (customer != null) {
            System.out.println(customer);
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private static void actualizarCliente() {
        System.out.print("Introduce el ID del cliente a actualizar: ");
        long id = scanner.nextLong();
        scanner.nextLine(); 
        System.out.print("Introduce el nuevo nombre: ");
        String nuevoNombre = scanner.nextLine();
        System.out.print("Introduce el nuevo apellido: ");
        String nuevoApellido = scanner.nextLine();
        System.out.print("Introduce el nuevo email: ");
        String nuevoEmail = scanner.nextLine();

        Customer updatedCustomer = new Customer(id, nuevoNombre, nuevoApellido, nuevoEmail);
        boolean actualizado = customerRepository.update(id, updatedCustomer);
        if (actualizado) {
            System.out.println("Cliente actualizado exitosamente.");
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private static void eliminarCliente() {
        System.out.print("Introduce el ID del cliente a eliminar: ");
        long id = scanner.nextLong();
        boolean eliminado = customerRepository.delete(id);
        if (eliminado) {
            System.out.println("Cliente eliminado.");
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private static void imprimirTodosLosClientes() {
        if (customerRepository.findAll().isEmpty()) {
            System.out.println("No hay clientes registrados.");
        } else {
            for (Customer customer : customerRepository.findAll().values()) {
                System.out.println(customer);
            }
        }
    }

    private static void eliminarTodosLosClientes() {
        customerRepository.deleteAll();
        System.out.println("Todos los clientes han sido eliminados.");
    }
}
