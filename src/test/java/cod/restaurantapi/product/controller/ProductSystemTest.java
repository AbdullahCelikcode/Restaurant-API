package cod.restaurantapi.product.controller;

import cod.restaurantapi.RMASystemTest;
import cod.restaurantapi.category.controller.exceptions.CategoryNotFoundException;
import cod.restaurantapi.common.model.Pagination;
import cod.restaurantapi.common.model.Sorting;
import cod.restaurantapi.product.controller.request.ProductAddRequest;
import cod.restaurantapi.product.controller.request.ProductListRequest;
import cod.restaurantapi.product.controller.request.ProductUpdateRequest;
import cod.restaurantapi.product.model.enums.ExtentType;
import cod.restaurantapi.product.model.enums.ProductStatus;
import cod.restaurantapi.product.repository.ProductTestRepository;
import cod.restaurantapi.product.repository.entity.ProductEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

class ProductSystemTest extends RMASystemTest {

    @Autowired
    protected ProductTestRepository productTestRepository;

    private final static String BASE_URL = "/api/v1/product";

    @Test
    void testAddProduct() throws Exception {
        ProductAddRequest productAddRequest = ProductAddRequest.builder()
                .name("ProductTest")
                .ingredient("test")
                .price(BigDecimal.valueOf(100))
                .extent(BigDecimal.valueOf(100))
                .extentType(ExtentType.GR)
                .categoryId(1l)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productAddRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertTrue(productTestRepository.existsByName(productAddRequest.getName()));


    }

    @Test
    void testGetProductById() throws Exception {

        UUID productId = UUID.fromString("7fa260e0-8391-4a1f-86ab-f20a4a3808c9");

        ProductEntity productEntity = productTestRepository.findById(productId)
                .orElseThrow(CategoryNotFoundException::new);

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/{id}", productId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(productEntity.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.name").value(productEntity.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.status").value(productEntity.getStatus()
                        .toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.ingredient").value(productEntity
                        .getIngredient()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.extentType").value(productEntity.
                        getExtentType().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.categoryId").value(productEntity
                        .getCategoryId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"));
    }

    @Test
    void testUpdateProductById() throws Exception {

        UUID productId = UUID.fromString("7fa260e0-8391-4a1f-86ab-f20a4a3808c9");

        ProductUpdateRequest productUpdateRequest = ProductUpdateRequest.builder()
                .name("test")
                .ingredient("test")
                .status(ProductStatus.INACTIVE)
                .price(BigDecimal.valueOf(100))
                .extent(BigDecimal.valueOf(100))
                .extentType(ExtentType.GR)
                .categoryId(1L)
                .build();


        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productUpdateRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(productId.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.name").value(productUpdateRequest.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.status").value(productUpdateRequest.getStatus()
                        .toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.updatedAt").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"));
    }

    @Test
    void testDeleteProductCategoryById() throws Exception {

        UUID productId = UUID.fromString("7fa260e0-8391-4a1f-86ab-f20a4a3808c9");

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/{id}", productId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        ProductEntity productEntity = productTestRepository.findById(productId)
                .orElseThrow(CategoryNotFoundException::new);
        Assertions.assertEquals(ProductStatus.DELETED, productEntity.getStatus());

    }

    @Test
    void testGetProductListWithFilter() throws Exception {
        ProductListRequest.ProductFilter filter = ProductListRequest.ProductFilter.builder()
                .name("p")
                .statuses(Set.of(ProductStatus.ACTIVE))
                .categoryId(1L)
                .minPrice(1)
                .maxPrice(150)
                .build();

        ProductListRequest productListRequest = ProductListRequest.builder()
                .pagination(Pagination.builder().pageSize(3).pageNumber(1).build())
                .sorting(Sorting.builder().property("price").direction(Sort.Direction.ASC).build())
                .filter(filter)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productListRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageNumber")
                        .value(productListRequest.getPagination().getPageNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageSize")
                        .value(productListRequest.getPagination().getPageSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.filteredBy.name")
                        .value(productListRequest.getFilter().getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"));

    }

    @Test
    void testGetProductListWithoutFilter() throws Exception {

        ProductListRequest productListRequest = ProductListRequest.builder()
                .pagination(Pagination.builder().pageSize(3).pageNumber(1).build())
                .sorting(Sorting.builder().property("price").direction(Sort.Direction.ASC).build())
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productListRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageNumber")
                        .value(productListRequest.getPagination().getPageNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageSize")
                        .value(productListRequest.getPagination().getPageSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"));

    }

    @Test
    void testGetProductListWithoutFilterAndSorting() throws Exception {

        ProductListRequest productListRequest = ProductListRequest.builder()
                .pagination(Pagination.builder().pageSize(3).pageNumber(1).build())
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productListRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageNumber")
                        .value(productListRequest.getPagination().getPageNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageSize")
                        .value(productListRequest.getPagination().getPageSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"));

    }

}
