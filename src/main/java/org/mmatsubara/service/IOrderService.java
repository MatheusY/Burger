package org.mmatsubara.service;

import org.mmatsubara.dto.OrderDTO;
import org.mmatsubara.exception.BusinessException;
import org.mmatsubara.exception.NotFoundException;
import org.mmatsubara.model.Order;
import org.mmatsubara.model.OrderStatus;

import java.util.List;

public interface IOrderService {

    List<Order> findByClientId(Long customerId);

    Order findById(Long id) throws NotFoundException;

    Order save(Order order);

    void update(Long id, String orderStatus) throws NotFoundException, BusinessException;
}
