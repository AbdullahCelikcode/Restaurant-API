package cod.restaurantapi.product.controller;

import cod.restaurantapi.common.BaseResponse;
import cod.restaurantapi.common.model.RMAPage;
import cod.restaurantapi.common.model.RMAPageResponse;
import cod.restaurantapi.product.controller.mapper.ProductAddRequestToCommandMapper;
import cod.restaurantapi.product.controller.mapper.ProductListRequestToProductListCommandMapper;
import cod.restaurantapi.product.controller.mapper.ProductToProductResponseMapper;
import cod.restaurantapi.product.controller.mapper.ProductUpdateRequestToCommandMapper;
import cod.restaurantapi.product.controller.request.ProductAddRequest;
import cod.restaurantapi.product.controller.request.ProductListRequest;
import cod.restaurantapi.product.controller.request.ProductUpdateRequest;
import cod.restaurantapi.product.controller.response.ProductResponse;
import cod.restaurantapi.product.service.ProductService;
import cod.restaurantapi.product.service.command.ProductAddCommand;
import cod.restaurantapi.product.service.command.ProductUpdateCommand;
import cod.restaurantapi.product.service.domain.Product;
import jakarta.validation.Valid;
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

import java.util.UUID;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

    private static final ProductAddRequestToCommandMapper productAddRequestToCommandMapper = ProductAddRequestToCommandMapper.INSTANCE;

    private static final ProductToProductResponseMapper productToProductResponse = ProductToProductResponseMapper.INSTANCE;

    private static final ProductUpdateRequestToCommandMapper productUpdateRequestToCommand = ProductUpdateRequestToCommandMapper.INSTANCE;
    private static final ProductListRequestToProductListCommandMapper productListRequestToProductListCommandMapper = ProductListRequestToProductListCommandMapper.INSTANCE;

    private final ProductService productService;

    @GetMapping("/{id}")
    public BaseResponse<ProductResponse> getProductById(@PathVariable UUID id) {

        Product product = productService.findById(id);
        ProductResponse productResponse = productToProductResponse.map(product);

        return BaseResponse.successOf(productResponse);
    }

    @PostMapping("/all")
    public BaseResponse<RMAPage<ProductResponse>> findAllProducts(@RequestBody @Valid ProductListRequest productListRequest) {
        RMAPageResponse<Product> productList = productService.findAll(productListRequestToProductListCommandMapper.map(productListRequest));

        RMAPage<ProductResponse> productListResponse = RMAPage.<ProductResponse>builder()
                .map(productToProductResponse.map(productList.getContent()), productList)
                .build();

        return BaseResponse.successOf(productListResponse);
    }


    @PostMapping
    public BaseResponse<Void> productAdd(@RequestBody @Valid ProductAddRequest productAddRequest) {

        ProductAddCommand productAddCommand = productAddRequestToCommandMapper.map(productAddRequest);
        productService.save(productAddCommand);

        return BaseResponse.SUCCESS;
    }

    @PutMapping("/{id}")
    public BaseResponse<ProductResponse> productUpdate(@PathVariable UUID id,
                                                       @RequestBody @Valid ProductUpdateRequest productUpdateRequest) {

        ProductUpdateCommand productUpdateCommand = productUpdateRequestToCommand.map(productUpdateRequest);
        Product product = productService.update(id, productUpdateCommand);
        ProductResponse productResponse = productToProductResponse.map(product);

        return BaseResponse.successOf(productResponse);
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> productDelete(@PathVariable UUID id) {
        productService.delete(id);
        return BaseResponse.SUCCESS;
    }


}
