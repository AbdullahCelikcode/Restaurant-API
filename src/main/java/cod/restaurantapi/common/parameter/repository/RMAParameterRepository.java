package cod.restaurantapi.common.parameter.repository;

import cod.restaurantapi.common.parameter.repository.entity.RMAParameterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RMAParameterRepository extends JpaRepository<RMAParameterEntity, Integer> {
    Optional<RMAParameterEntity> findByName(String name);
}
