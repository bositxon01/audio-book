package uz.pdp.audiobook.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.audiobook.entity.Category;
import uz.pdp.audiobook.mapper.CategoryMapper;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.CategoryDTO;
import uz.pdp.audiobook.payload.withoutId.CategoryDto;
import uz.pdp.audiobook.repository.CategoryRepository;
import uz.pdp.audiobook.service.CategoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ApiResult<CategoryDTO> createCategory(CategoryDto categoryDto) {
        Category category = categoryMapper.toEntity(categoryDto);
        categoryRepository.save(category);
        return ApiResult.success(categoryMapper.toDTO(category));
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ApiResult<List<CategoryDTO>> createCategories(List<CategoryDto> categoryDtoList) {
        List<Category> categories = new ArrayList<>();

        for (CategoryDto categoryDto : categoryDtoList) {
            Category category = categoryMapper.toEntity(categoryDto);
            categoryRepository.save(category);
            categories.add(category);
        }

        return ApiResult.success(categoryMapper.toDTO(categories));
    }

    @Override
    public ApiResult<CategoryDTO> getCategory(Integer id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toDTO)
                .map(ApiResult::success)
                .orElse(ApiResult.error("Category not found with id " + id));
    }

    @Override
    public ApiResult<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDTO)
                .toList();

        return ApiResult.success(categories);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ApiResult<CategoryDTO> updateCategory(Integer id, CategoryDto categoryDto) {
        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    categoryMapper.updateCategoryFromDTO(categoryDto, existingCategory);
                    categoryRepository.save(existingCategory);
                    return ApiResult.success(
                            "Category updated successfully",
                            categoryMapper.toDTO(existingCategory)
                    );
                })
                .orElse(ApiResult.error("Category not found with id: " + id));
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ApiResult<Object> deleteCategory(Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if (optionalCategory.isEmpty())
            return ApiResult.error("Category not found with id: " + id);

        Category category = optionalCategory.get();
        category.setDeleted(true);
        categoryRepository.save(category);
        return ApiResult.success("Category deleted successfully.");
    }
}