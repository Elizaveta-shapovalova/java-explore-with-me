package ru.practicum.ewm_main.category.service;

import org.springframework.data.domain.Page;
import ru.practicum.ewm_main.category.model.Category;

public interface CategoryService {
    Category create(Category category);

    void delete(Long id);

    Category update(Long id, Category category);

    Category getById(Long id);

    Page<Category> getAll(Integer from, Integer size);
}
