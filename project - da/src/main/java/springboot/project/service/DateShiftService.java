package springboot.project.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import springboot.project.entity.DateShift;
import springboot.project.model.DateShiftDTO;
import springboot.project.model.MessageResponseDTO;

public interface DateShiftService {
    DateShift create(DateShiftDTO dto);
    DateShift update(DateShiftDTO dto);
    Page<DateShift> page(Pageable pageable);

    boolean delete(int dateShiftId);

    String createAES(MessageResponseDTO dto);
}
