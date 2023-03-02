package ru.practicum.ewm_main.category.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm_main.category.dto.CategoryDto;
import ru.practicum.ewm_main.category.dto.CategoryShortDto;
import ru.practicum.ewm_main.category.mapper.CategoryMapper;
import ru.practicum.ewm_main.category.service.CategoryService;
import ru.practicum.ewm_main.validation.Create;
import ru.practicum.ewm_main.validation.Update;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/categories")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AdminCategoryController {
    CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto create(@Validated({Create.class}) @RequestBody CategoryShortDto categoryShortDto) {
        return CategoryMapper.toCategoryDto(categoryService.create(CategoryMapper.toCategory(categoryShortDto)));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }

    @PatchMapping("{id}")
    public CategoryDto update(@PathVariable Long id,
                              @Validated({Update.class}) @RequestBody CategoryShortDto categoryShortDto) {
        return CategoryMapper.toCategoryDto(categoryService.update(id, CategoryMapper.toCategory(categoryShortDto)));
    }
}
