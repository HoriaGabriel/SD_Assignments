package ro.utcluj.foodpanda;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import ro.utcluj.foodpanda.model.Role;
import ro.utcluj.foodpanda.repository.RoleRepository;
import ro.utcluj.foodpanda.service.RoleServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ro.utcluj.foodpanda.model.ERole.ROLE_CLIENT;

@RunWith(MockitoJUnitRunner.class)
public class RoleServiceImplTest {

    @InjectMocks
    private RoleServiceImpl roleServiceImpl;

    private RoleRepository roleRepository;

    @Before
    public void setup(){

        roleRepository = mock(RoleRepository.class);

        roleServiceImpl = new RoleServiceImpl(roleRepository);
    }

    @Test
    public void getByNameServiceScenario() {

        Role sampleRole = new Role();
        sampleRole.setName(ROLE_CLIENT);

        when(roleRepository.findByName(ROLE_CLIENT)).thenReturn(sampleRole);

        Role testRole = roleServiceImpl.getByNameService(ROLE_CLIENT);

        assertEquals(sampleRole,testRole);

    }

    @Test
    public void getByNameServiceFailScenario() {

        when(roleRepository.findByName(ROLE_CLIENT)).thenReturn(null);

        Role testRole = roleServiceImpl.getByNameService(ROLE_CLIENT);

        assertEquals(null,testRole);

    }
}
