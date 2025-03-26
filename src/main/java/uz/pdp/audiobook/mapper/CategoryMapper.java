package uz.pdp.audiobook.mapper;

import org.mapstruct.*;
import uz.pdp.audiobook.entity.Category;
import uz.pdp.audiobook.payload.CategoryDTO;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {
    Category toEntity(CategoryDTO categoryDTO);

    CategoryDTO toDTO(Category category);

    List<CategoryDTO> toDTO(List<Category> categoryList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(CategoryDTO categoryDTO, @MappingTarget Category category);
}