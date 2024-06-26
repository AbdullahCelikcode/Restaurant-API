package cod.restaurantapi.product.service.impl;

import cod.restaurantapi.category.controller.exceptions.CategoryNotFoundException;
import cod.restaurantapi.category.model.enums.CategoryStatus;
import cod.restaurantapi.category.repository.CategoryRepository;
import cod.restaurantapi.common.exception.RMAStatusAlreadyChangedException;
import cod.restaurantapi.common.model.RMAPageResponse;
import cod.restaurantapi.common.model.Sorting;
import cod.restaurantapi.product.controller.exceptions.ProductAlreadyExistException;
import cod.restaurantapi.product.controller.exceptions.ProductNotFoundException;
import cod.restaurantapi.product.model.enums.ProductStatus;
import cod.restaurantapi.product.repository.ProductRepository;
import cod.restaurantapi.product.repository.entity.ProductEntity;
import cod.restaurantapi.product.service.ProductService;
import cod.restaurantapi.product.service.command.ProductAddCommand;
import cod.restaurantapi.product.service.command.ProductListCommand;
import cod.restaurantapi.product.service.command.ProductUpdateCommand;
import cod.restaurantapi.product.service.domain.Product;
import cod.restaurantapi.product.service.mapper.ProductAddCommandToProductMapper;
import cod.restaurantapi.product.service.mapper.ProductEntityToProductMapper;
import cod.restaurantapi.product.service.mapper.ProductToProductEntityMapper;
import cod.restaurantapi.product.service.mapper.ProductUpdateCommandToProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private static final ProductAddCommandToProductMapper productAddCommandToProduct = ProductAddCommandToProductMapper.INSTANCE;
    private static final ProductToProductEntityMapper productDtoToProductEntity = ProductToProductEntityMapper.INSTANCE;
    private static final ProductEntityToProductMapper productEntityToProductMapper = ProductEntityToProductMapper.INSTANCE;
    private static final ProductUpdateCommandToProductEntity productUpdateCommandToProductEntity = ProductUpdateCommandToProductEntity.INSTANCE;
    private final String currency;

    @Override
    public Product findById(UUID id) {

        ProductEntity productEntity = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        Product product = productEntityToProductMapper.map(productEntity);
        product.setCurrency(currency);

        return product;
    }


    @Override
    public RMAPageResponse<Product> findAll(ProductListCommand productListCommand) {

        Page<ProductEntity> responseList = productRepository.findAll(
                productListCommand.toSpecification(ProductEntity.class),
                productListCommand.toPageable());

        List<Product> productList = productEntityToProductMapper.map(responseList.getContent());
        productList.forEach(product -> product.setCurrency(currency));

        return RMAPageResponse.<Product>builder()
                .page(responseList)
                .content(productList)
                .sortedBy(Sorting.of(responseList.getSort()))
                .filteredBy(productListCommand.getFilter()).build();
    }


    @Override
    public void save(ProductAddCommand productAddCommand) {

        if (productRepository.findByName(productAddCommand.getName()).isPresent()) {
            throw new ProductAlreadyExistException();
        }

        this.checkExistingOfCategory(productAddCommand.getCategoryId());


        Product product = productAddCommandToProduct.map(productAddCommand);
        product.active();

        ProductEntity productEntity = productDtoToProductEntity.map(product);

        productRepository.save(productEntity);

    }


    @Override
    public void update(UUID id, ProductUpdateCommand productUpdateCommand) {
        ProductEntity productEntity = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);

        this.checkExistingOfProductNameIfChanged(productUpdateCommand, productEntity);

        this.checkExistingOfCategory(productUpdateCommand.getCategoryId());

        productUpdateCommandToProductEntity.update(productUpdateCommand, productEntity);
        productRepository.save(productEntity);

    }

    private void checkExistingOfCategory(Long categoryId) {
        boolean categoryIsNotExist = categoryRepository.existsByIdAndStatusIsNot(categoryId, CategoryStatus.DELETED);

        if (!categoryIsNotExist) {
            throw new CategoryNotFoundException();
        }
    }

    private void checkExistingOfProductNameIfChanged(ProductUpdateCommand productUpdateCommand, ProductEntity productEntity) {
        if (!productEntity.getName().equalsIgnoreCase(productUpdateCommand.getName())) {
            checkExistingOfProductName(productUpdateCommand);
        }
    }

    private void checkExistingOfProductName(ProductUpdateCommand productUpdateCommand) {
        boolean isProductExistByName = productRepository.findByName(productUpdateCommand.getName()).isPresent();
        if (isProductExistByName) {
            throw new ProductAlreadyExistException();
        }
    }


    @Override
    public void delete(UUID id) {
        ProductEntity productEntity = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);

        this.checkIfStatusChanged(productEntity.getStatus(), ProductStatus.DELETED);
        productEntity.setStatus(ProductStatus.DELETED);

        productRepository.save(productEntity);
    }

    private void checkIfStatusChanged(ProductStatus entityStatus, ProductStatus status) {
        if (entityStatus == status) {
            throw new RMAStatusAlreadyChangedException();
        }
    }

}
