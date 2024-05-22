package cod.restaurantapi.diningtable.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiningTableAddRequest {

    @NotNull
    private List<DiningTables> diningTablesList;

    @Getter
    @Setter
    @Builder
    @Validated
    public static class DiningTables {

        @NotNull
        @Range(min = 1, max = 100)
        private Integer size;

        @NotNull
        @Range(min = 1, max = Integer.MAX_VALUE)
        private Integer count;

    }


}
