package com.henderson.simpleuser.service;

import com.henderson.simpleuser.domain.SimpleUser;
import com.henderson.simpleuser.exceptions.AlreadyExistsException;
import com.henderson.simpleuser.exceptions.NotFoundException;
import com.henderson.simpleuser.repository.SimpleUserRepository;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link SimpleUserService}
 */
public class SimpleUserServiceTest {


    @Test
    public void allUsers() throws Exception {
        List<SimpleUser> expected = new ArrayList<>();
        SimpleUserRepository repository = mock(SimpleUserRepository.class);
        SimpleUserService service = new SimpleUserService(repository);
        when(repository.findAll()).thenReturn(expected);

        List<SimpleUser> actual = service.allUsers();

        assertEquals(expected, actual);
    }

    @Test
    public void createNewUser() throws Exception {
        Long id = 1L;
        SimpleUser user = mock(SimpleUser.class);
        when(user.getId()).thenReturn(id);
        SimpleUserRepository repository = mock(SimpleUserRepository.class);
        SimpleUserService service = new SimpleUserService(repository);
        when(repository.save(user)).thenReturn(user);
        when(repository.exists(id)).thenReturn(false);

        SimpleUser actual = service.createNewUser(user);

        assertEquals(user, actual);
    }

    @Test(expected = AlreadyExistsException.class)
    public void createNewUserAlreadyExists() throws Exception {
        Long id = 1L;
        SimpleUser user = mock(SimpleUser.class);
        when(user.getId()).thenReturn(id);
        SimpleUserRepository repository = mock(SimpleUserRepository.class);
        SimpleUserService service = new SimpleUserService(repository);
        when(repository.exists(id)).thenReturn(true);

        service.createNewUser(user);
    }

    @Test
    public void createNewUserIdAutoIncrement() throws Exception {
        Long id = 1L;
        Long id2 = 2L;
        SimpleUser user = mock(SimpleUser.class);
        when(user.getId()).thenReturn(id);
        SimpleUser newUser = mock(SimpleUser.class);
        when(newUser.getId()).thenReturn(id2);
        SimpleUserRepository repository = mock(SimpleUserRepository.class);
        SimpleUserService service = new SimpleUserService(repository);
        when(repository.save(user)).thenReturn(newUser);
        when(repository.exists(id)).thenReturn(false);

        SimpleUser actual = service.createNewUser(user);

        assertEquals(newUser, actual);
        assertEquals(id2, actual.getId());
    }

    @Test
    public void updateUser() throws Exception {
        Long id = 1L;
        SimpleUser user = mock(SimpleUser.class);
        when(user.getId()).thenReturn(id);
        SimpleUser updated = mock(SimpleUser.class);
        SimpleUserRepository repository = mock(SimpleUserRepository.class);
        SimpleUserService service = new SimpleUserService(repository);
        when(repository.save(user)).thenReturn(updated);
        when(repository.exists(id)).thenReturn(true);

        SimpleUser actual = service.updateUser(user);

        assertNotEquals(user, actual);
    }

    @Test(expected = NotFoundException.class)
    public void updateUserNotFound() throws Exception {
        Long id = 1L;
        SimpleUser user = mock(SimpleUser.class);
        when(user.getId()).thenReturn(id);
        SimpleUserRepository repository = mock(SimpleUserRepository.class);
        SimpleUserService service = new SimpleUserService(repository);
        when(repository.exists(id)).thenReturn(false);

        service.updateUser(user);
    }

    @Test
    public void removeUser() throws Exception {
        Long id = 1L;
        SimpleUserRepository repository = mock(SimpleUserRepository.class);
        SimpleUserService service = new SimpleUserService(repository);
        when(repository.exists(id)).thenReturn(true);

        service.removeUser(id);

        verify(repository).delete(id);
    }

    @Test(expected = NotFoundException.class)
    public void removeUserNotFound() throws Exception {
        Long id = 1L;
        SimpleUserRepository repository = mock(SimpleUserRepository.class);
        SimpleUserService service = new SimpleUserService(repository);
        when(repository.exists(id)).thenReturn(false);

        service.removeUser(id);

        verify(repository, never()).delete(anyLong());
    }

}