package hust.project.freshfridge.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateRecipeRequest {
    @Size(max = 200)
    private String name;

    private String description;

    private String htmlContent;
}
