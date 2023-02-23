package ru.practicum.ewm_main.compilation.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm_main.compilation.dto.CompilationDto;
import ru.practicum.ewm_main.compilation.mapper.CompilationMapper;
import ru.practicum.ewm_main.compilation.service.CompilationService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/compilations")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PublicCompilationController {
    CompilationService compilationService;

    @GetMapping
    public List<CompilationDto> getAll(@RequestParam(required = false) Boolean pinned,
                                       @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                       @Positive @RequestParam(defaultValue = "10") Integer size) {
        return CompilationMapper.toListCompilationDto(compilationService.getAll(pinned, from, size));
    }

    @GetMapping("/{id}")
    public CompilationDto getById(@PathVariable Long id) {
        return CompilationMapper.toCompilationDto(compilationService.getById(id));
    }
}
