package cod.restaurantapi.product.controller;

import cod.restaurantapi.common.BaseResponse;
import cod.restaurantapi.product.controller.mapper.ProductAddRequestToCommandMapper;
import cod.restaurantapi.product.controller.request.ProductAddRequest;
import cod.restaurantapi.product.service.ProductService;
import cod.restaurantapi.product.service.command.ProductAddCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

    private static final ProductAddRequestToCommandMapper productAddRequestToCommandMapper = ProductAddRequestToCommandMapper.INSTANCE;

    private final ProductService productService;

    @PostMapping
    public BaseResponse<Void> productAdd(@RequestBody @Valid ProductAddRequest productAddRequest) {

        ProductAddCommand productAddCommand = productAddRequestToCommandMapper.map(productAddRequest);
        productService.save(productAddCommand);

        return BaseResponse.SUCCESS;
    }
}
