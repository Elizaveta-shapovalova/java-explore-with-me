package ru.practicum.ewm_main.category.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm_main.category.dto.CategoryDto;
import ru.practicum.ewm_main.category.mapper.CategoryMapper;
import ru.practicum.ewm_main.category.service.CategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/categories")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PublicCategoryController {
    CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getAll(@PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                    @Positive @RequestParam(defaultValue = "10") Integer size) {
        return CategoryMapper.toListCategoryDto(categoryService.getAll(from, size));
    }

    @GetMapping("{id}")
    public CategoryDto getById(@PathVariable Long id) {
        return CategoryMapper.toCategoryDto(categoryService.getById(id));
    }
}
