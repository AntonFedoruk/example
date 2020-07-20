package com.example.sweater.repository;

import com.example.sweater.domain.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

//the repository holds message records

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface MessageRepository extends CrudRepository<Message, Long> {
    Page<Message> findByTag(String tag, Pageable pageable); //Page(instead of List) + Pageable serve for page-by-page display of long lists
    Page<Message> findAll(Pageable pageable);
}
