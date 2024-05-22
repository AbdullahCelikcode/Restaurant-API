package cod.restaurantapi.category.controller;

import cod.restaurantapi.RMASystemTest;
import cod.restaurantapi.category.controller.exceptions.CategoryNotFoundException;
import cod.restaurantapi.category.controller.request.CategoryAddRequest;
import cod.restaurantapi.category.controller.request.CategoryListRequest;
import cod.restaurantapi.category.controller.request.CategoryUpdateRequest;
import cod.restaurantapi.category.model.enums.CategoryStatus;
import cod.restaurantapi.category.repository.CategoryTestRepository;
import cod.restaurantapi.category.repository.entity.CategoryEntity;
import cod.restaurantapi.common.model.Pagination;
import cod.restaurantapi.common.model.Sorting;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


class CategorySystemTest extends RMASystemTest {

    @Autowired
    protected CategoryTestRepository categoryTestRepository;

    private final static String BASE_URL = "/api/v1/category";


    @Test
    void testAddCategory() throws Exception {
        CategoryAddRequest categoryAddRequest = CategoryAddRequest.builder()
                .name("CategoryTest")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryAddRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertTrue(categoryTestRepository.existsByName(categoryAddRequest.getName()));


    }

    @Test
    void testGetCategoryById() throws Exception {

        long categoryId = 1L;

        CategoryEntity category = categoryTestRepository.findById(categoryId)
                .orElseThrow(CategoryNotFoundException::new);

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/{id}", categoryId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(category.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.name").value(category.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.status").value(category.getStatus()
                        .toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"));


    }

    @Test
    void testUpdateCategoryById() throws Exception {
        Long categoryId = 1L;

        CategoryUpdateRequest categoryUpdateRequest = new CategoryUpdateRequest();
        categoryUpdateRequest.setName("UpdateTest");
        categoryUpdateRequest.setStatus(CategoryStatus.INACTIVE);


        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/{id}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryUpdateRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(categoryId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.name").value(categoryUpdateRequest.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.status").value(categoryUpdateRequest.getStatus()
                        .toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.updatedAt").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"));


    }

    @Test
    void testDeleteCategoryById() throws Exception {

        Long categoryId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/{id}", categoryId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        CategoryEntity categoryEntity = categoryTestRepository.findById(categoryId)
                .orElseThrow(CategoryNotFoundException::new);
        Assertions.assertEquals(CategoryStatus.DELETED, categoryEntity.getStatus());

    }


    @Test
    void testGetCategoryListWithFilter() throws Exception {

        CategoryListRequest categoryListRequest = CategoryListRequest.builder()
                .pagination(Pagination.builder()
                        .pageSize(3)
                        .pageNumber(1)
                        .build())
                .sorting(Sorting.builder()
                        .property("name")
                        .direction(Sort.Direction.ASC)
                        .build())
                .filter(CategoryListRequest.CategoryFilter.builder()
                        .name("e")
                        .build())
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryListRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageNumber")
                        .value(categoryListRequest.getPagination().getPageNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageSize")
                        .value(categoryListRequest.getPagination().getPageSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.filteredBy.name")
                        .value(categoryListRequest.getFilter().getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content.size()").
                        value(categoryListRequest.getPagination().getPageSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"));

    }

    @Test
    void testGetCategoryListWithoutFilter() throws Exception {
        CategoryListRequest categoryListRequest = CategoryListRequest.builder()
                .pagination(Pagination.builder()
                        .pageSize(3)
                        .pageNumber(1)
                        .build())
                .sorting(Sorting.builder()
                        .direction(Sort.Direction.ASC)
                        .property("name")
                        .build())
                .build();
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryListRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageNumber")
                        .value(categoryListRequest.getPagination().getPageNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageSize")
                        .value(categoryListRequest.getPagination().getPageSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content.size()").
                        value(categoryListRequest.getPagination().getPageSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"));

    }

}