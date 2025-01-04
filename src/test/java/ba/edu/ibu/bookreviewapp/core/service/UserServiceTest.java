package ba.edu.ibu.bookreviewapp.core.service;

import ba.edu.ibu.bookreviewapp.core.model.User;
import ba.edu.ibu.bookreviewapp.core.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        User user1 = new User();
        user1.setId(1L);
        user1.setEmail("test1@example.com");

        User user2 = new User();
        user2.setId(2L);
        user2.setEmail("test2@example.com");

        List<User> users = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById_Found() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(1L);

        assertTrue(result.isPresent());
        assertEquals("test@example.com", result.get().getEmail());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<User> result = userService.getUserById(1L);

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUserByEmail_Found() {
        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        User result = userService.getUserByEmail("test@example.com");

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        verify(userRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void testSaveUser() {
        User user = new User();
        user.setEmail("new@example.com");

        when(userRepository.save(user)).thenReturn(user);

        User result = userService.saveUser(user);

        assertNotNull(result);
        assertEquals("new@example.com", result.getEmail());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;

        doNothing().when(userRepository).deleteById(userId);

        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testFindByEmail_Found() {
        User user = new User();
        user.setEmail("found@example.com");

        when(userRepository.findByEmail("found@example.com")).thenReturn(user);

        Optional<User> result = userService.findByEmail("found@example.com");

        assertTrue(result.isPresent());
        assertEquals("found@example.com", result.get().getEmail());
        verify(userRepository, times(1)).findByEmail("found@example.com");
    }

    @Test
    void testFindByEmail_NotFound() {
        when(userRepository.findByEmail("notfound@example.com")).thenReturn(null);

        Optional<User> result = userService.findByEmail("notfound@example.com");

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findByEmail("notfound@example.com");
    }
}
