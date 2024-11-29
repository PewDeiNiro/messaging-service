package com.pewde.messagingservice.repository;

import com.pewde.messagingservice.entity.Dialog;
import com.pewde.messagingservice.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    Optional<Message> findById(Integer id);

    List<Message> findByDialogOrderById(Dialog dialog);

    Optional<Message> findTopByDialogOrderByIdDesc(Dialog dialog);

}
