package springboot.project.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import springboot.project.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findAllByReceiverIdOrSenderId(Pageable pageable, Integer receiverId, Integer senderId);

    Page<Message> findAllByRoom(Pageable pageable, String room);
}
