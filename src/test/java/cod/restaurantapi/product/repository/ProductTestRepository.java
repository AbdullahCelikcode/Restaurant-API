package cod.restaurantapi.product.repository;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public interface ProductTestRepository extends ProductRepository {
    Boolean existsByName(String name);
}
