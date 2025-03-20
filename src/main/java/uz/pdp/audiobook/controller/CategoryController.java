package uz.pdp.audiobook.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.CategoryDTO;
import uz.pdp.audiobook.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Category API", description = "Category CRUD API")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResult<CategoryDTO>> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        ApiResult<CategoryDTO> result = categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<CategoryDTO>> getCategory(@PathVariable Integer id) {
        ApiResult<CategoryDTO> result = categoryService.getCategory(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<ApiResult<List<CategoryDTO>>> getAllCategories() {
        ApiResult<List<CategoryDTO>> result = categoryService.getAllCategories();
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResult<CategoryDTO>> updateCategory(@PathVariable Integer id,
                                                                 @Valid @RequestBody CategoryDTO categoryDTO) {
        ApiResult<CategoryDTO> result = categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult<Object>> deleteCategory(@PathVariable Integer id) {
        ApiResult<Object> result = categoryService.deleteCategory(id);
        return ResponseEntity.ok(result);
    }
}
