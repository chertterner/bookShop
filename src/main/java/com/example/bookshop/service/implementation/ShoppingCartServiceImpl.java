package com.example.bookshop.service.implementation;

import com.example.bookshop.dto.cartitem.CartItemRequestDto;
import com.example.bookshop.dto.cartitem.ShoppingCartDto;
import com.example.bookshop.exception.EntityNotFoundException;
import com.example.bookshop.mapper.ShoppingCartMapper;
import com.example.bookshop.model.CartItem;
import com.example.bookshop.model.ShoppingCart;
import com.example.bookshop.model.User;
import com.example.bookshop.repository.BookRepository;
import com.example.bookshop.repository.CartItemRepository;
import com.example.bookshop.repository.ShoppingCartRepository;
import com.example.bookshop.repository.UserRepository;
import com.example.bookshop.service.ShoppingCartService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private ShoppingCartRepository shoppingCartRepository;
    private ShoppingCartMapper shoppingCartMapper;
    private BookRepository bookRepository;
    private UserRepository userRepository;
    private CartItemRepository cartItemRepository;

    @Override
    public Page<ShoppingCartDto> findAll(Pageable pageable) {
        return new PageImpl<>(shoppingCartRepository.findAll(pageable)
                .stream()
                .map(shoppingCartMapper::toDto)
                .toList());
    }

    @Override
    public void createShoppingCartWithUser(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCartDto save(CartItemRequestDto cartItemRequestDto,
                                User user) {
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUserId(
                user.getId()
        );
        Optional<CartItem> cartItem = shoppingCart.getCartItems()
                .stream()
                .filter(item -> item.getBook()
                        .getId()
                        .equals(cartItemRequestDto.getBookId())
                ).findFirst();
        if (cartItem.isPresent()) {
            throw new EntityNotFoundException("This book exists in your cart");
        } else {
            CartItem item = new CartItem();
            item.setQuantity(cartItemRequestDto.getQuantity());
            item.setBook(bookRepository.getReferenceById(cartItemRequestDto.getBookId()));
            shoppingCart.getCartItems().add(item);
        }
        return shoppingCartMapper.toDto(shoppingCartRepository.save(shoppingCart));
    }

    @Override
    public ShoppingCartDto updateBookQuantity(Long id, int quantity, User user) {
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUserId(
                user.getId()
        );
        CartItem cartItem = cartItemRepository.findByIdAndShoppingCart_Id(
                id, shoppingCart.getId()
        );
        cartItem.setQuantity(quantity);
        return shoppingCartMapper.toDto(cartItemRepository.save(cartItem).getShoppingCart());
    }

    @Override
    public void deleteBook(Long id, User user) {
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUserId(user.getId());
        CartItem cartItem = cartItemRepository.findByBook_IdAndShoppingCart_Id(
                id, shoppingCart.getId()
        );
        cartItemRepository.delete(cartItem);
    }

}
