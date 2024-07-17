package cod.restaurantapi.menu.service.impl;

import cod.restaurantapi.RMAServiceTest;
import cod.restaurantapi.common.model.Pagination;
import cod.restaurantapi.common.model.RMAPageResponse;
import cod.restaurantapi.common.model.Sorting;
import cod.restaurantapi.menu.service.command.MenuListCommand;
import cod.restaurantapi.menu.service.domain.Menu;
import cod.restaurantapi.product.model.enums.ExtentType;
import cod.restaurantapi.product.model.enums.ProductStatus;
import cod.restaurantapi.product.repository.ProductRepository;
import cod.restaurantapi.product.repository.entity.ProductEntity;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class MenuServiceImplTest extends RMAServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private MenuServiceImpl menuService;

    @Test
    void givenMenuListCommand_whenFilterAndSorting_thenReturnMenu() {

        //given
        int pageNumber = 1;
        int pageSize = 3;
        MenuListCommand.MenuFilter menuFilter = MenuListCommand.MenuFilter.builder()
                .name("product")
                .build();

        MenuListCommand menuListCommand = MenuListCommand.builder()
                .filter(menuFilter)
                .pagination(Pagination.builder()
                        .pageNumber(pageSize)
                        .pageSize(pageNumber)
                        .build())
                .sorting(Sorting.builder()
                        .direction(Sort.Direction.ASC)
                        .property("id")
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


        RMAPageResponse<Menu> exceptedList = menuService.getMenu(menuListCommand);

        //verify
        Mockito.verify(productRepository, Mockito.times(1))
                .findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));

        Mockito.verify(productRepository, Mockito.never()).findAll();

        Assertions.assertEquals(exceptedList.getContent().get(0).getProduct().getName(), productEntities.get(0).getName());
        Assertions.assertEquals(exceptedList.getContent().get(1).getProduct().getName(), productEntities.get(1).getName());
        Assertions.assertEquals(exceptedList.getContent().get(2).getProduct().getName(), productEntities.get(2).getName());

        Assertions.assertEquals(exceptedList.getPageSize(), pageSize);
        Assertions.assertEquals(exceptedList.getTotalElementCount(), productEntities.size());

    }

    @Test
    void givenMenuListCommand_whenWithOutFilter_thenReturnMenu() {

        //given
        int pageNumber = 1;
        int pageSize = 3;


        MenuListCommand menuListCommand = MenuListCommand.builder()
                .pagination(Pagination.builder()
                        .pageNumber(pageSize)
                        .pageSize(pageNumber)
                        .build())
                .sorting(Sorting.builder()
                        .direction(Sort.Direction.ASC)
                        .property("id")
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


        RMAPageResponse<Menu> exceptedList = menuService.getMenu(menuListCommand);

        //verify
        Mockito.verify(productRepository, Mockito.times(1))
                .findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));

        Mockito.verify(productRepository, Mockito.never()).findAll();

        Assertions.assertEquals(exceptedList.getContent().get(0).getProduct().getName(), productEntities.get(0).getName());
        Assertions.assertEquals(exceptedList.getContent().get(1).getProduct().getName(), productEntities.get(1).getName());
        Assertions.assertEquals(exceptedList.getContent().get(2).getProduct().getName(), productEntities.get(2).getName());

        Assertions.assertEquals(exceptedList.getPageSize(), pageSize);
        Assertions.assertEquals(exceptedList.getTotalElementCount(), productEntities.size());

    }

    @Test
    void givenMenuListCommand_whenWithOutSorting_thenReturnMenu() {

        //given
        int pageNumber = 1;
        int pageSize = 3;
        MenuListCommand.MenuFilter menuFilter = MenuListCommand.MenuFilter.builder()
                .name("product")
                .build();

        MenuListCommand menuListCommand = MenuListCommand.builder()
                .filter(menuFilter)
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


        RMAPageResponse<Menu> exceptedList = menuService.getMenu(menuListCommand);

        //verify
        Mockito.verify(productRepository, Mockito.times(1))
                .findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));

        Mockito.verify(productRepository, Mockito.never()).findAll();

        Assertions.assertEquals(exceptedList.getContent().get(0).getProduct().getName(), productEntities.get(0).getName());
        Assertions.assertEquals(exceptedList.getContent().get(1).getProduct().getName(), productEntities.get(1).getName());
        Assertions.assertEquals(exceptedList.getContent().get(2).getProduct().getName(), productEntities.get(2).getName());

        Assertions.assertEquals(exceptedList.getPageSize(), pageSize);
        Assertions.assertEquals(exceptedList.getTotalElementCount(), productEntities.size());

    }

    @Test
    void givenMenuListCommand_whenWithOutFilterAndSorting_thenReturnMenu() {

        //given
        int pageNumber = 1;
        int pageSize = 3;

        MenuListCommand menuListCommand = MenuListCommand.builder()
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


        RMAPageResponse<Menu> exceptedList = menuService.getMenu(menuListCommand);

        //verify
        Mockito.verify(productRepository, Mockito.times(1))
                .findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));

        Mockito.verify(productRepository, Mockito.never()).findAll();

        Assertions.assertEquals(exceptedList.getContent().get(0).getProduct().getName(), productEntities.get(0).getName());
        Assertions.assertEquals(exceptedList.getContent().get(1).getProduct().getName(), productEntities.get(1).getName());
        Assertions.assertEquals(exceptedList.getContent().get(2).getProduct().getName(), productEntities.get(2).getName());

        Assertions.assertEquals(exceptedList.getPageSize(), pageSize);
        Assertions.assertEquals(exceptedList.getTotalElementCount(), productEntities.size());

    }


}