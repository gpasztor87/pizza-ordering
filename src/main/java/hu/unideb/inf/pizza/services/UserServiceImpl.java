package hu.unideb.inf.pizza.services;

import hu.unideb.inf.pizza.dao.interfaces.UserDao;
import hu.unideb.inf.pizza.managers.ConnectionManager;
import hu.unideb.inf.pizza.models.User;
import hu.unideb.inf.pizza.services.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.*;

import java.util.Set;

import static org.apache.commons.codec.digest.DigestUtils.*;

/**
 * A UserService interfészt megvalósító osztály.
 */
public class UserServiceImpl implements UserService {

    /**
     * A {@link UserDao} interfész egy implementációjának példánya.
     */
    private UserDao userDao;

    /**
     * A {@link ConnectionManager} interfész egy implementációjának példánya.
     */
    private ConnectionManager connectionManager;

    /**
     * A logger egy példánya.
     */
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * Az osztály konstruktora inicializálja a userDao objektumot.
     *
     * @param connectionManager A connectionManager interfész egy implementációjának példánya
     * @param userDao A userDao interfész egy implementációjának példánya
     */
    public UserServiceImpl(ConnectionManager connectionManager, UserDao userDao) {
        this.connectionManager = connectionManager;
        this.userDao = userDao;
    }

    @Override
    public User createUser(User user) throws ValidationException {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        if (constraintViolations.isEmpty()) {
            String encryptedPassword = sha256Hex(user.getPassword());
            user.setPassword(encryptedPassword);

            try {
                connectionManager.beginTransaction();
                userDao.create(user);
                connectionManager.commit();
            } catch (Exception e) {
                logger.error(e.getMessage());
            } finally {
                connectionManager.close();
            }
        } else {
            throw new ValidationException(constraintViolations.iterator().next().getMessage());
        }

        return user;
    }

    @Override
    public User updateUser(User user) throws ValidationException {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        if (constraintViolations.isEmpty()) {
            String encryptedPassword = sha256Hex(user.getPassword());
            user.setPassword(encryptedPassword);

            try {
                connectionManager.beginTransaction();
                userDao.update(user);
                connectionManager.commit();
            } catch (Exception e) {
                logger.error(e.getMessage());
            } finally {
                connectionManager.close();
            }
        } else {
            throw new ValidationException(constraintViolations.iterator().next().getMessage());
        }

        return user;
    }

    @Override
    public User getUserById(int id) {
        return userDao.findById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public boolean validateUser(String email, String password) {
        User user = userDao.findByEmail(email);
        String encryptedPassword = sha256Hex(password);

        return user != null && user.checkPassword(encryptedPassword);
    }

}
