package hust.project.freshfridge.infrastructure.persistence.repository.jpa;

import hust.project.freshfridge.infrastructure.persistence.model.ShoppingListModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShoppingListJpaRepository extends JpaRepository<ShoppingListModel, Long>,
        JpaSpecificationExecutor<ShoppingListModel> {
    
    List<ShoppingListModel> findByGroupIdOrderByCreatedAtDesc(Long groupId);
    
    List<ShoppingListModel> findByAssignedToUserIdOrderByShoppingDateAsc(Long userId);
    
    List<ShoppingListModel> findByShoppingDateAndStatusOrderByIdAsc(LocalDate shoppingDate, String status);
}
