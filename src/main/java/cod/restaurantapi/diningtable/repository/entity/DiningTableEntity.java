package cod.restaurantapi.diningtable.repository.entity;

import cod.restaurantapi.common.model.BaseEntity;
import cod.restaurantapi.diningtable.model.enums.DiningTableStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "rma_dining_table")
public class DiningTableEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "merge_id")
    @Builder.Default
    private UUID mergeId = UUID.randomUUID();

    @Column(name = "size")
    private Integer size;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private DiningTableStatus status = DiningTableStatus.AVAILABLE;

}
