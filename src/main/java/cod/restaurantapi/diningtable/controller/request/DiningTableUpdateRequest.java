package cod.restaurantapi.diningtable.controller.request;

import cod.restaurantapi.diningtable.model.enums.DiningTableStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.util.EnumSet;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiningTableUpdateRequest {

    @NotNull
    @Range(min = 1, max = 100)
    private Integer size;

    @NotNull
    private DiningTableStatus status;

    @JsonIgnore
    @AssertTrue
    @SuppressWarnings("This method is unused by the application directly but Spring is using it in the background.")
    private boolean isDiningTableStatusIsValid() {

        if (this.status == null) {
            return true;
        }

        EnumSet<DiningTableStatus> acceptableStatus = EnumSet.of(
                DiningTableStatus.AVAILABLE,
                DiningTableStatus.OCCUPIED,
                DiningTableStatus.RESERVED,
                DiningTableStatus.DELETED
        );
        return acceptableStatus.contains(this.status);
    }

}
