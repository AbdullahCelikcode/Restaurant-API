package cod.restaurantapi.product.service.command;

import cod.restaurantapi.common.model.RMAFilter;
import cod.restaurantapi.common.model.RMASpecification;
import cod.restaurantapi.common.service.command.RMAListCommand;
import cod.restaurantapi.product.model.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Set;

@Getter
@SuperBuilder
public class ProductListCommand extends RMAListCommand implements RMASpecification {

    private ProductFilter filter;


    @Getter
    @Builder
    @AllArgsConstructor
    public static class ProductFilter implements RMAFilter {

        private String name;

        private Set<ProductStatus> statuses;

        private Long categoryId;

        private Integer minPrice;

        private Integer maxPrice;


    }

    @Override
    public <C> Specification<C> toSpecification(Class<C> clazz) {

        if (this.filter == null) {
            return Specification.allOf();
        }

        Specification<C> specification = Specification.where(null);

        if (StringUtils.hasText(this.filter.getName())) {
            Specification<C> tempSpecification = (root, _, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), STR."%\{filter.getName().toLowerCase()}%");
            specification = specification.and(tempSpecification);
        }

        if (!CollectionUtils.isEmpty(this.filter.getStatuses())) {
            Specification<C> statusSpecification = (root, _, _) ->
                    root.get("status").in(this.filter.getStatuses());
            specification = specification.and(statusSpecification);
        }
        if ((this.filter.getCategoryId() != null)) {
            Specification<C> statusSpecification = (root, _, _) ->
                    root.get("categoryId").in(this.filter.getCategoryId());
            specification = specification.and(statusSpecification);
        }

        if (this.filter.getMinPrice() != null && this.filter.getMaxPrice() != null) {
            Specification<C> priceSpecification = (root, _, criteriaBuilder) ->
                    criteriaBuilder.between(root.get("price"), this.filter.getMinPrice(), this.filter.getMaxPrice());
            specification = specification.and(priceSpecification);
        }

        if (this.filter.getMinPrice() != null && this.filter.getMaxPrice() == null) {
            Specification<C> minPriceSpecification = (root, _, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("price"), this.filter.getMinPrice());
            specification = specification.and(minPriceSpecification);
        }

        if (this.filter.getMaxPrice() != null && this.filter.getMinPrice() == null) {
            Specification<C> maxPriceSpecification = (root, _, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("price"), this.filter.getMaxPrice());
            specification = specification.and(maxPriceSpecification);
        }

        return specification;
    }

}
