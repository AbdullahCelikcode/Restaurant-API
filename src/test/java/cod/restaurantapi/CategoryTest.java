package cod.restaurantapi;

import cod.restaurantapi.category.controller.request.CategoryAddRequest;
import cod.restaurantapi.category.controller.request.CategoryListRequest;
import cod.restaurantapi.category.controller.request.CategoryUpdateRequest;
import cod.restaurantapi.category.controller.response.CategoryResponse;
import cod.restaurantapi.category.model.enums.CategoryStatus;
import cod.restaurantapi.category.repository.entity.CategoryEntity;
import cod.restaurantapi.common.BaseResponse;
import cod.restaurantapi.common.util.Pagination;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class CategoryTest {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static TestRestTemplate restTemplate;


    @Autowired
    private CategoryTestRepository categoryRepository;


    @BeforeAll
    public static void init() {
        restTemplate = new TestRestTemplate();

    }


    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/api/v1/category");
    }


    @Test
    @Sql(value = {"/db/schema.sql", "/db/categoryData.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DROP TABLE IF EXISTS rma_category", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testAddCategory() {
        CategoryAddRequest categoryAddRequest = CategoryAddRequest.builder().name("H2CategoryTest").build();

        BaseResponse<CategoryResponse> response = restTemplate.postForObject(baseUrl, categoryAddRequest,
                BaseResponse.class);


        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getHttpStatus());
        Assertions.assertEquals(true, response.getIsSuccess());
        Assertions.assertEquals(true, categoryRepository.existsByName(categoryAddRequest.getName()));


    }

    @Test
    @Sql(value = {"/db/schema.sql", "/db/categoryData.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DROP TABLE IF EXISTS rma_category", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testGetCategoryById() {

        //  name= 'Desserts', status= 'ACTIVE'

        long categoryId = 2L;

        BaseResponse response = restTemplate.getForObject(baseUrl + "/{id}",
                BaseResponse.class, categoryId);

        HashMap<Object, Object> responseBody = (HashMap<Object, Object>) response.getResponse();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getHttpStatus());
        Assertions.assertEquals(true, response.getIsSuccess());
        Assertions.assertEquals("Desserts", responseBody.get("name"));
        Assertions.assertEquals(CategoryStatus.ACTIVE.toString(), responseBody.get("status").toString());
        Assertions.assertEquals(Long.toString(categoryId), responseBody.get("id").toString());


    }

    @Test
    @Sql(value = {"/db/schema.sql", "/db/categoryData.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DROP TABLE IF EXISTS rma_category", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testUpdateCategoryById() {
        Long categoryId = 1L;

        CategoryUpdateRequest categoryUpdateRequest = new CategoryUpdateRequest();
        categoryUpdateRequest.setName("UpdateTest");
        categoryUpdateRequest.setStatus(CategoryStatus.INACTIVE);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<CategoryUpdateRequest> requestEntity = new HttpEntity<>(categoryUpdateRequest, headers);

        ResponseEntity<BaseResponse> response = restTemplate.exchange(
                baseUrl + "/1",
                HttpMethod.PUT,
                requestEntity,
                BaseResponse.class);

        HashMap<Object, Object> responseBody = (HashMap<Object, Object>) response.getBody().getResponse();

        Assertions.assertEquals(Math.toIntExact(categoryId), responseBody.get("id"));
        Assertions.assertEquals(categoryUpdateRequest.getName(), responseBody.get("name"));
        Assertions.assertNotNull(responseBody.get("updatedAt"));

    }

    @Test
    @Sql(value = {"/db/schema.sql", "/db/categoryData.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DROP TABLE IF EXISTS rma_category", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testDeleteCategoryById() {

        Long categoryId = 1L;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CategoryUpdateRequest> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<BaseResponse> response = restTemplate.exchange(
                baseUrl + "/" + categoryId.toString(),
                HttpMethod.DELETE,
                requestEntity,
                BaseResponse.class);

        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElseThrow(ClassCastException::new);

        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getBody().getHttpStatus());
        Assertions.assertTrue(response.getBody().getIsSuccess());
        Assertions.assertEquals(CategoryStatus.INACTIVE, categoryEntity.getStatus());


    }


    @Test
    @Sql(value = {"/db/schema.sql", "/db/categoryData.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DROP TABLE IF EXISTS rma_category", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testGetCategoryListWithFilter() {
        CategoryListRequest categoryListRequest = CategoryListRequest.builder()
                .pagination(Pagination.builder().pageSize(3).pageNumber(1).build())
                .filter(CategoryListRequest.CategoryFilter.builder().name("e").build())
                .build();


        ResponseEntity<BaseResponse> response = restTemplate.postForEntity(baseUrl + "/all",
                categoryListRequest, BaseResponse.class);

        HashMap<Object, Object> responseBody = (HashMap<Object, Object>) response.getBody().getResponse();
        List<LinkedHashMap<Object, Object>> categoryList = (List<LinkedHashMap<Object, Object>>)
                responseBody.get("categoryList");

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getBody().getHttpStatus());
        Assertions.assertEquals(true, response.getBody().getIsSuccess());
        Assertions.assertEquals(categoryListRequest.getPagination().getPageSize(), categoryList.size());
        Assertions.assertFalse(categoryList.stream().noneMatch(map -> map.get("name").toString().contains("e")));

    }

    @Test
    @Sql(value = {"/db/schema.sql", "/db/categoryData.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DROP TABLE IF EXISTS rma_category", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testGetCategoryListWithoutFilter() {
        CategoryListRequest categoryListRequest = CategoryListRequest.builder()
                .pagination(Pagination.builder().pageSize(3).pageNumber(1).build())

                .build();


        ResponseEntity<BaseResponse> response = restTemplate.postForEntity(baseUrl + "/all",
                categoryListRequest, BaseResponse.class);

        HashMap<Object, Object> responseBody = (HashMap<Object, Object>) response.getBody().getResponse();
        List<LinkedHashMap<Object, Object>> categoryList = (List<LinkedHashMap<Object, Object>>)
                responseBody.get("categoryList");

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getBody().getHttpStatus());
        Assertions.assertEquals(true, response.getBody().getIsSuccess());
        Assertions.assertEquals(categoryListRequest.getPagination().getPageSize(), categoryList.size());


    }

}