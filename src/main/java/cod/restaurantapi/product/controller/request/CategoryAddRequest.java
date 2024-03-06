package cod.restaurantapi.product.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CategoryAddRequest {
    @NotBlank
    @Size(min = 3, max = 256)
    private String name;
}
