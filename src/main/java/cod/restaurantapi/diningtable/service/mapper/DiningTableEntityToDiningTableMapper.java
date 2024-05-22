package cod.restaurantapi.diningtable.service.mapper;

import cod.restaurantapi.common.model.mapper.BaseMapper;
import cod.restaurantapi.diningtable.repository.entity.DiningTableEntity;
import cod.restaurantapi.diningtable.service.domain.DiningTable;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DiningTableEntityToDiningTableMapper extends BaseMapper<DiningTableEntity, DiningTable> {
    DiningTableEntityToDiningTableMapper INSTANCE = Mappers.getMapper(DiningTableEntityToDiningTableMapper.class);
}
