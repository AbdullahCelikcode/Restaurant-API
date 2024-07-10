package cod.restaurantapi.menu.controller;

import cod.restaurantapi.RMAControllerTest;
import cod.restaurantapi.common.model.Pagination;
import cod.restaurantapi.common.model.RMAPageResponse;
import cod.restaurantapi.common.model.Sorting;
import cod.restaurantapi.menu.controller.request.MenuRequest;
import cod.restaurantapi.menu.service.MenuDTO;
import cod.restaurantapi.menu.service.MenuService;
import cod.restaurantapi.menu.service.command.MenuListCommand;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = MenuController.class)
public class MenuControllerTest extends RMAControllerTest {
    @MockBean
    protected MenuService menuService;

    private static final String BASE_URL = "/api/v1/menu";

    @Test
    void givenMenuRequest_whenValidInput_ThenReturnMenuResponse() throws Exception {

        //given
        Pagination pagination = Pagination.builder()
                .pageNumber(1)
                .pageSize(3)
                .build();
        MenuRequest.MenuFilter menuFilter = MenuRequest.MenuFilter.builder()
                .name("Test")
                .build();
        Sorting sorting = Sorting.builder()
                .property("id")
                .direction(Sort.Direction.ASC)
                .build();
        MenuRequest menuRequest = MenuRequest.builder()
                .pagination(pagination)
                .filter(menuFilter)
                .sorting(sorting)
                .build();
        //when

        List<MenuDTO> menuDTOS = new ArrayList<>();

        menuDTOS.add(
                MenuDTO.builder()
                        .category(MenuDTO.Category.builder().build())
                        .product(MenuDTO.Product.builder().build())
                        .build()
        );
        menuDTOS.add(
                MenuDTO.builder()
                        .category(MenuDTO.Category.builder().build())
                        .product(MenuDTO.Product.builder().build())
                        .build()
        );
        menuDTOS.add(
                MenuDTO.builder()
                        .category(MenuDTO.Category.builder().build())
                        .product(MenuDTO.Product.builder().build())
                        .build()
        );
        RMAPageResponse<MenuDTO> menuDTORMAPageResponse = RMAPageResponse.<MenuDTO>builder()
                .content(menuDTOS)
                .pageNumber(pagination.getPageNumber())
                .pageSize(pagination.getPageSize())
                .totalPageCount(menuDTOS.size())
                .totalElementCount((long) menuDTOS.size())
                .sortedBy(sorting)
                .filteredBy(menuFilter)
                .build();

        Mockito.when(menuService.getMenu(Mockito.any(MenuListCommand.class)))
                .thenReturn(menuDTORMAPageResponse);

        //then

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(menuRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageNumber")
                        .value(menuDTORMAPageResponse.getPageNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageSize")
                        .value(menuDTORMAPageResponse.getPageSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPageCount")
                        .value(menuDTORMAPageResponse.getTotalPageCount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.filteredBy.name")
                        .value(menuFilter.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"));
    }

    @Test
    void givenMenuRequest_whenWithOutFilter_ThenReturnMenuResponse() throws Exception {

        //given
        Pagination pagination = Pagination.builder()
                .pageNumber(1)
                .pageSize(3)
                .build();
        Sorting sorting = Sorting.builder()
                .property("id")
                .direction(Sort.Direction.ASC)
                .build();
        MenuRequest menuRequest = MenuRequest.builder()
                .pagination(pagination)
                .sorting(sorting)
                .build();
        //when

        List<MenuDTO> menuDTOS = new ArrayList<>();

        menuDTOS.add(
                MenuDTO.builder()
                        .category(MenuDTO.Category.builder().build())
                        .product(MenuDTO.Product.builder().build())
                        .build()
        );
        menuDTOS.add(
                MenuDTO.builder()
                        .category(MenuDTO.Category.builder().build())
                        .product(MenuDTO.Product.builder().build())
                        .build()
        );
        menuDTOS.add(
                MenuDTO.builder()
                        .category(MenuDTO.Category.builder().build())
                        .product(MenuDTO.Product.builder().build())
                        .build()
        );
        RMAPageResponse<MenuDTO> menuDTORMAPageResponse = RMAPageResponse.<MenuDTO>builder()
                .content(menuDTOS)
                .pageNumber(pagination.getPageNumber())
                .pageSize(pagination.getPageSize())
                .totalPageCount(menuDTOS.size())
                .totalElementCount((long) menuDTOS.size())
                .sortedBy(sorting)
                .build();

        Mockito.when(menuService.getMenu(Mockito.any(MenuListCommand.class)))
                .thenReturn(menuDTORMAPageResponse);

        //then

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(menuRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageNumber")
                        .value(menuDTORMAPageResponse.getPageNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageSize")
                        .value(menuDTORMAPageResponse.getPageSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPageCount")
                        .value(menuDTORMAPageResponse.getTotalPageCount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"));
    }

    @Test
    void givenMenuRequest_whenWithOutSorting_ThenReturnMenuResponse() throws Exception {

        //given
        Pagination pagination = Pagination.builder()
                .pageNumber(1)
                .pageSize(3)
                .build();
        MenuRequest.MenuFilter menuFilter = MenuRequest.MenuFilter.builder()
                .name("Test")
                .build();
        MenuRequest menuRequest = MenuRequest.builder()
                .pagination(pagination)
                .filter(menuFilter)
                .build();
        //when

        List<MenuDTO> menuDTOS = new ArrayList<>();

        menuDTOS.add(
                MenuDTO.builder()
                        .category(MenuDTO.Category.builder().build())
                        .product(MenuDTO.Product.builder().build())
                        .build()
        );
        menuDTOS.add(
                MenuDTO.builder()
                        .category(MenuDTO.Category.builder().build())
                        .product(MenuDTO.Product.builder().build())
                        .build()
        );
        menuDTOS.add(
                MenuDTO.builder()
                        .category(MenuDTO.Category.builder().build())
                        .product(MenuDTO.Product.builder().build())
                        .build()
        );
        RMAPageResponse<MenuDTO> menuDTORMAPageResponse = RMAPageResponse.<MenuDTO>builder()
                .content(menuDTOS)
                .pageNumber(pagination.getPageNumber())
                .pageSize(pagination.getPageSize())
                .totalPageCount(menuDTOS.size())
                .totalElementCount((long) menuDTOS.size())
                .filteredBy(menuFilter)
                .build();

        Mockito.when(menuService.getMenu(Mockito.any(MenuListCommand.class)))
                .thenReturn(menuDTORMAPageResponse);

        //then

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(menuRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageNumber")
                        .value(menuDTORMAPageResponse.getPageNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageSize")
                        .value(menuDTORMAPageResponse.getPageSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPageCount")
                        .value(menuDTORMAPageResponse.getTotalPageCount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.filteredBy.name")
                        .value(menuFilter.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"));
    }

    @Test
    void givenMenuRequest_whenWithOutSortingAndFilter_ThenReturnMenuResponse() throws Exception {

        //given
        Pagination pagination = Pagination.builder()
                .pageNumber(1)
                .pageSize(3)
                .build();
        MenuRequest menuRequest = MenuRequest.builder()
                .pagination(pagination)
                .build();
        //when

        List<MenuDTO> menuDTOS = new ArrayList<>();

        menuDTOS.add(
                MenuDTO.builder()
                        .category(MenuDTO.Category.builder().build())
                        .product(MenuDTO.Product.builder().build())
                        .build()
        );
        menuDTOS.add(
                MenuDTO.builder()
                        .category(MenuDTO.Category.builder().build())
                        .product(MenuDTO.Product.builder().build())
                        .build()
        );
        menuDTOS.add(
                MenuDTO.builder()
                        .category(MenuDTO.Category.builder().build())
                        .product(MenuDTO.Product.builder().build())
                        .build()
        );
        RMAPageResponse<MenuDTO> menuDTORMAPageResponse = RMAPageResponse.<MenuDTO>builder()
                .content(menuDTOS)
                .pageNumber(pagination.getPageNumber())
                .pageSize(pagination.getPageSize())
                .totalPageCount(menuDTOS.size())
                .totalElementCount((long) menuDTOS.size())
                .build();

        Mockito.when(menuService.getMenu(Mockito.any(MenuListCommand.class)))
                .thenReturn(menuDTORMAPageResponse);

        //then

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(menuRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageNumber")
                        .value(menuDTORMAPageResponse.getPageNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageSize")
                        .value(menuDTORMAPageResponse.getPageSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPageCount")
                        .value(menuDTORMAPageResponse.getTotalPageCount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"));
    }


}
