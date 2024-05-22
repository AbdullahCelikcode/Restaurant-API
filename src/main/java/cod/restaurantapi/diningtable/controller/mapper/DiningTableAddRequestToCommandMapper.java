package cod.restaurantapi.diningtable.controller.mapper;

import cod.restaurantapi.common.model.mapper.BaseMapper;
import cod.restaurantapi.diningtable.controller.request.DiningTableAddRequest;
import cod.restaurantapi.diningtable.service.command.DiningTableAddCommand;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DiningTableAddRequestToCommandMapper extends BaseMapper<DiningTableAddRequest, DiningTableAddCommand> {
    DiningTableAddRequestToCommandMapper INSTANCE = Mappers.getMapper(DiningTableAddRequestToCommandMapper.class);

}
