package hust.project.freshfridge.application.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateRecipeRequest {
    @NotBlank(message = "Recipe name is required")
    @Size(max = 200)
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "HTML content is required")
    private String htmlContent;

    @Valid
    private List<IngredientItem> ingredients;

    @Data
    public static class IngredientItem {
        @NotNull(message = "Food ID is required")
        private Long foodId;

        @NotNull(message = "Quantity is required")
        @Positive
        private BigDecimal quantity;

        @NotNull(message = "Unit ID is required")
        private Long unitId;

        private Boolean isMainIngredient = false;

        private String note;
    }
}
