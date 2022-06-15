package com.sena.tecmiecommercebackend.controller;

import com.sena.tecmiecommercebackend.common.ApiResponse;
import com.sena.tecmiecommercebackend.dto.product.ProductDto;
import com.sena.tecmiecommercebackend.exceptions.AuthenticationFailException;
import com.sena.tecmiecommercebackend.exceptions.CartItemNotExistException;
import com.sena.tecmiecommercebackend.exceptions.WishtNotExistException;
import com.sena.tecmiecommercebackend.repository.entity.Product;
import com.sena.tecmiecommercebackend.repository.entity.User;
import com.sena.tecmiecommercebackend.repository.entity.Wish;
import com.sena.tecmiecommercebackend.service.AuthenticationService;
import com.sena.tecmiecommercebackend.service.ProductService;
import com.sena.tecmiecommercebackend.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

    @Autowired
    WishListService wishListService;

    @Autowired
    AuthenticationService authenticationService;

    @GetMapping("/{token}")
    public ResponseEntity<List<ProductDto>> getWishList(@PathVariable("token") String token) {
        Long userId = authenticationService.getUser(token).getId();
        List<Wish> body = wishListService.readWishList(userId);
        List<ProductDto> products = new ArrayList<>();
        for (Wish wishList : body) {
            products.add(ProductService.getDtoFromProduct(wishList.getProduct()));
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToWishList(@RequestBody Product product, @RequestParam("token") String token) {
        authenticationService.authenticate(token);

        User user= authenticationService.getUser(token);

        Wish wishList = new Wish(user, product);
        wishListService.saveWishList(wishList);

        ApiResponse apiResponse = new ApiResponse(true, "Added to wish list.");
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{wishId}")
    public ResponseEntity<ApiResponse> deleteWish(@PathVariable("wishId") Integer wishId,@RequestParam("token") String token) throws AuthenticationFailException, WishtNotExistException {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        wishListService.deleteWish(wishId, user);
        return new ResponseEntity<>(new ApiResponse(true, "Wish has been removed."), HttpStatus.OK);
    }

}
