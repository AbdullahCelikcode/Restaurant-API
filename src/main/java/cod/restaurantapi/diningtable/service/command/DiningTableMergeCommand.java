package cod.restaurantapi.diningtable.service.command;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class DiningTableMergeCommand {

    private List<Long> ids;
}
