package ru.practicum.ewm_stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm_stats.model.App;

@Repository
public interface AppRepository extends JpaRepository<App, Long> {
}
