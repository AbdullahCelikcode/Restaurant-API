package cod.restaurantapi.diningtable.service;

import cod.restaurantapi.common.model.RMAPageResponse;
import cod.restaurantapi.diningtable.service.command.DiningTableAddCommand;
import cod.restaurantapi.diningtable.service.command.DiningTableListCommand;
import cod.restaurantapi.diningtable.service.command.DiningTableUpdateCommand;
import cod.restaurantapi.diningtable.service.domain.DiningTable;

public interface DiningTableService {

    void save(DiningTableAddCommand diningTableAddCommand);

    DiningTable findById(Long id);

    void update(Long id, DiningTableUpdateCommand diningTableUpdateCommand);

    void deleteById(Long id);

    RMAPageResponse<DiningTable> findAll(DiningTableListCommand map);
}
