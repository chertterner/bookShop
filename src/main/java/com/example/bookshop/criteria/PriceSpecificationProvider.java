package com.example.bookshop.criteria;

import com.example.bookshop.model.Book;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class PriceSpecificationProvider implements SpecificationProvider<Book> {
    @Override
    public String getKey() {
        return "price";
    }

    public Specification<Book> getSpecification(List<String> params) {
        return (root, query, criteriaBuilder) -> root.get("price").in(params.stream().toList());
    }
}
