package cod.restaurantapi.diningtable.service.domain;

import cod.restaurantapi.diningtable.model.enums.DiningTableStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class DiningTable {
    private Long id;
    private UUID mergeId;
    private Integer size;
    private DiningTableStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
