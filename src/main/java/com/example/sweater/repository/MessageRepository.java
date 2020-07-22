package com.example.sweater.repository;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.domain.dto.MessageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

//the repository holds message records

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface MessageRepository extends CrudRepository<Message, Long> {
    @Query("select new com.example.sweater.domain.dto.MessageDto(" +
            "   m, " +
            "   count(ml), " +
            "   (sum(case when ml = :user then 1 else 0 end) > 0)" +
            ") " +
            "from Message m left join m.likes ml " +
            "where m.tag = :tag " +
            "group by m")
    Page<MessageDto> findByTag(@Param("tag") String tag, Pageable pageable, @Param("user") User user); //Page(instead of List) + Pageable serve for page-by-page display of long lists

    @Query("select new com.example.sweater.domain.dto.MessageDto(" +
            "   m, " +
            "   count(ml), " +
            "   (sum(case when ml = :user then 1 else 0 end) > 0)" +
            ") " +
            "from Message m left join m.likes ml " +
            "group by m")
    Page<MessageDto> findAll(Pageable pageable, @Param("user") User user);

    //using HQL(JPQL)
    @Query("select new com.example.sweater.domain.dto.MessageDto(" +
            "   m, " +
            "   count(ml), " +
            "   (sum(case when ml = :user then 1 else 0 end) > 0)" +
            ") " +
            "from Message m left join m.likes ml " +
            "where m.author = :author " +
            "group by m")
    Page<MessageDto> findByUser(Pageable pageable, @Param("author") User author, @Param("user") User user); //it`s necessary to notify author field with @Param() to use it in @Query

}
