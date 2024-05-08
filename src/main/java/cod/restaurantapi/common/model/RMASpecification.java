package cod.restaurantapi.common.model;

import org.springframework.data.jpa.domain.Specification;

public interface RMASpecification {
    <C> Specification<C> toSpecification(final Class<C> clazz);
}
