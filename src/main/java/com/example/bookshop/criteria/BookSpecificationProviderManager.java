package com.example.bookshop.criteria;

import com.example.bookshop.model.Book;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {
    @Autowired
    private List<SpecificationProvider<Book>> booksProviders;

    @Override
    public SpecificationProvider<Book> getSpecificationProvider(String key) {
        return booksProviders.stream()
                .filter(bookSpecificationProvider -> bookSpecificationProvider
                        .getKey()
                        .equals(key))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Can`t find the key!"));
    }
}
