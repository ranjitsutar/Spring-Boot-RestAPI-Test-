package com.ranjit.spring_boot_rest_test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.ranjit.spring_boot_rest_test.controller.BookController;
import com.ranjit.spring_boot_rest_test.model.Book;
import com.ranjit.spring_boot_rest_test.repository.BookRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {

    private MockMvc mockMvc;

   ObjectMapper objectMapper= new ObjectMapper();
    ObjectWriter objectWriter= objectMapper.writer();

    @Mock
    private BookRepo bookRepo;

    @InjectMocks
    private BookController bookController;

    Book book1= new Book(1,"Automobile","Mechanical",9);
    Book book2= new Book(2,"Web","Html",7);
    Book book3= new Book(3,"Java","Fullstack developer",7);

    @Before
    public void SetUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc= MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void getAllBookTest() throws Exception{

        List<Book> bookList = new ArrayList<>(Arrays.asList(book1,book2,book3));

        Mockito.when(bookRepo.findAll()).thenReturn(bookList);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/book")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(3)))
                .andExpect(jsonPath("$[2].name",is("Java")));
    }

    @Test
    public void getBookByIdTest()throws  Exception{
        Mockito.when(bookRepo.findById(book1.getBookId())).thenReturn(java.util.Optional.of(book1));

        mockMvc.perform(MockMvcRequestBuilders
                .get("book/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.name",is("Automobile")));
    }

    @Test
    public void createBookTest()throws  Exception{
        Book book= Book.builder()
                .bookId(4)
                .name("css")
                .summery("tailwin css")
                .rating(8)
                .build();

        Mockito.when(bookRepo.save(book)).thenReturn(book);

        String  content= objectWriter.writeValueAsString(book);
        MockHttpServletRequestBuilder mockRequest=MockMvcRequestBuilders.post("/book/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.name",is("css")));
    }

    @Test
    public void updateBookTest() throws Exception{

        Book book= Book.builder()
                .bookId(1)
                .name("Mockito")
                .summery("It")
                .rating(7)
                .build();

        Mockito.when(bookRepo.findById(book1.getBookId())).thenReturn(java.util.Optional.ofNullable(book1));
        Mockito.when(bookRepo.save(book)).thenReturn(book);

        String updateBook= objectWriter.writeValueAsString(book);

        MockHttpServletRequestBuilder mockeRequest= MockMvcRequestBuilders.put("/book/update")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updateBook);

        mockMvc.perform(mockeRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$", is("Mockito")));


    }

    @Test
    public void deleteByIdTest()throws Exception{
        Mockito.when(bookRepo.findById(book2.getBookId())).thenReturn(Optional.of(book2));

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/delete")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
