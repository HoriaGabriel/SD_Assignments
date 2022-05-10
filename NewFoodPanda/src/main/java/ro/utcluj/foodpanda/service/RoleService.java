package ro.utcluj.foodpanda.service;

import ro.utcluj.foodpanda.model.ERole;
import ro.utcluj.foodpanda.model.Role;

public interface RoleService {

    Role getByNameService(ERole roleCustomer);
}
