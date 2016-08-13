package ch.reserveyourroom.user.service;

import ch.reserveyourroom.common.service.GenericService;
import ch.reserveyourroom.user.model.User;

import javax.ejb.Stateless;

/**
 * Specific method definition for the User service
 */
@Stateless
public interface UserService extends GenericService<User> {
}
