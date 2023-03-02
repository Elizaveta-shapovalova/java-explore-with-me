package ru.practicum.ewm_main.category.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm_main.category.model.Category;
import ru.practicum.ewm_main.category.repository.CategoryRepository;
import ru.practicum.ewm_main.event.repository.EventRepository;
import ru.practicum.ewm_main.exception.ObjectNotFoundException;
import ru.practicum.ewm_main.exception.ValidationException;

import static ru.practicum.ewm_main.constant.Constant.SORT_BY_ASC;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository;
    EventRepository eventRepository;

    @Transactional
    @Override
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (!eventRepository.findAllByCategoryId(id).isEmpty()) {
            throw new ValidationException("The category is not empty");
        }
        categoryRepository.delete(getById(id));
    }

    @Transactional
    @Override
    public Category update(Long id, Category category) {
        Category categoryToUpdate = getById(id);
        categoryToUpdate.setName(category.getName());
        return categoryRepository.save(categoryToUpdate);
    }

    public Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Category with id=%d was not found", id)));
    }

    @Override
    public Page<Category> getAll(Integer from, Integer size) {
        return categoryRepository.findAll(PageRequest.of(from / size, size, SORT_BY_ASC));
    }
}
