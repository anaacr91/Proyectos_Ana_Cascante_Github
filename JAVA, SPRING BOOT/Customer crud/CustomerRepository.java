import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {
    private List<Customer> customers = new ArrayList<>();


    public void agregarCliente(Customer customer) {
        customers.add(customer);
    }


    public Customer buscarClientePorId(int id) {
        for (Customer customer : customers) {
            if (customer.getId() == id) {
                return customer;
            }
        }
        return null; 
    }


    public boolean modificarCliente(int id, String nuevoNombre, String nuevoApellido, String nuevoEmail, int nuevaEdad) {
        Customer customer = buscarClientePorId(id);
        if (customer != null) {
            customer.setNombre(nuevoNombre);
            customer.setApellido(nuevoApellido);
            customer.setEmail(nuevoEmail);
            customer.setEdad(nuevaEdad);
            return true;
        }
        return false;
    }


    public boolean eliminarCliente(int id) {
        Customer customer = buscarClientePorId(id);
        if (customer != null) {
            customers.remove(customer);
            return true;
        }
        return false;
    }

    public List<Customer> obtenerTodosLosClientes() {
        return new ArrayList<>(customers);
    }


    public void eliminarTodosLosClientes() {
        customers.clear();
    }
}