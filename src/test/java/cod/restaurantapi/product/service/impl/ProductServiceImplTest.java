package cod.restaurantapi.product.service.impl;

import cod.restaurantapi.RMAServiceTest;
import cod.restaurantapi.category.controller.exceptions.CategoryNotFoundException;
import cod.restaurantapi.category.model.enums.CategoryStatus;
import cod.restaurantapi.category.repository.CategoryRepository;
import cod.restaurantapi.category.repository.entity.CategoryEntity;
import cod.restaurantapi.common.model.Pagination;
import cod.restaurantapi.common.model.RMAPageResponse;
import cod.restaurantapi.common.model.Sorting;
import cod.restaurantapi.product.controller.exceptions.ProductNotFoundException;
import cod.restaurantapi.product.model.enums.ExtentType;
import cod.restaurantapi.product.model.enums.ProductStatus;
import cod.restaurantapi.product.repository.ProductRepository;
import cod.restaurantapi.product.repository.entity.ProductEntity;
import cod.restaurantapi.product.service.command.ProductAddCommand;
import cod.restaurantapi.product.service.command.ProductListCommand;
import cod.restaurantapi.product.service.command.ProductUpdateCommand;
import cod.restaurantapi.product.service.domain.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductServiceImplTest extends RMAServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void givenFindById_whenProductExists_thenReturnProduct() {

        // given

        ProductEntity productEntity = ProductEntity.builder()
                .id(UUID.randomUUID())
                .name("test")
                .status(ProductStatus.ACTIVE)
                .categoryId(1L)
                .extent(BigDecimal.valueOf(100))
                .extentType(ExtentType.GR)
                .ingredient("ingredients")
                .price(BigDecimal.valueOf(100))
                .build();

        // when

        Mockito.when(productRepository.findById(productEntity.getId()))
                .thenReturn(Optional.of(productEntity));

        Product exceptedProduct = productService.findById(productEntity.getId());

        Mockito.verify(productRepository, Mockito.times(1)).findById(Mockito.any(UUID.class));
        Assertions.assertEquals(productEntity.getName(), exceptedProduct.getName());
        Assertions.assertEquals(productEntity.getId(), exceptedProduct.getId());
        Assertions.assertEquals(productEntity.getCategoryId(), exceptedProduct.getCategoryId());
        Assertions.assertEquals(productEntity.getStatus(), exceptedProduct.getStatus());
        Assertions.assertEquals(productEntity.getExtent(), exceptedProduct.getExtent());
        Assertions.assertEquals(productEntity.getExtentType(), exceptedProduct.getExtentType());
        Assertions.assertEquals(productEntity.getIngredient(), exceptedProduct.getIngredient());
        Assertions.assertEquals(productEntity.getPrice(), exceptedProduct.getPrice());
    }

    @Test
    void givenFindById_whenProductNotExists_thenReturnException() {
        UUID productId = UUID.randomUUID();

        Mockito.when(productRepository.findById((Mockito.any(UUID.class)))).thenThrow(ProductNotFoundException.class);

        Assertions.assertThrows(ProductNotFoundException.class, () -> productService.findById(productId));

    }

    @Test
    void givenFindAll_whenProductListWithFilterAndSorting_thenReturnProducts() {

        //given
        int pageNumber = 1;
        int pageSize = 3;
        ProductListCommand.ProductFilter productFilter = ProductListCommand.ProductFilter.builder()
                .name("product")
                .statuses(Set.of(ProductStatus.ACTIVE))
                .minPrice(1)
                .maxPrice(101)
                .build();

        ProductListCommand productListCommand = ProductListCommand.builder()
                .filter(productFilter)
                .pagination(Pagination.builder()
                        .pageNumber(pageSize
                        ).pageSize(pageNumber)
                        .build())
                .sorting(Sorting.builder()
                        .direction(Sort.Direction.ASC)
                        .property("name")
                        .build())

                .build();

        // then
        List<ProductEntity> productEntities = new ArrayList<>();
        productEntities.add(ProductEntity.builder()
                .id(UUID.randomUUID())
                .name("Product 1")
                .ingredient("ingredients")
                .price(BigDecimal.valueOf(100))
                .status(ProductStatus.ACTIVE)
                .extent(BigDecimal.valueOf(100))
                .extentType(ExtentType.GR)
                .categoryId(1L)
                .build()
        );
        productEntities.add(ProductEntity.builder()
                .id(UUID.randomUUID())
                .name("Product 2")
                .ingredient("ingredients 2")
                .price(BigDecimal.valueOf(100))
                .status(ProductStatus.ACTIVE)
                .extent(BigDecimal.valueOf(100))
                .extentType(ExtentType.GR)
                .categoryId(1L)
                .build()
        );
        productEntities.add(ProductEntity.builder()
                .id(UUID.randomUUID())
                .name("Product 3")
                .ingredient("ingredients 3")
                .price(BigDecimal.valueOf(100))
                .status(ProductStatus.ACTIVE)
                .extent(BigDecimal.valueOf(100))
                .extentType(ExtentType.GR)
                .categoryId(1L)
                .build()
        );

        Page<ProductEntity> productEntityPage = new PageImpl<>(productEntities);

        Mockito.when(productRepository.findAll(Mockito.any(Specification.class),
                Mockito.any(Pageable.class))).thenReturn(productEntityPage);


        RMAPageResponse<Product> exceptedList = productService.findAll(productListCommand);

        //verify
        Mockito.verify(productRepository, Mockito.times(1))
                .findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));

        Mockito.verify(productRepository, Mockito.never()).findAll();

        Assertions.assertEquals(exceptedList.getContent().get(0).getName(), productEntities.get(0).getName());
        Assertions.assertEquals(exceptedList.getContent().get(1).getName(), productEntities.get(1).getName());
        Assertions.assertEquals(exceptedList.getContent().get(2).getName(), productEntities.get(2).getName());

        Assertions.assertEquals(exceptedList.getPageSize(), pageSize);
        Assertions.assertEquals(exceptedList.getTotalElementCount(), productEntities.size());

    }

    @Test
    void givenFindAll_whenProductListWithFilter_thenReturnProducts() {

        //given
        int pageNumber = 1;
        int pageSize = 3;
        ProductListCommand.ProductFilter productFilter = ProductListCommand.ProductFilter.builder()
                .name("product")
                .statuses(Set.of(ProductStatus.ACTIVE))
                .minPrice(1)
                .maxPrice(101)
                .build();

        ProductListCommand productListCommand = ProductListCommand.builder()
                .filter(productFilter)
                .pagination(Pagination.builder()
                        .pageNumber(pageSize)
                        .pageSize(pageNumber)
                        .build())
                .build();

        // then
        List<ProductEntity> productEntities = new ArrayList<>();
        productEntities.add(ProductEntity.builder()
                .id(UUID.randomUUID())
                .name("Product 1")
                .ingredient("ingredients")
                .price(BigDecimal.valueOf(100))
                .status(ProductStatus.ACTIVE)
                .extent(BigDecimal.valueOf(100))
                .extentType(ExtentType.GR)
                .categoryId(1L)
                .build()
        );
        productEntities.add(ProductEntity.builder()
                .id(UUID.randomUUID())
                .name("Product 2")
                .ingredient("ingredients 2")
                .price(BigDecimal.valueOf(100))
                .status(ProductStatus.ACTIVE)
                .extent(BigDecimal.valueOf(100))
                .extentType(ExtentType.GR)
                .categoryId(1L)
                .build()
        );
        productEntities.add(ProductEntity.builder()
                .id(UUID.randomUUID())
                .name("Product 3")
                .ingredient("ingredients 3")
                .price(BigDecimal.valueOf(100))
                .status(ProductStatus.ACTIVE)
                .extent(BigDecimal.valueOf(100))
                .extentType(ExtentType.GR)
                .categoryId(1L)
                .build()
        );

        Page<ProductEntity> productEntityPage = new PageImpl<>(productEntities);

        Mockito.when(productRepository.findAll(Mockito.any(Specification.class),
                Mockito.any(Pageable.class))).thenReturn(productEntityPage);


        RMAPageResponse<Product> exceptedList = productService.findAll(productListCommand);

        //verify
        Mockito.verify(productRepository, Mockito.times(1))
                .findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));

        Mockito.verify(productRepository, Mockito.never()).findAll();

        Assertions.assertEquals(exceptedList.getContent().get(0).getName(), productEntities.get(0).getName());
        Assertions.assertEquals(exceptedList.getContent().get(1).getName(), productEntities.get(1).getName());
        Assertions.assertEquals(exceptedList.getContent().get(2).getName(), productEntities.get(2).getName());


        Assertions.assertEquals(exceptedList.getPageSize(), pageSize);
        Assertions.assertEquals(exceptedList.getTotalElementCount(), productEntities.size());

    }

    @Test
    void givenFindAll_whenProductListWithOutFilter_thenReturnProducts() {

        //given
        int pageNumber = 1;
        int pageSize = 3;


        ProductListCommand productListCommand = ProductListCommand.builder()
                .pagination(Pagination.builder()
                        .pageNumber(pageSize)
                        .pageSize(pageNumber)
                        .build())
                .build();

        // then
        List<ProductEntity> productEntities = new ArrayList<>();
        productEntities.add(ProductEntity.builder()
                .id(UUID.randomUUID())
                .name("Product 1")
                .ingredient("ingredients")
                .price(BigDecimal.valueOf(100))
                .status(ProductStatus.ACTIVE)
                .extent(BigDecimal.valueOf(100))
                .extentType(ExtentType.GR)
                .categoryId(1L)
                .build()
        );
        productEntities.add(ProductEntity.builder()
                .id(UUID.randomUUID())
                .name("Product 2")
                .ingredient("ingredients 2")
                .price(BigDecimal.valueOf(100))
                .status(ProductStatus.ACTIVE)
                .extent(BigDecimal.valueOf(100))
                .extentType(ExtentType.GR)
                .categoryId(1L)
                .build()
        );
        productEntities.add(ProductEntity.builder()
                .id(UUID.randomUUID())
                .name("Product 3")
                .ingredient("ingredients 3")
                .price(BigDecimal.valueOf(100))
                .status(ProductStatus.ACTIVE)
                .extent(BigDecimal.valueOf(100))
                .extentType(ExtentType.GR)
                .categoryId(1L)
                .build()
        );

        Page<ProductEntity> productEntityPage = new PageImpl<>(productEntities);

        Mockito.when(productRepository.findAll(Mockito.any(Specification.class),
                Mockito.any(Pageable.class))).thenReturn(productEntityPage);


        RMAPageResponse<Product> exceptedList = productService.findAll(productListCommand);

        //verify
        Mockito.verify(productRepository, Mockito.times(1))
                .findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));

        Mockito.verify(productRepository, Mockito.never()).findAll();

        Assertions.assertEquals(exceptedList.getContent().get(0).getName(), productEntities.get(0).getName());
        Assertions.assertEquals(exceptedList.getContent().get(1).getName(), productEntities.get(1).getName());
        Assertions.assertEquals(exceptedList.getContent().get(2).getName(), productEntities.get(2).getName());


        Assertions.assertEquals(exceptedList.getPageSize(), pageSize);
        Assertions.assertEquals(exceptedList.getTotalElementCount(), productEntities.size());

    }

    @Test
    void givenCreateProduct_whenExistCategoryId_thenSaveProduct() {
        // given

        ProductAddCommand productAddCommand = ProductAddCommand.builder()
                .name("Product 3")
                .ingredient("ingredients 3")
                .price(BigDecimal.valueOf(100))
                .extent(BigDecimal.valueOf(100))
                .extentType(ExtentType.GR)
                .categoryId(1L)
                .build();
        // then

        CategoryEntity categoryEntity = CategoryEntity.builder()
                .status(CategoryStatus.ACTIVE)
                .id(productAddCommand.getCategoryId())
                .build();

        Mockito.when(categoryRepository.existsByIdAndStatusIsNot(Mockito.anyLong(), Mockito.any(CategoryStatus.class)))
                .thenReturn(true);

        productService.save(productAddCommand);

        //verify

        Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any(ProductEntity.class));


    }

    @Test
    void givenCreateProduct_whenNotExistCategoryId_thenThrowException() {
        //given

        ProductAddCommand productAddCommand = ProductAddCommand.builder()
                .name("Product 3")
                .ingredient("ingredients 3")
                .price(BigDecimal.valueOf(100))
                .extent(BigDecimal.valueOf(100))
                .extentType(ExtentType.GR)
                .categoryId(1L)
                .build();
        //then

        Mockito.when(categoryRepository.existsByIdAndStatusIsNot(Mockito.anyLong(), Mockito.any(CategoryStatus.class)))
                .thenThrow(CategoryNotFoundException.class);

        assertThrows(CategoryNotFoundException.class,
                () -> productService.save(productAddCommand));

    }

    @Test
    void givenUpdateProductAndProductId_whenProductExist_thenUpdateProduct() {
        //given

        UUID productId = UUID.randomUUID();
        Long categoryId = 1L;

        ProductUpdateCommand productUpdateCommand = ProductUpdateCommand.builder()
                .name("ProductUpdate")
                .ingredient("update ")
                .status(ProductStatus.INACTIVE)
                .price(BigDecimal.valueOf(55))
                .extent(BigDecimal.valueOf(55))
                .extentType(ExtentType.ML)
                .categoryId(categoryId)
                .build();

        //when
        ProductEntity productEntity = ProductEntity.builder()
                .id(productId)
                .name("Product 3")
                .ingredient("ingredients 3")
                .status(ProductStatus.ACTIVE)
                .price(BigDecimal.valueOf(100))
                .extent(BigDecimal.valueOf(100))
                .extentType(ExtentType.GR)
                .categoryId(categoryId)
                .build();

        productEntity.setUpdatedAt(LocalDateTime.now());

        CategoryEntity categoryEntity = CategoryEntity.builder()
                .id(1L)
                .name("TestCategory")
                .status(CategoryStatus.ACTIVE)
                .build();

        Mockito.when(productRepository.findById(Mockito.any(UUID.class)))
                .thenReturn(Optional.of(productEntity));
        Mockito.when(productRepository.findByName(Mockito.any(String.class)))
                .thenReturn(Optional.ofNullable(null));
        Mockito.when(categoryRepository.existsByIdAndStatusIsNot(Mockito.anyLong(), Mockito.any(CategoryStatus.class)))
                .thenReturn(true);

        //then

        productService.update(productId, productUpdateCommand);

        //verify

        Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any(ProductEntity.class));

    }

    @Test
    void givenUpdateProductAndProductId_whenProductNotExist_thenUpdateProduct() {
        //given

        UUID productId = UUID.randomUUID();
        ProductUpdateCommand productUpdateCommand = ProductUpdateCommand.builder()
                .name("ProductUpdate")
                .ingredient("update ")
                .status(ProductStatus.INACTIVE)
                .price(BigDecimal.valueOf(55))
                .extent(BigDecimal.valueOf(55))
                .extentType(ExtentType.ML)
                .categoryId(3L)
                .build();
        //when

        Mockito.when(productRepository.findById(Mockito.any(UUID.class))).thenThrow(ProductNotFoundException.class);

        //then

        assertThrows(ProductNotFoundException.class,
                () -> productService.update(productId, productUpdateCommand));
    }

    @Test
    void givenUpdateProductAndProductId_whenCategoryNotExist_thenThrowCategoryNotFoundException() {
        //given
        UUID productId = UUID.randomUUID();
        ProductUpdateCommand productUpdateCommand = ProductUpdateCommand.builder()
                .name("ProductUpdate")
                .ingredient("update ")
                .status(ProductStatus.INACTIVE)
                .price(BigDecimal.valueOf(55))
                .extent(BigDecimal.valueOf(55))
                .extentType(ExtentType.ML)
                .categoryId(3L)
                .build();
        //when


        ProductEntity productEntity = ProductEntity.builder()
                .name("TestProductForUpdate")
                .build();

        Mockito.when(categoryRepository.existsByIdAndStatusIsNot(Mockito.any(Long.class), Mockito.any(CategoryStatus.class)))
                .thenThrow(CategoryNotFoundException.class);
        Mockito.when(productRepository.findById(Mockito.any(UUID.class)))
                .thenReturn(Optional.of(productEntity));

        //then
        assertThrows(CategoryNotFoundException.class,
                () -> productService.update(productId, productUpdateCommand));
    }

    @Test
    void givenDeleteProduct_WhenProductExist_thenDeleteProduct() {
        //given
        UUID productId = UUID.randomUUID();

        //when
        ProductEntity productEntity = ProductEntity.builder()
                .id(productId)
                .status(ProductStatus.ACTIVE)
                .build();

        Mockito.when(productRepository.findById(Mockito.any(UUID.class)))
                .thenReturn(Optional.of(productEntity));

        //then

        productService.delete(productId);

        //verify

        Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any(ProductEntity.class));
        Assertions.assertEquals(ProductStatus.DELETED, productEntity.getStatus());
    }

    @Test
    void givenDeleteProduct_WhenProductNotExist_thenThrowException() {
        //given
        UUID productId = UUID.randomUUID();

        //when
        Mockito.when(productRepository.findById(Mockito.any(UUID.class)))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(ProductNotFoundException.class, () -> productService.delete(productId));

    }


}