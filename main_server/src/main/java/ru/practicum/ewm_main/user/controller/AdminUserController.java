package ru.practicum.ewm_main.user.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm_main.user.dto.NewUserRequest;
import ru.practicum.ewm_main.user.dto.UserDto;
import ru.practicum.ewm_main.user.mapper.UserMapper;
import ru.practicum.ewm_main.user.service.UserService;
import ru.practicum.ewm_main.validation.Create;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/admin/users")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AdminUserController {
    UserService adminUserService;

    @GetMapping
    public List<UserDto> getAll(@RequestParam(required = false) List<Long> ids,
                                @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                @Positive @RequestParam(defaultValue = "10") Integer size) {
        return UserMapper.toListUserDto(adminUserService.getAll(ids, from, size));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@Validated({Create.class}) @RequestBody NewUserRequest newUserRequest) {
        return UserMapper.toUserDto(adminUserService.create(UserMapper.toUser(newUserRequest)));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        adminUserService.delete(id);
    }
}
