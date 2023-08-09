package com.virtusa.springbootlibrary.dao;

import com.virtusa.springbootlibrary.entity.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CheckoutRepository extends JpaRepository<Checkout,Long> {

    Checkout findByUserEmailAndBookId(String userEmail,long bookId);

    List<Checkout> findBookByUserEmail(String userEmail);



    void deleteAllByBookId(@Param(value = "book_id")Long bookId);
}
