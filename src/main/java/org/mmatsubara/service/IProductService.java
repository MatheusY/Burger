package org.mmatsubara.service;

import org.mmatsubara.exception.NotFoundException;
import org.mmatsubara.model.Product;

import java.util.List;

public interface IProductService {

    List<Product> find(String query, String sort, Integer pageIndex, Integer pageSize);

    Product save(Product product);

    void update(Long id, Product product) throws NotFoundException;

    void delete(Long id) throws NotFoundException;
}
