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
    @Range(min = 0, max = Integer.MAX_VALUE)
    private double price;

    @NotNull
    @Range(min = 0, max = Integer.MAX_VALUE)
    private double extent;

    @NotNull
    private ExtentType extentType;

    @NotNull
    @Range(min = 0, max = Integer.MAX_VALUE)
    private Long categoryId;

}
