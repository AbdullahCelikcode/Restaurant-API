package cod.restaurantapi.category.controller;

import cod.restaurantapi.RMAControllerTest;
import cod.restaurantapi.category.controller.request.CategoryAddRequest;
import cod.restaurantapi.category.controller.request.CategoryListRequest;
import cod.restaurantapi.category.controller.request.CategoryUpdateRequest;
import cod.restaurantapi.category.model.enums.CategoryStatus;
import cod.restaurantapi.category.service.CategoryService;
import cod.restaurantapi.category.service.command.CategoryCreateCommand;
import cod.restaurantapi.category.service.command.CategoryListCommand;
import cod.restaurantapi.category.service.command.CategoryUpdateCommand;
import cod.restaurantapi.category.service.domain.Category;
import cod.restaurantapi.common.model.Pagination;
import cod.restaurantapi.common.model.RMAPageResponse;
import cod.restaurantapi.common.model.Sorting;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = CategoryController.class)
class CategoryControllerTest extends RMAControllerTest {

    @MockBean
    protected CategoryService categoryService;

    private final static String BASE_URL = "/api/v1/category";

    @Test
    void givenCreateCategory_whenValidInput_thenReturnSuccess() throws Exception {
        // Given
        CategoryAddRequest categoryAddRequest = CategoryAddRequest.builder()
                .name("TestCategory")
                .build();

        // Then
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryAddRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk());


        // Verify

