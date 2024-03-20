package cod.restaurantapi.product.controller.request;

import cod.restaurantapi.product.model.enums.CategoryStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryUpdateRequest {

    @NotBlank
    @Size(min = 3, max = 255)
    private String name;

    @NotNull
    private CategoryStatus status;
}
