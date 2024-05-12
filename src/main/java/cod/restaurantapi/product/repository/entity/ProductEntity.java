package cod.restaurantapi.product.repository.entity;

import cod.restaurantapi.category.repository.entity.CategoryEntity;
import cod.restaurantapi.common.model.BaseEntity;
import cod.restaurantapi.product.model.enums.ExtentType;
import cod.restaurantapi.product.model.enums.ProductStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rma_product")
public class ProductEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "ingredient")
    private String ingredient;

    @Column(name = "price")
    private double price;

    @Column(name = "status")
    private ProductStatus status;

    @Column(name = "extent")
    private double extent;

    @Column(name = "extent_type")
    private ExtentType extentType;

    @Column(name = "category_id")
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;
}
