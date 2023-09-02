package org.mmatsubara.service;

import org.mmatsubara.exception.BusinessException;
import org.mmatsubara.exception.NotFoundException;
import org.mmatsubara.model.User;

public interface IUserService {
    User findById(Long id) throws NotFoundException;

    User save(User user) throws BusinessException;

    void update(User user) throws NotFoundException;
}
