package ro.utcluj.foodpanda.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ro.utcluj.foodpanda.model.ERole;
import ro.utcluj.foodpanda.model.Role;
import ro.utcluj.foodpanda.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService{

    private RoleRepository roleRepository;

    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    public RoleServiceImpl( RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    /**
     * The method will find a Role based on its name
     * @param roleCustomer the name of role to be found
     * @return the role in case it is found and null otherwise
     */
    @Override
    public Role getByNameService(ERole roleCustomer) {

        logger.info("Searching for Role with given role name {}",roleCustomer);

        Role userRole = roleRepository.findByName(roleCustomer);

        logger.info("Role has been found {}",userRole);

        return userRole;
    }
}
