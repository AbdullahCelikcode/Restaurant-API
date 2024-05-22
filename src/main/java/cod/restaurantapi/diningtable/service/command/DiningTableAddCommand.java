package cod.restaurantapi.diningtable.service.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class DiningTableAddCommand {

    private List<DiningTables> diningTablesList;


    @Getter
    @Setter
    @Builder
    public static class DiningTables {

        private Integer size;
        private Integer count;

    }

}
