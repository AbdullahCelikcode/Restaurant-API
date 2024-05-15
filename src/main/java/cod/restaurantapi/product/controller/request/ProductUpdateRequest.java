package cod.restaurantapi.product.controller.request;

import cod.restaurantapi.product.model.enums.ExtentType;
import cod.restaurantapi.product.model.enums.ProductStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;

@Getter
@Builder
public class ProductUpdateRequest {

    @NotBlank
    @Size(max = 512)
    private String name;

    @NotBlank
    @Size(max = 2048)
    private String ingredient;


    @NotNull
    private ProductStatus status;

    @NotNull
    @Range(min = 0, max = Integer.MAX_VALUE)
    private BigDecimal price;

    @NotNull
    @Range(min = 0, max = Integer.MAX_VALUE)
    private BigDecimal extent;

    @NotNull
    private ExtentType extentType;

    @NotNull
    @Range(min = 0, max = Integer.MAX_VALUE)
    private Long categoryId;

}
