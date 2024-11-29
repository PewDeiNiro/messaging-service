package com.pewde.messagingservice.repository;

import com.pewde.messagingservice.entity.Dialog;
import com.pewde.messagingservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DialogRepository extends JpaRepository<Dialog, Integer> {

    Optional<Dialog> findById(Integer id);

    List<Dialog> findByCollocutors(User user);

}
