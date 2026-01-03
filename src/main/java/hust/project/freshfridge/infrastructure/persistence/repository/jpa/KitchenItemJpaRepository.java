package hust.project.freshfridge.infrastructure.persistence.repository.jpa;

import hust.project.freshfridge.infrastructure.persistence.model.KitchenItemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface KitchenItemJpaRepository extends JpaRepository<KitchenItemModel, Long>, 
        JpaSpecificationExecutor<KitchenItemModel> {
    
    Optional<KitchenItemModel> findByFoodIdAndGroupId(Long foodId, Long groupId);
    
    List<KitchenItemModel> findByGroupId(Long groupId);
    
    boolean existsByFoodIdAndGroupId(Long foodId, Long groupId);

    @Query("SELECT k FROM KitchenItemModel k WHERE k.groupId = :groupId AND k.expiryDate IS NOT NULL AND k.expiryDate <= :thresholdDate ORDER BY k.expiryDate ASC")
    List<KitchenItemModel> findExpiringItems(@Param("groupId") Long groupId, @Param("thresholdDate") LocalDate thresholdDate);

    @Query("SELECT k FROM KitchenItemModel k JOIN FoodModel f ON k.foodId = f.id " +
           "WHERE k.groupId = :groupId " +
           "AND (:foodId IS NULL OR k.foodId = :foodId) " +
           "AND (:foodName IS NULL OR LOWER(f.name) LIKE LOWER(CONCAT('%', :foodName, '%'))) " +
           "AND (:expiryBefore IS NULL OR k.expiryDate <= :expiryBefore) " +
           "ORDER BY k.expiryDate ASC NULLS LAST")
    List<KitchenItemModel> findAllWithFilters(
            @Param("groupId") Long groupId,
            @Param("foodId") Long foodId,
            @Param("foodName") String foodName,
            @Param("expiryBefore") LocalDate expiryBefore);
}
