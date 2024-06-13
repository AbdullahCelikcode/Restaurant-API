package cod.restaurantapi.product.controller.request;

import cod.restaurantapi.product.model.enums.ExtentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductAddRequest {

    @NotBlank
    @Size(max = 512)
    private String name;

    @NotBlank
    @Size(max = 2048)
    private String ingredient;

    @NotNull
    @Range(min = 0, max = 10000)
    private BigDecimal price;

    @NotNull
    @Range(min = 0, max = 10000)
    private BigDecimal extent;

    @NotNull
    private ExtentType extentType;

    @NotNull
    @Range(min = 0, max = Integer.MAX_VALUE)
    private Long categoryId;

}
