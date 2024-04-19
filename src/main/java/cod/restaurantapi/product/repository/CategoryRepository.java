package cod.restaurantapi.product.repository;

import cod.restaurantapi.product.repository.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    Page<CategoryEntity> findAll(Pageable pageable);

    Page<CategoryEntity> findAllByNameContainingIgnoreCase(String name, Pageable pageable);

}