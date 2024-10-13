import java.util.HashMap;
import java.util.Map;

public class CustomerRepository {
    private Map<Long, Customer> customers = new HashMap<>();


    public void save(Customer customer) {
        customers.put(customer.getId(), customer);
    }


    public Customer findById(Long id) {
        return customers.get(id);
    }


    public boolean update(Long id, Customer updatedCustomer) {
        if (customers.containsKey(id)) {
            customers.put(id, updatedCustomer);
            return true;
        }
        return false;
    }


    public boolean delete(Long id) {
        if (customers.containsKey(id)) {
            customers.remove(id);
            return true;
        }
        return false;
    }


    public Map<Long, Customer> findAll() {
        return new HashMap<>(customers);
    }


    public void deleteAll() {
        customers.clear();
    }
}
