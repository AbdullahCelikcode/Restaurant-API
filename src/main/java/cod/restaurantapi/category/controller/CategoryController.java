package cod.restaurantapi.category.controller;

import cod.restaurantapi.category.controller.mapper.CategoryCreateRequestToCommandMapper;
import cod.restaurantapi.category.controller.mapper.CategoryListRequestToCommandListMapper;
import cod.restaurantapi.category.controller.mapper.CategoryToCategoryResponse;
import cod.restaurantapi.category.controller.mapper.CategoryUpdateRequestToCommandMapper;
import cod.restaurantapi.category.controller.request.CategoryAddRequest;
import cod.restaurantapi.category.controller.request.CategoryListRequest;
import cod.restaurantapi.category.controller.request.CategoryUpdateRequest;
import cod.restaurantapi.category.controller.response.CategoryResponse;
import cod.restaurantapi.category.service.CategoryService;
import cod.restaurantapi.category.service.command.CategoryCreateCommand;
import cod.restaurantapi.category.service.command.CategoryUpdateCommand;
import cod.restaurantapi.category.service.domain.Category;
import cod.restaurantapi.common.BaseResponse;
import cod.restaurantapi.common.model.RMAPage;
import cod.restaurantapi.common.model.RMAPageResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Validated
@RestController
@RequiredArgsConstructor

public class CategoryController {

    private final CategoryService categoryService;
    private static final CategoryCreateRequestToCommandMapper createCategoryRequestToCommandMapper = CategoryCreateRequestToCommandMapper.INSTANCE;
    private static final CategoryToCategoryResponse categoryToCategoryResponseMapper = CategoryToCategoryResponse.INSTANCE;

    private static final CategoryUpdateRequestToCommandMapper updateRequestToCommandMapper = CategoryUpdateRequestToCommandMapper.INSTANCE;

    private static final CategoryListRequestToCommandListMapper listRequestToCommandMapper = CategoryListRequestToCommandListMapper.INSTANCE;


    @GetMapping("/api/v1/category/{id}")
    public BaseResponse<CategoryResponse> getCategoryById(@PathVariable @Positive @Max(999) Long id) {
        Category category = categoryService.findById(id);
        CategoryResponse categoryResponse = categoryToCategoryResponseMapper.map(category);
        return BaseResponse.successOf(categoryResponse);
    }


    @PostMapping("/api/v1/categories")
    public BaseResponse<RMAPage<CategoryResponse>> findAllCategories(
            @RequestBody @Valid CategoryListRequest listRequest) {


        RMAPageResponse<Category> categoryList = categoryService.findAll(listRequestToCommandMapper.map(listRequest));

        RMAPage<CategoryResponse> categoryListResponse = RMAPage.<CategoryResponse>builder()
                .map(categoryToCategoryResponseMapper.map(categoryList.getContent()), categoryList)
                .build();

        return BaseResponse.successOf(categoryListResponse);
    }


    @PostMapping("/api/v1/category")
    public BaseResponse<Void> categoryAdd(@RequestBody @Valid CategoryAddRequest categoryAddRequest) {
        CategoryCreateCommand categoryCreateCommand = createCategoryRequestToCommandMapper.map(categoryAddRequest);
        categoryService.save(categoryCreateCommand);
        return BaseResponse.SUCCESS;
    }


    @PutMapping("/api/v1/category/{id}")
    public BaseResponse<Void> categoryUpdate(@PathVariable @Positive @Max(999) Long id,
                                             @RequestBody @Valid CategoryUpdateRequest categoryUpdateRequest) {

        CategoryUpdateCommand categoryUpdateCommand = updateRequestToCommandMapper.map(categoryUpdateRequest);
        categoryService.update(id, categoryUpdateCommand);

        return BaseResponse.SUCCESS;
    }


    @DeleteMapping("/api/v1/category/{id}")
    public BaseResponse<Void> categoryDelete(@PathVariable @Positive @Max(999) Long id) {
        categoryService.delete(id);
        return BaseResponse.SUCCESS;
    }
}
