package hust.project.freshfridge.presentation.controller;

import hust.project.freshfridge.application.dto.response.*;
import hust.project.freshfridge.application.usecase.SuggestionUseCase;
import hust.project.freshfridge.infrastructure.security.CurrentUser;
import hust.project.freshfridge.infrastructure.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suggestions")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class SuggestionController {

    private final SuggestionUseCase suggestionUseCase;

    @GetMapping("/recipes")
    public ResponseEntity<ApiResponse<List<RecipeResponse>>> getSuggestedRecipes(
            @CurrentUser UserPrincipal principal) {
        List<RecipeResponse> response = suggestionUseCase.getSuggestedRecipes(principal.getId());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/recipes/by-food")
    public ResponseEntity<ApiResponse<List<RecipeResponse>>> getSuggestedRecipesByFood(
            @CurrentUser UserPrincipal principal,
            @RequestParam Long foodId) {
        List<RecipeResponse> response = suggestionUseCase.getSuggestedRecipesByFood(principal.getId(), foodId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
