package cod.restaurantapi.product.service;

import cod.restaurantapi.product.service.command.ProductAddCommand;

public interface ProductService {
    void save(ProductAddCommand productAddCommand);
}
