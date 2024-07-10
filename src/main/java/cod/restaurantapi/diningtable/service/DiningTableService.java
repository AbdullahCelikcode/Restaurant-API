package cod.restaurantapi.diningtable.service;

import cod.restaurantapi.common.model.RMAPageResponse;
import cod.restaurantapi.diningtable.service.command.DiningTableAddCommand;
import cod.restaurantapi.diningtable.service.command.DiningTableListCommand;
import cod.restaurantapi.diningtable.service.command.DiningTableMergeCommand;
import cod.restaurantapi.diningtable.service.command.DiningTableStatusCommand;
import cod.restaurantapi.diningtable.service.command.DiningTableUpdateCommand;
import cod.restaurantapi.diningtable.service.domain.DiningTable;

import java.util.UUID;

public interface DiningTableService {

    void save(DiningTableAddCommand diningTableAddCommand);

    DiningTable findById(Long id);

    void update(Long id, DiningTableUpdateCommand diningTableUpdateCommand);

    void deleteById(Long id);

    RMAPageResponse<DiningTable> findAll(DiningTableListCommand map);

    void changeStatus(DiningTableStatusCommand DiningTableStatusCommand, Long id);

    void mergeDiningTables(DiningTableMergeCommand diningTableMergeCommand);

    void splitDiningTables(UUID mergeId);
}
