package cod.restaurantapi.diningtable.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiningTableMergeRequest {

    @NotEmpty
    @Size(min = 2, max = 5)
    List<Long> ids;

    @JsonIgnore
    @AssertTrue
    @SuppressWarnings("This method is unused by the application directly but Spring is using it in the background.")
    private boolean isSizeNotDuplicated() {
        if (CollectionUtils.isEmpty(this.ids)) {
            return true;
        }

        for (Long id : this.ids) {
            if (id <= 0) {
                return false;
            }
        }

        long uniqTablesSizeCount = this.ids.stream().distinct().count();
        return uniqTablesSizeCount == this.ids.size();
    }

}
