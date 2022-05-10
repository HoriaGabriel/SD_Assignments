package ro.utcluj.foodpanda;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import ro.utcluj.foodpanda.model.*;
import ro.utcluj.foodpanda.repository.UserRepository;
import ro.utcluj.foodpanda.service.UserServiceImpl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    private UserRepository userRepository;

    @Before
    public void setup(){

        userRepository = mock(UserRepository.class);

        userServiceImpl = new UserServiceImpl(userRepository);

    }

    @Test
    public void getAdminUserWithNameServiceScenario() {

        User sampleAdminUser = new User();
        sampleAdminUser.setPassword("nmjkli");
        sampleAdminUser.setUsername("Sample");

        Role sampleRole = new Role();
        sampleRole.setName(ERole.ROLE_ADMIN);

        sampleAdminUser.setRole(sampleRole);

        when(userRepository.findByUsername("Sample")).thenReturn(sampleAdminUser);

        User testAdminUser = userServiceImpl.getAdminUserWithNameService("Sample");

        assertEquals(sampleAdminUser,testAdminUser);

    }

    @Test
    public void getAdminUserWithNameServiceFailScenario() {


        when(userRepository.findByUsername("Sample")).thenReturn(null);

        User testAdminUser = userServiceImpl.getAdminUserWithNameService("Sample");

        assertEquals(null,testAdminUser);

    }

    @Test
    public void getAdminUserWithNameServiceFailScenario2() {

        User sampleAdminUser = new User();
        sampleAdminUser.setPassword("nmjkli");
        sampleAdminUser.setUsername("Sample");

        Role sampleRole = new Role();
        sampleRole.setName(ERole.ROLE_CLIENT);

        sampleAdminUser.setRole(sampleRole);

        when(userRepository.findByUsername("Sample")).thenReturn(sampleAdminUser);

        User testAdminUser = userServiceImpl.getAdminUserWithNameService("Sample");

        assertEquals(null,testAdminUser);

    }


    @Test
    public void existsByUsernameServiceScenario() {

        User sampleClientUser = new User();
        sampleClientUser.setPassword("nmjkli");
        sampleClientUser.setUsername("Sample");


        when(userRepository.findByUsername("Sample")).thenReturn(sampleClientUser);

        Boolean result = userServiceImpl.existsByUsernameService("Sample");

        assertEquals(true,result);
    }

    @Test
    public void existsByUsernameServiceFailScenario() {

        when(userRepository.findByUsername("Sample")).thenReturn(null);

        Boolean result = userServiceImpl.existsByUsernameService("Sample");

        assertEquals(false,result);
    }

    @Test
    public void getClientUserWithNameServiceScenario() {

        User sampleClientUser = new User();
        sampleClientUser.setPassword("nmjkli");
        sampleClientUser.setUsername("Sample");

        Role sampleRole = new Role();
        sampleRole.setName(ERole.ROLE_CLIENT);

        sampleClientUser.setRole(sampleRole);

        when(userRepository.findByUsername("Sample")).thenReturn(sampleClientUser);

        User testClientUser = userServiceImpl.getClientUserWithNameService("Sample");

        assertEquals(sampleClientUser,testClientUser);

    }

    @Test
    public void getClientUserWithNameServiceFailScenario() {

        when(userRepository.findByUsername("Sample")).thenReturn(null);

        User testClientUser = userServiceImpl.getClientUserWithNameService("Sample");

        assertEquals(null,testClientUser);

    }

    @Test
    public void getClientUserWithNameServiceFailScenario2() {

        User sampleClientUser = new User();
        sampleClientUser.setPassword("nmjkli");
        sampleClientUser.setUsername("Sample");

        Role sampleRole = new Role();
        sampleRole.setName(ERole.ROLE_ADMIN);

        sampleClientUser.setRole(sampleRole);

        when(userRepository.findByUsername("Sample")).thenReturn(sampleClientUser);

        User testAdminUser = userServiceImpl.getClientUserWithNameService("Sample");

        assertEquals(null,testAdminUser);

    }

    @Test
    public void existsByEmailServiceScenario() {

        User sampleClientUser = new User();
        sampleClientUser.setPassword("nmjkli");
        sampleClientUser.setUsername("Sample");
        sampleClientUser.setEmail("sa.mple@gmial.com");


        when(userRepository.findByEmail("sa.mple@gmial.com")).thenReturn(sampleClientUser);

        Boolean result = userServiceImpl.existsByEmailService("sa.mple@gmial.com");

        assertEquals(true,result);
    }

    @Test
    public void existsByEmailServiceFailScenario() {

        when(userRepository.findByEmail("sa.mple@gmial.com")).thenReturn(null);

        Boolean result = userServiceImpl.existsByEmailService("sa.mple@gmial.com");

        assertEquals(false,result);
    }

}