        Mockito.verify(categoryService, Mockito.times(1))
                .save(Mockito.any(CategoryCreateCommand.class));

    }

    @Test
    void givenCreateCategory_whenInvalidsMinSizeInput_thenReturnBadRequest() throws Exception {

        // Given

        CategoryAddRequest categoryAddRequest = CategoryAddRequest.builder()
                .name("aa")
                .build();

        // Then

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryAddRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void givenCreateCategory_whenInvalidsMaxSizeInput_thenReturnBadRequest() throws Exception {

        // Given

        CategoryAddRequest categoryAddRequest = CategoryAddRequest.builder()
                .name("\"T5lgsg7SY6315kjJT0F1HpNQoasxpAZgmZ9cIwO9jiPZEVAPZUXm8bajgDOtoc9aPZHyeqdx1UZpDMWq7Uy6J" +
                        " \"Cgz1aHPSkyt6QKpr988mtJG7NQ4eXhKCaChjnVcKtT6DQdyD14yJtgCvaBKG6qfpNsoFYhg" +
                        "iEnR4GkTA1aMIyq1k57X8QyTxjhek1Cm" +
                        " r7ZbyA7bVRxth4ZWHFS4r4w2v46wUn9QxGuqZCQn5Lpno0gnb1uCQHT3LPIdCdAmrak0")
                .build();

        // Then

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryAddRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void givenCreateCategory_whenInvalidInput_thenReturnBadRequest() throws Exception {

        //Given

        CategoryAddRequest categoryAddRequest = CategoryAddRequest.builder().build();


        //Then

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryAddRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    void givenCategoryListRequest_whenValidInput_thenReturnResponse() throws Exception {

        // given
        CategoryListRequest givenRequest = CategoryListRequest.builder()
                .filter(CategoryListRequest.CategoryFilter.builder()
                        .name("Test")
                        .build())
                .sorting(Sorting.builder()
                        .direction(Sort.Direction.ASC)
                        .property("name")
                        .build())
                .pagination(Pagination.builder()
                        .pageSize(1)
                        .pageNumber(1)
                        .build())
                .build();


        // then

        List<Category> entityList = new ArrayList<>();


        entityList.add(Category.builder()
                .name("category1")
                .status(CategoryStatus.ACTIVE)
                .id(1L)
                .build());
        entityList.add(Category.builder()
                .name("category2")
                .status(CategoryStatus.ACTIVE)
                .id(2L)
                .build());
        entityList.add(Category.builder()
                .name("category3")
                .status(CategoryStatus.ACTIVE)
                .id(3L)
                .build());

        CategoryListCommand.CategoryFilter filter = CategoryListCommand.CategoryFilter
                .builder()
                .name("Test")
                .build();

        RMAPageResponse<Category> categoryList = RMAPageResponse.<Category>builder()
                .content(entityList)
                .pageNumber(1)
                .totalPageCount(1)
                .totalElementCount(3L)
                .pageSize(3)
                .sortedBy(givenRequest.getSorting())
                .filteredBy(filter)
                .build();

        Mockito.when(categoryService.findAll(Mockito.any(CategoryListCommand.class)))
                .thenReturn(categoryList);


        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(givenRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageNumber")
                        .value(categoryList.getPageNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageSize")
                        .value(categoryList.getPageSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPageCount")
                        .value(categoryList.getTotalPageCount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.filteredBy.name")
                        .value(filter.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content.size()").
                        value(categoryList.getContent().size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"));

    }

    @Test
    void givenCategoryListRequestWithOutFilter_whenValidInput_thenReturnResponse() throws Exception {

        // given
        CategoryListRequest givenRequest = CategoryListRequest.builder()
                .pagination(Pagination.builder()
                        .pageSize(1)
                        .pageNumber(2)
                        .build())
                .sorting(Sorting.builder()
                        .direction(Sort.Direction.ASC)
                        .property("name")
                        .build())
                .build();

        // then


        List<Category> entityList = new ArrayList<>();

        entityList.add(Category.builder()
                .name("category1")
                .status(CategoryStatus.ACTIVE)
                .id(1L)
                .build());
        entityList.add(Category.builder()
                .name("category2")
                .status(CategoryStatus.ACTIVE)
                .id(2L)
                .build());
        entityList.add(Category.builder()
                .name("category3")
                .status(CategoryStatus.ACTIVE)
                .id(3L)
                .build());

        CategoryListCommand.CategoryFilter filter = CategoryListCommand.CategoryFilter
                .builder()
                .name("Test")
                .build();

        RMAPageResponse<Category> categoryList = RMAPageResponse.<Category>builder()
                .content(entityList)
                .pageNumber(1)
                .totalPageCount(1)
                .totalElementCount(3L)
                .filteredBy(filter)
                .build();


        Mockito.when(categoryService.findAll(Mockito.any(CategoryListCommand.class)))
                .thenReturn(categoryList);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(givenRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageNumber")
                        .value(categoryList.getPageNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageSize")
                        .value(categoryList.getPageSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPageCount")
                        .value(categoryList.getTotalPageCount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.filteredBy.name")
                        .value(filter.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.totalElementCount").
                        value(categoryList.getTotalElementCount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content.size()").
                        value(categoryList.getContent().size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"));
    }

    @Test
    void givenCategoryListRequestWithOutFilterName_whenValidInput_thenReturnResponse() throws Exception {

        // given
        CategoryListRequest givenRequest = CategoryListRequest.builder()
                .pagination(Pagination.builder()
                        .pageSize(1)
                        .pageNumber(2)
                        .build())
                .sorting(Sorting.builder()
                        .direction(Sort.Direction.ASC)
                        .property("name")
                        .build())
                .filter(CategoryListRequest.CategoryFilter.builder().build())
                .build();

        // then


        List<Category> entityList = new ArrayList<>();

        entityList.add(Category.builder()
                .name("category1")
                .status(CategoryStatus.ACTIVE)
                .id(1L).build());
        entityList.add(Category
                .builder()
                .name("category2")
                .status(CategoryStatus.ACTIVE)
                .id(2L).build());
        entityList.add(Category.builder()
                .name("category3")
                .status(CategoryStatus.ACTIVE)
                .id(3L)
                .build());


        RMAPageResponse<Category> categoryList = RMAPageResponse.<Category>builder()
                .content(entityList)
                .pageNumber(1)
                .totalPageCount(1)
                .totalElementCount(3L)
                .build();

        Mockito.when(categoryService.findAll(Mockito.any(CategoryListCommand.class))).thenReturn(categoryList);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(givenRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageNumber")
                        .value(categoryList.getPageNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageSize")
                        .value(categoryList.getPageSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPageCount")
                        .value(categoryList.getTotalPageCount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.filteredBy.name")
                        .doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.totalElementCount").
                        value(categoryList.getTotalElementCount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content.size()").
                        value(categoryList.getContent().size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"));

    }

    @Test
    void givenCategoryListRequestWithOutSorting_whenValidInput_thenReturnResponse() throws Exception {

        // given
        CategoryListRequest givenRequest = CategoryListRequest.builder()
                .pagination(Pagination.builder()
                        .pageSize(3)
                        .pageNumber(1)
                        .build())
                .filter(CategoryListRequest.CategoryFilter.builder().build())
                .build();

        // then
        List<Category> entityList = new ArrayList<>();

        entityList.add(Category.builder()
                .name("category1")
                .status(CategoryStatus.ACTIVE).
                id(1L)
                .build());
        entityList.add(Category.builder()
                .name("category2")
                .status(CategoryStatus.ACTIVE)
                .id(2L)
                .build());
        entityList.add(Category.builder()
                .name("category3")
                .status(CategoryStatus.ACTIVE)
                .id(3L)
                .build());

        RMAPageResponse categoryList = RMAPageResponse.<Category>builder()
                .content(entityList)
                .pageNumber(1)
                .totalPageCount(1)
                .totalElementCount(3L)
                .filteredBy(CategoryListCommand.CategoryFilter.builder().build())
                .build();


        Mockito.when(categoryService.findAll(Mockito.any(CategoryListCommand.class))).thenReturn(categoryList);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(givenRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageNumber")
                        .value(categoryList.getPageNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageSize")
                        .value(categoryList.getPageSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPageCount")
                        .value(categoryList.getTotalPageCount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.filteredBy.name")
                        .doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.totalElementCount").
                        value(categoryList.getTotalElementCount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content.size()").
                        value(categoryList.getContent().size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"));

    }

    @Test
    void givenCategoryListRequest_whenInValidInput_thenReturnBadRequest() throws Exception {

        // given

        CategoryListRequest givenRequest = CategoryListRequest.builder()
                .filter(CategoryListRequest.CategoryFilter.builder().build())
                .build();

        // then

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(givenRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void givenCategoryListRequest_whenInValidNegativePageSizeInput_thenReturnBadRequest() throws Exception {

        // given
        CategoryListRequest givenRequest = CategoryListRequest.builder()
                .pagination(Pagination.builder()
                        .pageSize(-1)
                        .pageNumber(1)
                        .build())
                .filter(CategoryListRequest.CategoryFilter.builder().build())
                .build();

        // then

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(givenRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void givenCategoryListRequest_whenInValidNegativePageNumberInput_thenReturnBadRequest() throws Exception {

        // given
        CategoryListRequest givenRequest = CategoryListRequest.builder()
                .pagination(Pagination.builder()
                        .pageSize(1)
                        .pageNumber(-1).build())
                .filter(CategoryListRequest.CategoryFilter.builder().build())
                .build();

        // then

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(givenRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    void givenGetCategoryById_whenValidInput_thenReturnSuccess() throws Exception {

        // Given

        Long categoryId = 1L;
        Category category = Category.builder()
                .id(categoryId)
                .name("TestCategory")
                .status(CategoryStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();


        // When

        Mockito.when(categoryService.findById(categoryId))
                .thenReturn(category);

        // Then

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/{id}", categoryId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(category.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.name").value(category.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.status").value(category.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"));
    }

    @Test
    void givenGetCategoryById_whenInvalidNegativeInput_thenReturnBadRequest() throws Exception {

        //Given

        Long categoryId = -1L;

        // Then

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/{id}", categoryId))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void givenGetCategoryById_whenInvalidStringInput_thenReturnBadRequest() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/{id}", "abc"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void givenGetCategoryById_whenInvalidBigInput_thenReturnBadRequest() throws Exception {

        //Given

        Long categoryId = 9999L;

        // Then

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/{id}", categoryId))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void givenUpdateCategory_whenValidInput_thenReturnSuccess() throws Exception {

        //Given

        Long categoryId = 1L;
        CategoryUpdateRequest categoryUpdateRequest = new CategoryUpdateRequest();
        categoryUpdateRequest.setName("TestCategory");
        categoryUpdateRequest.setStatus(CategoryStatus.INACTIVE);




        //Then


        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/{id}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryUpdateRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk());


        // Verify

        Mockito.verify(categoryService, Mockito.times(1))
                .update(Mockito.any(Long.class), Mockito.any(CategoryUpdateCommand.class));

    }

    @Test
    void givenDeleteCategory_whenValidInput_thenReturnSuccess() throws Exception {

        //Given

        Long categoryId = 1L;

        //Then

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/{id}", categoryId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify

        Mockito.verify(categoryService, Mockito.times(1)).delete(categoryId);

    }

    @Test
    void givenDeleteCategory_whenInvalidNegativeInput_thenReturnBadRequest() throws Exception {

        //Given

        Long categoryId = -1L;

        //Then

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/{id}", categoryId))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    void givenDeleteCategory_whenInvalidStringInput_thenReturnBadRequest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/{id}", "abc"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void givenDeleteCategory_whenInvalidBigInput_thenReturnBadRequest() throws Exception {

        //Given

        Long categoryId = 9999L;

        //Then

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/{id}", categoryId))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


}