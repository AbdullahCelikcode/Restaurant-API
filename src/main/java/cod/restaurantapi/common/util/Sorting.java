package cod.restaurantapi.common.util;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@Builder
public class Sorting {

    @NotNull
    public String property;


    @NotNull
    public Sort.Direction direction;


    public static Sort of(final Sorting sort) {

        if (sort.property.equals(null) || sort.direction == null) {
            return Sort.unsorted();
        }


        return Sort.by(sort.getDirection(), sort.getProperty());

    }
}