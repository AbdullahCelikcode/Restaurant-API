package cod.restaurantapi.product.controller;

import cod.restaurantapi.RMAControllerTest;
import cod.restaurantapi.common.model.Pagination;
import cod.restaurantapi.common.model.RMAPageResponse;
import cod.restaurantapi.common.model.Sorting;
import cod.restaurantapi.product.controller.request.ProductAddRequest;
import cod.restaurantapi.product.controller.request.ProductListRequest;
import cod.restaurantapi.product.controller.request.ProductUpdateRequest;
import cod.restaurantapi.product.model.enums.ExtentType;
import cod.restaurantapi.product.model.enums.ProductStatus;
import cod.restaurantapi.product.service.ProductService;
import cod.restaurantapi.product.service.command.ProductAddCommand;
import cod.restaurantapi.product.service.command.ProductListCommand;
import cod.restaurantapi.product.service.command.ProductUpdateCommand;
import cod.restaurantapi.product.service.domain.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest extends RMAControllerTest {
    @MockBean
    private ProductService productService;

    private final static String BASE_URL = "/api/v1/product";

    @Test
    void givenGetProductId_whenValidInput_thenReturnSuccess() throws Exception {
        //given
        UUID productId = UUID.randomUUID();

        //when

        Product product = Product.builder()
                .id(productId)
                .name("Product 1")
                .ingredient("ingredients")
                .status(ProductStatus.ACTIVE)
                .price(BigDecimal.valueOf(100))
                .extent(BigDecimal.valueOf(100))
                .extentType(ExtentType.GR)
                .categoryId(1L)
                .build();

        Mockito.when(productService.findById(productId)).thenReturn(product);

        //then

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/{id}", productId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(product.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.name").value(product.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.ingredient").value(product.getIngredient()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.status").value(product.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.price").value(product.getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.extent").value(product.getExtent()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.extentType").value(product.getExtentType().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.categoryId").value(product.getCategoryId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"));
    }

    @Test
    void givenGetProductId_whenInvalidStringInput_thenReturnBadRequest() throws Exception {
        //given
        String productId = "1234565scdf";

        //then
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/{id}", productId))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    void givenGetProductId_whenInvalidIntegerInput_thenReturnBadRequest() throws Exception {
        //given
        Integer productId = 12345213;

        //then
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/{id}", productId))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }


    @Test
    void givenCreateProduct_whenValidInput_thenReturnSuccess() throws Exception {
        //given
        ProductAddRequest productAddRequest = ProductAddRequest.builder()
                .name("Product 1")
                .ingredient("ingredients")
                .price(BigDecimal.valueOf(100))
                .extent(BigDecimal.valueOf(100))
                .extentType(ExtentType.GR)
                .categoryId(1L)
                .build();
        //then
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productAddRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //verify
        Mockito.verify(productService, Mockito.times(1)).save(Mockito.any(ProductAddCommand.class));

    }

    @Test
    void givenCreateProduct_whenInvalidNameInput_thenReturnBadRequest() throws Exception {
        //given
        ProductAddRequest productAddRequest = ProductAddRequest.builder()
                .name("")
                .ingredient("ingredients")
                .price(BigDecimal.valueOf(100))
                .extent(BigDecimal.valueOf(100))
                .extentType(ExtentType.GR)
                .categoryId(1L)
                .build();
        //then
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productAddRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());


    }

    @Test
    void givenCreateProduct_whenWithoutNameInput_thenReturnBadRequest() throws Exception {
        //given
        ProductAddRequest productAddRequest = ProductAddRequest.builder()
                .ingredient("ingredients")
                .price(BigDecimal.valueOf(100))
                .extent(BigDecimal.valueOf(100))
                .extentType(ExtentType.GR)
                .categoryId(1L)
                .build();
        //then
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productAddRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());


    }

    @Test
    void givenCreateProduct_whenInvalidIngredientInput_thenReturnBadRequest() throws Exception {
        //given
        ProductAddRequest productAddRequest = ProductAddRequest.builder()
                .name("test")
                .ingredient("")
                .price(BigDecimal.valueOf(100))
                .extent(BigDecimal.valueOf(100))
                .extentType(ExtentType.GR)
                .categoryId(1L)
                .build();
        //then
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productAddRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    void givenCreateProduct_whenWithoutIngredientInput_thenReturnBadRequest() throws Exception {
        //given
        ProductAddRequest productAddRequest = ProductAddRequest.builder()
                .name("test")
                .price(BigDecimal.valueOf(100))
                .extent(BigDecimal.valueOf(100))
                .extentType(ExtentType.GR)
                .categoryId(1L)
                .build();
        //then
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productAddRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    void givenCreateProduct_whenInvalidPriceInput_thenReturnBadRequest() throws Exception {
        //given
        ProductAddRequest productAddRequest = ProductAddRequest.builder()
                .name("test")
                .ingredient("test")
                .price(BigDecimal.valueOf(-1))
                .extent(BigDecimal.valueOf(100))
                .extentType(ExtentType.GR)
                .categoryId(1L)
                .build();
        //then
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productAddRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    void givenCreateProduct_whenWithoutPriceInput_thenReturnBadRequest() throws Exception {
        //given
        ProductAddRequest productAddRequest = ProductAddRequest.builder()
                .name("test")
                .ingredient("test")
                .extent(BigDecimal.valueOf(100))
                .extentType(ExtentType.GR)
                .categoryId(1L)
                .build();

        //then
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productAddRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    void givenCreateProduct_whenInvalidExtentInput_thenReturnBadRequest() throws Exception {
        //given
        ProductAddRequest productAddRequest = ProductAddRequest.builder()
                .name("test")
                .ingredient("test")
                .price(BigDecimal.valueOf(100))
                .extent(BigDecimal.valueOf(-1))
                .extentType(ExtentType.GR)
                .categoryId(1L)
                .build();
        //then
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productAddRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    void givenCreateProduct_whenWithoutExtentInput_thenReturnBadRequest() throws Exception {
        //given
        ProductAddRequest productAddRequest = ProductAddRequest.builder()
                .name("test")
                .ingredient("test")
                .price(BigDecimal.valueOf(100))
                .extentType(ExtentType.GR)
                .categoryId(1L)
                .build();

        //then
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productAddRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    void givenCreateProduct_whenInvalidCategoryIdInput_thenReturnBadRequest() throws Exception {
        //given
        ProductAddRequest productAddRequest = ProductAddRequest.builder()
                .name("test")
                .ingredient("test")
                .price(BigDecimal.valueOf(100))
                .extent(BigDecimal.valueOf(100))
                .extentType(ExtentType.GR)
                .categoryId(-1L)
                .build();
        //then
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productAddRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    void givenCreateProduct_whenWithoutCategoryIdInput_thenReturnBadRequest() throws Exception {
        //given
        ProductAddRequest productAddRequest = ProductAddRequest.builder()
                .name("test")
                .ingredient("test")
                .price(BigDecimal.valueOf(100))
                .extent(BigDecimal.valueOf(100))
                .extentType(ExtentType.GR)
                .build();

        //then
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productAddRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    void givenCreateProduct_whenWithoutExtentTypeInput_thenReturnBadRequest() throws Exception {
        //given
        ProductAddRequest productAddRequest = ProductAddRequest.builder()
                .name("test")
                .ingredient("test")
                .price(BigDecimal.valueOf(100))
                .extent(BigDecimal.valueOf(100))
                .categoryId(1L)
                .build();
        //then
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productAddRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }


    @Test
    void givenGetAllProducts_whenValidInput_thenReturnSuccess() throws Exception {
        // Given
        ProductListRequest.ProductFilter filter = ProductListRequest.ProductFilter.builder()
                .name("Test")
                .statuses(Set.of(ProductStatus.ACTIVE))
                .minPrice(150)
                .maxPrice(1)
                .build();

        ProductListRequest productListRequest = ProductListRequest.builder()
                .pagination(Pagination.builder()
                        .pageNumber(1)
                        .pageSize(3)
                        .build())
                .sorting(Sorting.builder()
                        .direction(Sort.Direction.ASC)
                        .property("price")
                        .build())
                .filter(filter)
                .build();

        // When
        List<Product> products = new ArrayList<>();
        products.add(Product.builder()
                .id(UUID.randomUUID())
                .name("product 1")
                .status(ProductStatus.ACTIVE)
                .build());
        products.add(Product.builder()
                .id(UUID.randomUUID())
                .name("product 2")
                .status(ProductStatus.ACTIVE)
                .build());
        products.add(Product.builder()
                .id(UUID.randomUUID())
                .name("product 3")
                .status(ProductStatus.INACTIVE)
                .build());

        RMAPageResponse<Product> rmaPage = RMAPageResponse.<Product>builder()
                .content(products)
                .pageNumber(1)
                .pageSize(3)
                .totalPageCount(1)
                .totalElementCount(3L)
                .sortedBy(productListRequest.getSorting())
                .filteredBy(filter)
                .build();

        Mockito.when(productService.findAll(Mockito.any(ProductListCommand.class))).thenReturn(rmaPage);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productListRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageNumber")
                        .value(rmaPage.getPageNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageSize")
                        .value(rmaPage.getPageSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPageCount")
                        .value(rmaPage.getTotalPageCount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.filteredBy.name")
                        .value(filter.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"));
    }

    @Test
    void givenGetAllProducts_whenWithoutFilter_thenReturnSuccess() throws Exception {
        // Given


        ProductListRequest productListRequest = ProductListRequest.builder()
                .pagination(Pagination.builder().pageNumber(1).pageSize(3).build())
                .sorting(Sorting.builder().direction(Sort.Direction.ASC).property("price").build())
                .build();

        // When
        List<Product> products = new ArrayList<>();
        products.add(Product.builder()
                .id(UUID.randomUUID())
                .name("product 1")
                .status(ProductStatus.ACTIVE)
                .build());
        products.add(Product.builder()
                .id(UUID.randomUUID())
                .name("product 2")
                .status(ProductStatus.ACTIVE)
                .build());
        products.add(Product.builder()
                .id(UUID.randomUUID())
                .name("product 3")
                .status(ProductStatus.INACTIVE)
                .build());

        RMAPageResponse<Product> rmaPage = RMAPageResponse.<Product>builder()
                .content(products)
                .pageNumber(1)
                .pageSize(3)
                .totalPageCount(1)
                .totalElementCount(3L)
                .sortedBy(productListRequest.getSorting())
                .filteredBy(productListRequest.getFilter())
                .build();

        Mockito.when(productService.findAll(Mockito.any(ProductListCommand.class))).thenReturn(rmaPage);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productListRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageNumber")
                        .value(rmaPage.getPageNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageSize")
                        .value(rmaPage.getPageSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPageCount")
                        .value(rmaPage.getTotalPageCount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"));
    }

    @Test
    void givenGetAllProducts_whenWithoutSorting_thenReturnSuccess() throws Exception {
        // Given
        ProductListRequest.ProductFilter filter = ProductListRequest.ProductFilter.builder()
                .name("Test")
                .statuses(Set.of(ProductStatus.ACTIVE))
                .minPrice(150)
                .maxPrice(1)
                .build();

        ProductListRequest productListRequest = ProductListRequest.builder()
                .pagination(Pagination.builder().pageNumber(1).pageSize(3).build())
                .filter(filter)
                .build();

        // When
        List<Product> products = new ArrayList<>();
        products.add(Product.builder()
                .id(UUID.randomUUID())
                .name("product 1")
                .status(ProductStatus.ACTIVE)
                .build());
        products.add(Product.builder()
                .id(UUID.randomUUID())
                .name("product 2")
                .status(ProductStatus.ACTIVE)
                .build());
        products.add(Product.builder()
                .id(UUID.randomUUID())
                .name("product 3")
                .status(ProductStatus.INACTIVE)
                .build());

        RMAPageResponse<Product> rmaPage = RMAPageResponse.<Product>builder()
                .content(products)
                .pageNumber(1)
                .pageSize(3)
                .totalPageCount(1)
                .totalElementCount(3L)
                .sortedBy(productListRequest.getSorting())
                .filteredBy(filter)
                .build();

        Mockito.when(productService.findAll(Mockito.any(ProductListCommand.class))).thenReturn(rmaPage);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productListRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageNumber")
                        .value(rmaPage.getPageNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageSize")
                        .value(rmaPage.getPageSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPageCount")
                        .value(rmaPage.getTotalPageCount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.filteredBy.name")
                        .value(filter.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"));
    }

    @Test
    void givenGetAllProducts_whenWithoutPaginationNumber_thenReturnBadRequest() throws Exception {
        // Given
        ProductListRequest.ProductFilter filter = ProductListRequest.ProductFilter.builder()
                .name("Test")
                .statuses(Set.of(ProductStatus.ACTIVE))
                .minPrice(150)
                .maxPrice(1)
                .build();

        ProductListRequest productListRequest = ProductListRequest.builder()
                .sorting(Sorting.builder()
                        .direction(Sort.Direction.ASC)
                        .property("price")
                        .build())
                .pagination(Pagination.builder()
                        .pageSize(3).build())
                .filter(filter)
                .build();


        // Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productListRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    void givenGetAllProducts_whenWithoutPaginationSize_thenReturnBadRequest() throws Exception {
        // Given
        ProductListRequest.ProductFilter filter = ProductListRequest.ProductFilter.builder()
                .name("Test")
                .statuses(Set.of(ProductStatus.ACTIVE))
                .minPrice(150)
                .maxPrice(1)
                .build();

        ProductListRequest productListRequest = ProductListRequest.builder()
                .sorting(Sorting.builder()
                        .direction(Sort.Direction.ASC)
                        .property("price")
                        .build())
                .pagination(Pagination.builder()
                        .pageNumber(1)
                        .build())
                .filter(filter)
                .build();


        // Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productListRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    void givenGetAllProducts_whenInvalidPagination_thenReturnBadRequest() throws Exception {
        // Given
        ProductListRequest.ProductFilter filter = ProductListRequest.ProductFilter.builder()
                .name("Test")
                .statuses(Set.of(ProductStatus.ACTIVE))
                .minPrice(150)
                .maxPrice(1)
                .build();

        ProductListRequest productListRequest = ProductListRequest.builder()
                .sorting(Sorting.builder().direction(Sort.Direction.ASC).property("price").build())
                .filter(filter)
                .build();


        // Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productListRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    void givenGetAllProduct_whenInvalidInput_thenReturnBadRequest() throws Exception {
        // Given
        ProductListRequest productListRequest = ProductListRequest.builder().build();

        // Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productListRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void givenGetAllProduct_whenInvalidNegativePageSize_thenReturnBadRequest() throws Exception {
        // Given
        ProductListRequest productListRequest = ProductListRequest.builder()
                .pagination(Pagination.builder()
                        .pageSize(-1)
                        .pageNumber(1)
                        .build())
                .build();

        // Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productListRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void givenGetAllProducts_whenInvalidNegativePageNumber_thenReturnBadRequest() throws Exception {
        // Given
        ProductListRequest productListRequest = ProductListRequest.builder()
                .pagination(Pagination.builder()
                        .pageSize(1)
                        .pageNumber(-1)
                        .build())
                .build();

        // Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productListRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    void givenUpdateProduct_whenValidInput_thenReturnSuccess() throws Exception {

        //Given
        UUID productId = UUID.randomUUID();
        ProductUpdateRequest productUpdateRequest = ProductUpdateRequest.builder()
                .name("test")
                .ingredient("test")
                .status(ProductStatus.ACTIVE)
                .price(BigDecimal.valueOf(100))
                .extent(BigDecimal.valueOf(100))
                .extentType(ExtentType.GR)
                .categoryId(1L)
                .build();

        //when
        Product product = Product.builder()
                .id(productId)
                .name("test")
                .ingredient("test")
                .status(ProductStatus.ACTIVE)
                .price(BigDecimal.valueOf(100))
                .extent(BigDecimal.valueOf(100))
                .extentType(ExtentType.GR)
                .categoryId(1L)
                .build();


        //Then


        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productUpdateRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify

        Mockito.verify(productService, Mockito.times(1))
                .update(Mockito.any(UUID.class), Mockito.any(ProductUpdateCommand.class));

    }

    @Test
    void givenDeleteProduct_whenProductExist_thenReturnSuccess() throws Exception {
        //given
        UUID productId = UUID.randomUUID();

        //then

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/{id}", productId))
                .andExpect(MockMvcResultMatchers.status().isOk());
        //verify

        Mockito.verify(productService, Mockito.times(1)).delete(productId);
    }

    @Test
    void givenDeleteProductId_whenInvalidStringInput_thenReturnBadRequest() throws Exception {
        //given
        String productId = "1234565scdf";

        //then
        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/{id}", productId))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    void givenDeleteProductId_whenInvalidIntegerInput_thenReturnBadRequest() throws Exception {
        //given
        Integer productId = 12345213;

        //then
        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/{id}", productId))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

}