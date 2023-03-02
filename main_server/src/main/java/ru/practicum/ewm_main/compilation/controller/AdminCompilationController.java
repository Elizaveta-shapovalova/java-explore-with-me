package ru.practicum.ewm_main.compilation.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm_main.compilation.dto.CompilationDto;
import ru.practicum.ewm_main.compilation.dto.CompilationShortDto;
import ru.practicum.ewm_main.compilation.mapper.CompilationMapper;
import ru.practicum.ewm_main.compilation.service.CompilationService;
import ru.practicum.ewm_main.validation.Create;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/compilations")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AdminCompilationController {
    CompilationService compilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto create(@Validated({Create.class}) @RequestBody CompilationShortDto compilationShortDto) {
        return CompilationMapper.toCompilationDto(compilationService.create(CompilationMapper.toCompilation(compilationShortDto),
                compilationShortDto.getEvents()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        compilationService.delete(id);
    }

    @PatchMapping("/{id}")
    public CompilationDto update(@PathVariable Long id,
                                 @RequestBody CompilationShortDto compilationShortDto) {
        return CompilationMapper.toCompilationDto(compilationService.update(id,
                CompilationMapper.toCompilation(compilationShortDto),
                compilationShortDto.getEvents()));
    }
}
