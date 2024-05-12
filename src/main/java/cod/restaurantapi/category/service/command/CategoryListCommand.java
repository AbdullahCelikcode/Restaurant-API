package cod.restaurantapi.category.service.command;

import cod.restaurantapi.common.model.RMAFilter;
import cod.restaurantapi.common.model.RMASpecification;
import cod.restaurantapi.common.service.command.RMAListCommand;
import cod.restaurantapi.category.model.enums.CategoryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Set;

@Getter
@Setter
@SuperBuilder
public class CategoryListCommand extends RMAListCommand implements RMASpecification {

    private CategoryFilter filter;


    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryFilter implements RMAFilter {

        private String name;

        private Set<CategoryStatus> statuses;

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

        return specification;

    }

}
