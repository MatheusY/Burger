package org.mmatsubara.controller;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.mmatsubara.dto.UserDTO;
import org.mmatsubara.exception.BusinessException;
import org.mmatsubara.exception.NotFoundException;
import org.mmatsubara.model.User;
import org.mmatsubara.service.IUserService;

@Path("/api/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional(Transactional.TxType.SUPPORTS)
public class UserController extends AbstractController {

    @Inject
    private IUserService userService;


    @GET
    @Path("{id}")
    public UserDTO findById(@PathParam("id") Long id) throws NotFoundException {
        return convert(userService.findById(id), UserDTO.class);
    }

    @POST
    @ResponseStatus(201)
    public Long saveUser(UserDTO userDTO) throws BusinessException {
        return userService.save(convert(userDTO, User.class)).id;
    }

    @PUT
    @Path("{id}")
    public void updateUser(@PathParam("id") Long id, UserDTO userDTO) throws NotFoundException{
        userService.update(id, convert(userDTO, User.class));
    }
}