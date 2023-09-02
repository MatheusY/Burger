package org.mmatsubara.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.mmatsubara.exception.BusinessException;
import org.mmatsubara.exception.NotFoundException;
import org.mmatsubara.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserService implements IUserService {
    @Override
    public User findById(Long id) throws NotFoundException {
        Optional<User> optional = User.findByIdOptional(id);
        if (optional.isEmpty()) {
            throw new NotFoundException("User doesn't exist!");
        }
        return optional.get();
    }

    @Transactional
    @Override
    public User save(User user) throws BusinessException {
        List<User> persistedUsers = User.find("email = ?1", user.email).list();
        if (!persistedUsers.isEmpty()) {
            if (persistedUsers.get(0).isActive) {
                throw new BusinessException("This email already exists!");
            }
            throw new BusinessException("This account has been inactivated, please contact the administrator!");
        }
        user.id = null;
        user.createdDate = LocalDateTime.now();
        user.isActive = true;
        user.isVerified = false;
        User.persist(user);
        return user;
    }

    @Transactional
    @Override
    public void update(User user) throws NotFoundException {
        var persistedUser = findById(user.id);
        persistedUser.name = user.name;
    }
}
