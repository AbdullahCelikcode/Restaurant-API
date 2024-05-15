package cod.restaurantapi.product.service;

import cod.restaurantapi.common.model.RMAPageResponse;
import cod.restaurantapi.product.service.command.ProductAddCommand;
import cod.restaurantapi.product.service.command.ProductListCommand;
import cod.restaurantapi.product.service.command.ProductUpdateCommand;
import cod.restaurantapi.product.service.domain.Product;

import java.util.UUID;

public interface ProductService {

    void save(ProductAddCommand productAddCommand);

    Product findById(UUID id);

    void delete(UUID id);

    Product update(UUID id, ProductUpdateCommand productUpdateCommand);

    RMAPageResponse<Product> findAll(ProductListCommand productListCommand);
}
