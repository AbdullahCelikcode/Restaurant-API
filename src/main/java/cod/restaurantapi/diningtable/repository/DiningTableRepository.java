package cod.restaurantapi.diningtable.repository;

import cod.restaurantapi.diningtable.repository.entity.DiningTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DiningTableRepository extends JpaRepository<DiningTableEntity, Long>, JpaSpecificationExecutor<DiningTableEntity> {
}
