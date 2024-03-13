package cod.restaurantapi.product.controller;

import cod.restaurantapi.product.model.enums.CategoryStatus;
import cod.restaurantapi.product.service.CategoryService;
import cod.restaurantapi.product.service.command.CategoryCreateCommand;
import cod.restaurantapi.product.service.domain.Category;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@WebMvcTest(controllers = CategoryController.class)
class CategoryControllerTest {

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    private final static String BASE_URL = "/api/v1/category";

    @Test
    void givenCreateCategory_thenReturnSuccess() throws Exception {
        // Given
        CategoryCreateCommand categoryCreateCommand = CategoryCreateCommand.builder()
                .name("TestCategory")
                .build();

        // When
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryCreateCommand)))
                .andExpect(MockMvcResultMatchers.status().isOk());


        // Then

        Mockito.verify(categoryService, Mockito.times(1)).save(Mockito.any(CategoryCreateCommand.class));

    }

    @Test
    void givenGetCategoryById_whenValidInput_thenReturnSuccess() throws Exception {
        // Given

        Long categoryId = 1L;
        Category category = Category.builder()
                .id(categoryId)
                .name("TestCategory")
                .status(CategoryStatus.ACTIVE)
                .build();


        // When

        Mockito.when(categoryService.findById(categoryId)).thenReturn(category);

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

}