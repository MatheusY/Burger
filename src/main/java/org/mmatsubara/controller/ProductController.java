package org.mmatsubara.controller;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.mmatsubara.dto.ProductDTO;
import org.mmatsubara.exception.NotFoundException;
import org.mmatsubara.model.Product;
import org.mmatsubara.service.ProductService;

import java.util.List;

@Path("/api/products")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional(Transactional.TxType.SUPPORTS)
public class ProductController extends AbstractController {

    @Inject
    private ProductService productService;

    @GET
    public List<ProductDTO> getProducts(@QueryParam("query") String query, @QueryParam("sort") @DefaultValue("id") String sort, @QueryParam("page") @DefaultValue("0") Integer pageIndex, @QueryParam("size") @DefaultValue("5") Integer pageSize) {
        return convert(productService.find(query, sort, pageIndex, pageSize), ProductDTO.class);
    }

    @GET
    @Path("{id}")
    public ProductDTO findById(@PathParam("id") Long id) throws NotFoundException {
        return convert(productService.findById(id), ProductDTO.class);
    }

    @POST
    @ResponseStatus(201)
    public Long saveProduct(ProductDTO productDTO) {
        return productService.save(convert(productDTO, Product.class)).id;
    }

    @PUT
    @Path("{id}")
    public void updateProduct(@PathParam("id") Long id, ProductDTO productDTO) throws NotFoundException {
        productService.update(id, convert(productDTO, Product.class));
    }

    @DELETE
    @Path("{id}")
    public void deleteProduct(@PathParam("id") Long id) throws NotFoundException {
        productService.delete(id);
    }
}
