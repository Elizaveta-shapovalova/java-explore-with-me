package ru.practicum.ewm_main.compilation.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import ru.practicum.ewm_main.compilation.dto.CompilationDto;
import ru.practicum.ewm_main.compilation.dto.CompilationShortDto;
import ru.practicum.ewm_main.compilation.model.Compilation;
import ru.practicum.ewm_main.event.mapper.EventMapper;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompilationMapper {
    public static Compilation toCompilation(CompilationShortDto compilationShortDto) {
        return Compilation.builder()
                .title(compilationShortDto.getTitle())
                .pinned(compilationShortDto.getPinned())
                .build();
    }

    public static CompilationDto toCompilationDto(Compilation compilation) {
        return CompilationDto.builder()
                .events(EventMapper.toListEventDto(compilation.getEvents()))
                .title(compilation.getTitle())
                .pinned(compilation.getPinned())
                .build();
    }

    public static List<CompilationDto> toListCompilationDto(Page<Compilation> compilations) {
        return compilations.stream().map(CompilationMapper::toCompilationDto).collect(Collectors.toList());
    }
}
