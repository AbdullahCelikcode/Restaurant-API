package cod.restaurantapi.category.service.domain;

import cod.restaurantapi.category.model.enums.CategoryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class Category {

    private Long id;
    private String name;
    private CategoryStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void active() {
        this.status = CategoryStatus.ACTIVE;
    }

}
