package uz.pdp.audiobook.service;

import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.CategoryDTO;
import uz.pdp.audiobook.payload.withoutId.CategoryDto;

import java.util.List;

public interface CategoryService {

    ApiResult<CategoryDTO> createCategory(CategoryDto categoryDto);

    ApiResult<List<CategoryDTO>> createCategories(List<CategoryDto> categoryDtoList);

    ApiResult<CategoryDTO> getCategory(Integer id);

    ApiResult<List<CategoryDTO>> getAllCategories();

    ApiResult<CategoryDTO> updateCategory(Integer id, CategoryDto categoryDto);

    ApiResult<Object> deleteCategory(Integer id);

}
