package cod.restaurantapi.diningtable.controller.mapper;

import cod.restaurantapi.common.model.mapper.BaseMapper;
import cod.restaurantapi.diningtable.controller.response.DiningTableResponse;
import cod.restaurantapi.diningtable.service.domain.DiningTable;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DiningTableToDiningTableResponseMapper extends BaseMapper<DiningTable, DiningTableResponse> {
    DiningTableToDiningTableResponseMapper INSTANCE = Mappers.getMapper(DiningTableToDiningTableResponseMapper.class);
}
