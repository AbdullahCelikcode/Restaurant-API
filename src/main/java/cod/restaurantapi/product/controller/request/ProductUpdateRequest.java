package cod.restaurantapi.product.controller.request;

import cod.restaurantapi.product.model.enums.ExtentType;
import cod.restaurantapi.product.model.enums.ProductStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;
import java.util.EnumSet;

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

    @JsonIgnore
    @AssertTrue
    @SuppressWarnings("This method is unused by the application directly but Spring is using it in the background.")
    private boolean isProductStatusValid() {

        if (this.status == null) {
            return true;
        }

        EnumSet<ProductStatus> acceptableStatus = EnumSet.of(
                ProductStatus.ACTIVE,
                ProductStatus.INACTIVE,
                ProductStatus.DELETED
        );
        return acceptableStatus.contains(this.status);
    }

    @JsonIgnore
    @AssertTrue
    @SuppressWarnings("This method is unused by the application directly but Spring is using it in the background.")
    private boolean isExtentTypeValid() {

        if (this.extentType == null) {
            return true;
        }

        EnumSet<ExtentType> acceptableStatus = EnumSet.of(
                ExtentType.ML,
                ExtentType.GR
        );
        return acceptableStatus.contains(this.extentType);
    }


}
