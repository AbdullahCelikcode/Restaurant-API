package cod.restaurantapi.menu.service.command;

import cod.restaurantapi.category.model.enums.CategoryStatus;
import cod.restaurantapi.common.model.RMAFilter;
import cod.restaurantapi.common.model.RMASpecification;
import cod.restaurantapi.common.service.command.RMAListCommand;
import cod.restaurantapi.product.model.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

@Getter
@SuperBuilder
public class MenuListCommand extends RMAListCommand implements RMASpecification {

    private MenuFilter filter;


    @Getter
    @Builder
    @AllArgsConstructor
    public static class MenuFilter implements RMAFilter {

        private String name;

    }


    @Override
    public <C> Specification<C> toSpecification(Class<C> clazz) {

        Specification<C> specification = Specification.where(null);

        Specification<C> statusProductSpecification = (root, _, _) -> root.get("status").in(ProductStatus.ACTIVE);
        Specification<C> statusCategorySpecification = (root, _, _) -> root.join("category").get("status").in(CategoryStatus.ACTIVE);

        specification = specification.and(statusProductSpecification.and(statusCategorySpecification));

        if (this.filter != null && StringUtils.hasText(this.filter.getName())) {
            Specification<C> productNameSpecification = (root, _, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), STR."%\{filter.getName().toLowerCase()}%");

            Specification<C> categoryNameSpecification = (root, _, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.join("category").get("name")), STR."%\{filter.getName().toLowerCase()}%");
            specification = specification.and(productNameSpecification.or(categoryNameSpecification));
        }


        return specification;
    }
}
