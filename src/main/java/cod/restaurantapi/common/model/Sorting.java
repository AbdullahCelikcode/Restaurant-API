package cod.restaurantapi.common.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Sort;

@Getter
@Builder
public class Sorting {

    @NotNull
    public String property;


    @NotNull
    public Sort.Direction direction;


    public static Sorting of(Sort sort) {
        return sort.stream().findFirst()
                .map(order -> Sorting.builder()
                        .property(order.getProperty())
                        .direction(order.getDirection())
                        .build())
                .orElse(null);
    }
}

