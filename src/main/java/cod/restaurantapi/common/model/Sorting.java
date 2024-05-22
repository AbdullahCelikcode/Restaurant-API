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


}