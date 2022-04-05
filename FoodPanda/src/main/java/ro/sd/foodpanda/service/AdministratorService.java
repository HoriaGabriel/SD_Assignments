package ro.sd.foodpanda.service;

import ro.sd.foodpanda.model.Administrator;

public interface AdministratorService {

    Administrator findAdmin(String name, String password);

    Administrator findAdminWithName(String administrator);

}
