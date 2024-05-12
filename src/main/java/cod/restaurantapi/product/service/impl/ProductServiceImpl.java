package cod.restaurantapi.product.service.impl;

import cod.restaurantapi.product.repository.ProductRepository;
import cod.restaurantapi.product.repository.entity.ProductEntity;
import cod.restaurantapi.product.service.ProductService;
import cod.restaurantapi.product.service.command.ProductAddCommand;
import cod.restaurantapi.product.service.domain.Product;
import cod.restaurantapi.product.service.mapper.ProductAddCommandToProductMapper;
import cod.restaurantapi.product.service.mapper.ProductDtoToProductEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private static final ProductAddCommandToProductMapper productAddCommandToProduct = ProductAddCommandToProductMapper.INSTANCE;

    private static final ProductDtoToProductEntityMapper productDtoToProductEntity = ProductDtoToProductEntityMapper.INSTANCE;


    @Override

    public void save(ProductAddCommand productAddCommand) {

        Product product = productAddCommandToProduct.map(productAddCommand);
        product.active();

        ProductEntity productEntity = productDtoToProductEntity.map(product);

        productRepository.save(productEntity);

    }
}
