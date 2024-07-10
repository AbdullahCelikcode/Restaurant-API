package cod.restaurantapi.diningtable.service.command;

import cod.restaurantapi.diningtable.model.enums.DiningTableStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DiningTableStatusCommand {
    private DiningTableStatus status;

}
