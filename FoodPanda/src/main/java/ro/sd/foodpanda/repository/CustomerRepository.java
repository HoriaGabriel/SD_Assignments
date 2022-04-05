package ro.sd.foodpanda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sd.foodpanda.model.Client;

public interface CustomerRepository extends JpaRepository<Client,Long> {

    Client findByUsername(String username);
}
