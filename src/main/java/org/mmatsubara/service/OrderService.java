package org.mmatsubara.service;

import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.mmatsubara.dto.OrderDTO;
import org.mmatsubara.exception.BusinessException;
import org.mmatsubara.exception.NotFoundException;
import org.mmatsubara.model.Order;
import org.mmatsubara.model.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class OrderService implements IOrderService {
    @Override
    public List<Order> findByClientId(Long customerId) {
        return Order.find("customer.id = ?1", Sort.by("orderedDate"), customerId).list();
    }

    @Override
    public Order findById(Long id) throws NotFoundException {
        List<Order> orders = Order.find("id = ?1", id).list();
        if(orders.isEmpty()) {
            throw new NotFoundException("Order doesn't exist!");
        }
        return orders.get(0);
    }

    @Transactional
    @Override
    public Order save(Order order) {
        order.id = null;
        order.orderedDate = LocalDateTime.now();
        order.items.forEach(item -> {
            item.id = null;
            item.order = order;
        });
        Order.persist(order);
        return order;
    }

    @Transactional
    @Override
    public void update(Long id, String orderStatus) throws NotFoundException, BusinessException {
        var order  = findById(id);
        try {
            order.orderStatus = OrderStatus.valueOf(orderStatus);
            Order.getEntityManager().merge(order);
        } catch(IllegalArgumentException ex) {
            throw new BusinessException("Invalid status!");
        }

    }
}
