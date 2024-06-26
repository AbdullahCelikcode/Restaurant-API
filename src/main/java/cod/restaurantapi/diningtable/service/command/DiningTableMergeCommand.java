package cod.restaurantapi.diningtable.service.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class DiningTableMergeCommand {

    private List<Long> ids;
}
