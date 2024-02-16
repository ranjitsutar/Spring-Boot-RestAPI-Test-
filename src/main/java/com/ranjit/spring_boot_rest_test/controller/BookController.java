package com.ranjit.spring_boot_rest_test.controller;

import com.ranjit.spring_boot_rest_test.exception.NotFoundException;
import com.ranjit.spring_boot_rest_test.model.Book;
import com.ranjit.spring_boot_rest_test.repository.BookRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookRepo bookRepo;

    @GetMapping("")
    private List<Book> getAllBook(){
    return bookRepo.findAll();
    }
    @GetMapping(value = "/{id}")
    public  Book getBookById(@PathVariable(value = "id") Integer bookId){
        return bookRepo.findById(bookId).get();
    }

    @PostMapping("/add")
    public Book addBook(@RequestBody @Valid Book addBook){
        return bookRepo.save(addBook);
    }
    @PutMapping("/update")
    public Book updateBook(@RequestBody @Valid Book book) throws NotFoundException {
        if(book==null || book.getBookId()==null)
            throw  new NotFoundException("Book Record or ID not be null");

        Optional<Book> byId = bookRepo.findById(book.getBookId());

        if(!byId.isPresent())
            throw new NotFoundException("Book with Id "+book.getBookId()+"does not exist");

       Book existingBook= byId.get();
       existingBook.setName(book.getName());
       existingBook.setSummery(book.getSummery());
       existingBook.setRating(book.getRating());

       return bookRepo.save(existingBook);

    }

    // Delete is Created By TDD Approch

    @DeleteMapping("/delete/{id}")
    public void deleteBookById(@PathVariable (value = "id") Integer bookId) throws  Exception{
        if(!bookRepo.findById(bookId).isPresent()){
            throw new NotFoundException("book "+bookId+" notpresent");
        }
        bookRepo.deleteById(bookId);



    }


}
