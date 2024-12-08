package com.example.bookshop.controller;

import com.example.bookshop.dto.AddBookShoppingCartRequestDto;
import com.example.bookshop.dto.BookQuantityReqDto;
import com.example.bookshop.dto.ShoppingCartDto;
import com.example.bookshop.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shopping carts management",
        description = "Endpoints for shopping carts")
@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class ShoppingCartController {
    private ShoppingCartService shoppingCartService;

    @Operation(summary = "Get all cart items in the user's shopping cart",
            description = "Find all user's shopping carts")
    @GetMapping
    public Page<ShoppingCartDto> getAll(Pageable pageable) {
        return shoppingCartService.findAll(pageable);
    }

    @Operation(summary = "Add book to the shopping cart",
            description = "Add new book to the shopping cart")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void addBookShoppingCart(
            @RequestBody @Valid AddBookShoppingCartRequestDto
                    addBookShoppingCartRequestDto,
            @AuthenticationPrincipal UserDetails userDetails) {
        shoppingCartService.save(addBookShoppingCartRequestDto, userDetails);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Update quantity of a book", description = "Update quantity of a book")
    @PutMapping("/items/{id}")
    public void updateCategory(@PathVariable Long id,
                                      @RequestBody BookQuantityReqDto bookQuantityReqDto,
                               @AuthenticationPrincipal UserDetails userDetails) {
        shoppingCartService.updateBookQuantity(id, bookQuantityReqDto.getQuantity(), userDetails);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Delete book by id", description = "Delete book by id")
    @DeleteMapping("/items/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id,
                           @AuthenticationPrincipal UserDetails userDetails) {
        shoppingCartService.deleteBook(id, userDetails);
    }

}
