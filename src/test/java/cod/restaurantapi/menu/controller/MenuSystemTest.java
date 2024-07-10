package cod.restaurantapi.menu.controller;

import cod.restaurantapi.RMASystemTest;
import cod.restaurantapi.common.model.Pagination;
import cod.restaurantapi.common.model.Sorting;
import cod.restaurantapi.menu.controller.request.MenuRequest;
import cod.restaurantapi.product.repository.ProductTestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class MenuSystemTest extends RMASystemTest {

    @Autowired
    protected ProductTestRepository productTestRepository;

    private static final String BASE_URL = "/api/v1/menu";

    @Test
    void givenGetProductListWithFilter_thenReturnProductList() throws Exception {
        MenuRequest.MenuFilter filter = MenuRequest.MenuFilter.builder()
                .name("p")
                .build();

        MenuRequest menuRequest = MenuRequest.builder()
                .pagination(Pagination.builder()
                        .pageSize(3)
                        .pageNumber(1)
                        .build())
                .sorting(Sorting.builder()
                        .property("id")
                        .direction(Sort.Direction.ASC)
                        .build())
                .filter(filter)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(menuRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageNumber")
                        .value(menuRequest.getPagination().getPageNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageSize")
                        .value(menuRequest.getPagination().getPageSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.filteredBy.name")
                        .value(menuRequest.getFilter().getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"));

    }

    @Test
    void givenGetProductListWithOutFilter_thenReturnProductList() throws Exception {


        MenuRequest menuRequest = MenuRequest.builder()
                .pagination(Pagination.builder()
                        .pageSize(3)
                        .pageNumber(1)
                        .build())
                .sorting(Sorting.builder()
                        .property("id")
                        .direction(Sort.Direction.ASC)
                        .build())
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(menuRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageNumber")
                        .value(menuRequest.getPagination().getPageNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageSize")
                        .value(menuRequest.getPagination().getPageSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"));

    }

    @Test
    void givenGetProductListWithOutFilterAndSorting_thenReturnProductList() throws Exception {


        MenuRequest menuRequest = MenuRequest.builder()
                .pagination(Pagination.builder()
                        .pageSize(3)
                        .pageNumber(1)
                        .build())
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(menuRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageNumber")
                        .value(menuRequest.getPagination().getPageNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageSize")
                        .value(menuRequest.getPagination().getPageSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"));

    }

}
