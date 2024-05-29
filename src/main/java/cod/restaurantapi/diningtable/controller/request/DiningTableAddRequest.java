package cod.restaurantapi.diningtable.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiningTableAddRequest {

    @NotEmpty
    @Valid
    private List<DiningTables> diningTablesList;

    @Getter
    @Setter
    @Builder
    public static class DiningTables {

        @NotNull
        @Range(min = 1, max = 100)
        private Integer size;

        @NotNull
        @Range(min = 1, max = 500)
        private Integer count;

    }

    @JsonIgnore
    @AssertTrue
    @SuppressWarnings("This method is unused by the application directly but Spring is using it in the background.")
    private boolean isSizeNotDuplicated() {
        if (CollectionUtils.isEmpty(this.diningTablesList)) {
            return true;
        }
        long uniqTablesSizeCount = this.diningTablesList.stream().map(DiningTables::getSize).distinct().count();
        return uniqTablesSizeCount == this.diningTablesList.size();
    }


}
