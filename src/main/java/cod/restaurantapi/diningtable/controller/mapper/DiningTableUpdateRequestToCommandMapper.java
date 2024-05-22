package cod.restaurantapi.diningtable.controller.mapper;

import cod.restaurantapi.common.model.mapper.BaseMapper;
import cod.restaurantapi.diningtable.controller.request.DiningTableUpdateRequest;
import cod.restaurantapi.diningtable.service.command.DiningTableUpdateCommand;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DiningTableUpdateRequestToCommandMapper extends BaseMapper<DiningTableUpdateRequest, DiningTableUpdateCommand> {
    DiningTableUpdateRequestToCommandMapper INSTANCE = Mappers.getMapper(DiningTableUpdateRequestToCommandMapper.class);
}
