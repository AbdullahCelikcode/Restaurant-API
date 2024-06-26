package cod.restaurantapi.diningtable.controller.mapper;

import cod.restaurantapi.common.model.mapper.BaseMapper;
import cod.restaurantapi.diningtable.controller.request.DiningTableMergeRequest;
import cod.restaurantapi.diningtable.service.command.DiningTableMergeCommand;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DiningTableMergeRequestToDiningTableMergeCommand extends BaseMapper<DiningTableMergeRequest, DiningTableMergeCommand> {
    DiningTableMergeRequestToDiningTableMergeCommand INSTANCE = Mappers.getMapper(DiningTableMergeRequestToDiningTableMergeCommand.class);
}
