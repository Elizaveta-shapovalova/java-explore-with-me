package ru.practicum.ewm_main.compilation.service;

import org.springframework.data.domain.Page;
import ru.practicum.ewm_main.compilation.model.Compilation;

import java.util.List;

public interface CompilationService {
    Compilation create(Compilation compilation, List<Long> events);

    void delete(Long id);

    Compilation update(Long id, Compilation compilation, List<Long> events);

    Page<Compilation> getAll(Boolean pinned, Integer from, Integer size);

    Compilation getById(Long id);
}
