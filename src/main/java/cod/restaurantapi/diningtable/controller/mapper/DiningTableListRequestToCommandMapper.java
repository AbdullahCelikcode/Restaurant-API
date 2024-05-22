package cod.restaurantapi.diningtable.controller.mapper;

import cod.restaurantapi.common.model.mapper.BaseMapper;
import cod.restaurantapi.diningtable.controller.request.DiningTableListRequest;
import cod.restaurantapi.diningtable.service.command.DiningTableListCommand;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DiningTableListRequestToCommandMapper extends BaseMapper<DiningTableListRequest, DiningTableListCommand> {
    DiningTableListRequestToCommandMapper INSTANCE = Mappers.getMapper(DiningTableListRequestToCommandMapper.class);
}
