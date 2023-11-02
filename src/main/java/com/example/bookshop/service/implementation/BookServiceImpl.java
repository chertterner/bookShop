package com.example.bookshop.service.implementation;

import com.example.bookshop.criteria.SpecificationProvider;
import com.example.bookshop.dto.BookDto;
import com.example.bookshop.dto.CreateBookRequestDto;
import com.example.bookshop.exceptions.EntityNotFoundException;
import com.example.bookshop.mappers.BookMapper;
import com.example.bookshop.model.Book;
import com.example.bookshop.repository.BookRepository;
import com.example.bookshop.service.BookService;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private SpecificationProvider specificationProvider;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto book) {
        Book book1 = bookMapper.toModel(book);
        return bookMapper.toDto(bookRepository.save(book1));
    }

    @Override
    public List<Book> getAll(Map<String, List<String>> params) {
        Specification<Book> specification = null;
        for (Map.Entry entry : params.entrySet()) {
            Specification<Book> sp = specificationProvider
                    .getSpecification((List<String>) entry.getValue(),
                            (String) entry.getKey());
            specification = specification == null ? Specification.where(sp) : specification.and(sp);
        }
        return bookRepository.findAll(Objects.requireNonNull(specification));
    }

    @Override
    public List<BookDto> findAll(Pageable pageabale) {
        return bookRepository.findAll(pageabale).stream().map(bookMapper::toDto).toList();
    }

    @Override
    public BookDto get(Long id) {
        return bookMapper.toDto(bookRepository.getReferenceById(id));
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public BookDto update(Long id, CreateBookRequestDto bookDto) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Can't found book with id: " + id);
        }
        Book book = bookMapper.toModel(bookDto);
        book.setId(id);
        return bookMapper.toDto(bookRepository.save(book));
    }
}
