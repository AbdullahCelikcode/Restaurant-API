package cod.restaurantapi.product.controller;

import cod.restaurantapi.common.BaseResponse;
import cod.restaurantapi.product.controller.request.CategoryAddRequest;
import cod.restaurantapi.product.controller.request.CategoryListRequest;
import cod.restaurantapi.product.controller.request.CategoryUpdateRequest;
import cod.restaurantapi.product.controller.response.CategoryListResponse;
import cod.restaurantapi.product.controller.response.CategoryResponse;
import cod.restaurantapi.product.model.mapper.CategoryCreateRequestToCommandMapper;
import cod.restaurantapi.product.model.mapper.CategoryListRequestToCommandListMapper;
import cod.restaurantapi.product.model.mapper.CategoryListToCategoryResponseListMapper;
import cod.restaurantapi.product.model.mapper.CategoryToCategoryResponse;
import cod.restaurantapi.product.model.mapper.CategoryUpdateRequestToCommandMapper;
import cod.restaurantapi.product.service.CategoryService;
import cod.restaurantapi.product.service.command.CategoryCreateCommand;
import cod.restaurantapi.product.service.command.CategoryUpdateCommand;
import cod.restaurantapi.product.service.domain.Category;
import cod.restaurantapi.product.service.domain.CategoryList;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
class CategoryController {
    private final CategoryService categoryService;
    private static final CategoryCreateRequestToCommandMapper createCategoryRequestToCommandMapper = CategoryCreateRequestToCommandMapper.INSTANCE;
    private static final CategoryToCategoryResponse categoryToCategoryResponseMapper = CategoryToCategoryResponse.INSTANCE;

    private static final CategoryUpdateRequestToCommandMapper updateRequestToCommandMapper = CategoryUpdateRequestToCommandMapper.INSTANCE;

    private static final CategoryListRequestToCommandListMapper listRequestToCommandMapper = CategoryListRequestToCommandListMapper.INSTANCE;

    private static final CategoryListToCategoryResponseListMapper categoryListToResponseList = CategoryListToCategoryResponseListMapper.INSTANCE;

    @GetMapping("/{id}")
    public BaseResponse<CategoryResponse> getCategoryById(@PathVariable @Positive @Max(999) Long id) {
        Category category = categoryService.findById(id);
        CategoryResponse categoryResponse = categoryToCategoryResponseMapper.map(category);
        return BaseResponse.successOf(categoryResponse);
    }

    @PostMapping("/all")
    public BaseResponse<CategoryListResponse> findAllCategories(
            @RequestBody @Valid CategoryListRequest listRequest) {

        CategoryList categoryList = categoryService.findAll(listRequestToCommandMapper.map(listRequest));
        CategoryListResponse categoryListResponse = categoryListToResponseList.map(categoryList);
        return BaseResponse.successOf(categoryListResponse);
    }


    @PostMapping
    public BaseResponse<Void> categoryAdd(@RequestBody @Valid CategoryAddRequest categoryAddRequest) {
        CategoryCreateCommand categoryCreateCommand = createCategoryRequestToCommandMapper.map(categoryAddRequest);
        categoryService.save(categoryCreateCommand);
        return BaseResponse.SUCCESS;
    }


    @PutMapping("/{id}")
    public BaseResponse<CategoryResponse> categoryUpdate(@PathVariable @Positive @Max(999) Long id,
                                                         @RequestBody @Valid CategoryUpdateRequest categoryUpdateRequest) {

        CategoryUpdateCommand categoryUpdateCommand = updateRequestToCommandMapper.map(categoryUpdateRequest);
        Category category = categoryService.update(id, categoryUpdateCommand);
        CategoryResponse categoryResponse = categoryToCategoryResponseMapper.map(category);

        return BaseResponse.successOf(categoryResponse);
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> categoryDelete(@PathVariable @Positive @Max(999) Long id) {
        categoryService.delete(id);
        return BaseResponse.SUCCESS;
    }
}
