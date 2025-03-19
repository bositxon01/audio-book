package uz.pdp.audiobook.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.audiobook.entity.Category;
import uz.pdp.audiobook.mapper.CategoryMapper;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.CategoryDTO;
import uz.pdp.audiobook.repository.CategoryRepository;
import uz.pdp.audiobook.service.CategoryService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public ApiResult<CategoryDTO> createCategory(CategoryDTO categoryDTO) {
        Category category = categoryMapper.toEntity(categoryDTO);
        categoryRepository.save(category);
        return ApiResult.success(categoryMapper.toDTO(category));
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
    public ApiResult<CategoryDTO> updateCategory(Integer id, CategoryDTO categoryDTO) {
        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    categoryMapper.partialUpdate(categoryDTO, existingCategory);
                    categoryRepository.save(existingCategory);
                    return ApiResult.success(categoryMapper.toDTO(existingCategory));
                })
                .orElse(ApiResult.error("Category not found with id: " + id));
    }

    @Override
    public ApiResult<Object> deleteCategory(Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if (optionalCategory.isEmpty()) {
            return ApiResult.error("Category not found with id: " + id);
        }

        Category category = optionalCategory.get();
        category.setDeleted(true);
        categoryRepository.save(category);
        return ApiResult.success("Category deleted successfully.");
    }
}