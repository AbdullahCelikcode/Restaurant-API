package cod.restaurantapi.category.controller.request;

import cod.restaurantapi.category.model.enums.CategoryStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.EnumSet;

@Getter
@Setter
public class CategoryUpdateRequest {

    @NotBlank
    @Size(min = 3, max = 255)
    private String name;

    @NotNull
    private CategoryStatus status;

    @JsonIgnore
    @AssertTrue
    @SuppressWarnings("This method is unused by the application directly but Spring is using it in the background.")
    private boolean isCategoryStatusIsValid() {

        if (this.status == null) {
            return true;
        }

        EnumSet<CategoryStatus> acceptableStatus = EnumSet.of(
                CategoryStatus.ACTIVE,
                CategoryStatus.INACTIVE,
                CategoryStatus.DELETED
        );
        return acceptableStatus.contains(this.status);
    }
}
