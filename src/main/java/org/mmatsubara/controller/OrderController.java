package org.mmatsubara.controller;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.mmatsubara.dto.OrderDTO;
import org.mmatsubara.exception.BusinessException;
import org.mmatsubara.exception.NotFoundException;
import org.mmatsubara.model.Order;
import org.mmatsubara.model.OrderStatus;
import org.mmatsubara.service.IOrderService;

import java.util.List;

@Path("/api/orders")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional(Transactional.TxType.SUPPORTS)
public class OrderController extends AbstractController {

    @Inject
    private IOrderService orderService;

    @GET
    @Path("customer/{id}")
    public List<OrderDTO> findOrdersByCustomerId(@PathParam("id") Long customerId) {
        return convert(orderService.findByClientId(customerId), OrderDTO.class);
    }

    @GET
    @Path("{id}")
    public OrderDTO findById(@PathParam("id") Long id) throws NotFoundException {
        return convert(orderService.findById(id), OrderDTO.class);
    }

    @POST
    @ResponseStatus(201)
    public Long saveOrder(OrderDTO orderDTO) {
        return orderService.save(convert(orderDTO, Order.class)).id;
    }

    @PATCH
    @Path("{id}")
    public void updateOrderStatus(@PathParam("id") Long id, OrderDTO orderDTO) throws NotFoundException, BusinessException {
        orderService.update(id, orderDTO.getOrderStatus());
    }
}
