package ro.sd.foodpanda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sd.foodpanda.model.Administrator;

public interface AdministratorRepository extends JpaRepository<Administrator,Long> {

    Administrator findByUsername(String administrator);

}
