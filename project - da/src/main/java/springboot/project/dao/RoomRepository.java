package springboot.project.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import springboot.project.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    Room findByRoom(String room);
    Page<Room> findAllByDoctorId(Integer doctorId);
    Page<Room> findAllByUserId(Integer userId);
}
