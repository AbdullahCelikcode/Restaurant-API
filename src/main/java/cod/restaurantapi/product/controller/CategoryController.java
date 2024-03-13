package cod.restaurantapi.product.controller;

import cod.restaurantapi.common.BaseResponse;
import cod.restaurantapi.product.controller.request.CategoryAddRequest;
import cod.restaurantapi.product.controller.response.CategoryResponse;
import cod.restaurantapi.product.model.mapper.CategoryCreateRequestToCommandMapper;
import cod.restaurantapi.product.model.mapper.CategoryToCategoryResponse;
import cod.restaurantapi.product.service.CategoryService;
import cod.restaurantapi.product.service.command.CategoryCreateCommand;
import cod.restaurantapi.product.service.domain.Category;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
class CategoryController {
    private final CategoryService categoryService;
    private static final CategoryCreateRequestToCommandMapper toCommand = CategoryCreateRequestToCommandMapper.INSTANCE;
    private static final CategoryToCategoryResponse toResponse = CategoryToCategoryResponse.INSTANCE;


    @PostMapping
    public BaseResponse<Void> categoryAdd(@RequestBody @Valid CategoryAddRequest categoryAddRequest) {
        CategoryCreateCommand categoryCreateCommand = toCommand.map(categoryAddRequest);
        categoryService.save(categoryCreateCommand);
        return BaseResponse.SUCCESS;
    }

    @GetMapping("/{id}")
    public BaseResponse<CategoryResponse> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.findById(id);
        CategoryResponse categoryResponse = toResponse.map(category);
        return BaseResponse.successOf(categoryResponse);
    }
}
