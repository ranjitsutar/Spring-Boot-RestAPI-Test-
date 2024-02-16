package com.ranjit.spring_boot_rest_test.repository;

import com.ranjit.spring_boot_rest_test.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepo extends JpaRepository<Book,Integer> {

}
