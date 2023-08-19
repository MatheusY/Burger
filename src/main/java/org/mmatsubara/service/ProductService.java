package org.mmatsubara.service;

import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.mmatsubara.exception.NotFoundException;
import org.mmatsubara.model.Product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@ApplicationScoped
public class ProductService implements IProductService {

    @Override
    public List<Product> find(String query, String sort, Integer pageIndex, Integer pageSize) {
        query = Objects.nonNull(query) && !query.isBlank() ?  query + " AND isActive = true" : "isActive = true";
        return Product.find(query, Sort.by(sort)).page(pageIndex, pageSize).list();
    }

    public Product findById(Long id) throws NotFoundException {
        List<Product> products = Product.find("id = ?1 AND isActive = true", id).list();
        if(products.isEmpty()) {
            throw new NotFoundException("Product doesn't not exist!");
        }
        return products.get(0);
    }

    @Override
    @Transactional
    public Product save(Product product) {
        product.id = null;
        product.createdDate = LocalDateTime.now();
        product.isActive = true;
        Product.persist(product);
        return product;
    }

    @Override
    @Transactional
    public void update(Long id, Product product) throws NotFoundException {
        var persistedProduct = findById(id);
        product.id = persistedProduct.id;
        product.createdDate = persistedProduct.createdDate;
        product.isActive = persistedProduct.isActive;
        Product.getEntityManager().merge(product);
    }

    @Override
    @Transactional
    public void delete(Long id) throws NotFoundException {
        var persistedProduct = findById(id);
        Product.update("isActive = false where id = ?1", id);
    }


}
