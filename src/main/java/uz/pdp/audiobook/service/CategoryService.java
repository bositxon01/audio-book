package uz.pdp.audiobook.service;

import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.CategoryDTO;

import java.util.List;

public interface CategoryService {

    ApiResult<CategoryDTO> createCategory(CategoryDTO categoryDTO);

    ApiResult<List<CategoryDTO>> createCategories(List<CategoryDTO> categoryDTOList);

    ApiResult<CategoryDTO> getCategory(Integer id);

    ApiResult<List<CategoryDTO>> getAllCategories();

    ApiResult<CategoryDTO> updateCategory(Integer id, CategoryDTO categoryDTO);

    ApiResult<Object> deleteCategory(Integer id);

}
