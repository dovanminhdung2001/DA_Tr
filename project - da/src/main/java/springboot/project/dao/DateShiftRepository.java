package springboot.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.project.entity.DateShift;

public interface DateShiftRepository extends JpaRepository<DateShift, Integer> {
}
