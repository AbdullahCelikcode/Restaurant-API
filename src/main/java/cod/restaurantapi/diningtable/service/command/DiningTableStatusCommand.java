package cod.restaurantapi.diningtable.service.command;

import cod.restaurantapi.diningtable.model.enums.DiningTableStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DiningTableStatusCommand {
    private DiningTableStatus status;

}
