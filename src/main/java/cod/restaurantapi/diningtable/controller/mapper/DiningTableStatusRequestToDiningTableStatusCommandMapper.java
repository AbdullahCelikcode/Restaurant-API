package cod.restaurantapi.diningtable.controller.mapper;

import cod.restaurantapi.common.model.mapper.BaseMapper;
import cod.restaurantapi.diningtable.controller.response.DiningTableStatusRequest;
import cod.restaurantapi.diningtable.service.command.DiningTableStatusCommand;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DiningTableStatusRequestToDiningTableStatusCommandMapper extends BaseMapper<DiningTableStatusRequest, DiningTableStatusCommand> {

    DiningTableStatusRequestToDiningTableStatusCommandMapper INSTANCE = Mappers.getMapper(DiningTableStatusRequestToDiningTableStatusCommandMapper.class);
}
