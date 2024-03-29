package cod.restaurantapi.product.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryAddRequest {
    @NotBlank
    @Size(min = 3, max = 255)
    private String name;
}
