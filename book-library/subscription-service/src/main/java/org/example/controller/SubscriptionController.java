package org.example.controller;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.example.model.BookView;
import org.example.model.Subscription;
import org.example.service.SubscriptionImpl;
import org.apache.commons.lang3.StringUtils;
import org.example.util.BookFeignService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("subscriptions")
public class SubscriptionController {
    final String STATUS_MSG = "Status Msg";
    final String RESPONSE_OBJ = "Response Object";
    final SubscriptionImpl subscriptionService;
    final BookFeignService bookFeignService;

    @GetMapping
    public ResponseEntity<List<Subscription>> getSubscriptions(@RequestParam(value = "subscriberName", required = false) String subscriberName){
        try{
            if(!StringUtils.isEmpty(subscriberName)){
                Optional<List<Subscription>> queriedSub = subscriptionService.getSubscription(subscriberName);
                return queriedSub.map(subscriptions -> new ResponseEntity<>(subscriptions, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
            }
            List<Subscription> subscriptions = new ArrayList<>(subscriptionService.getAllSubscriptions());
            if(subscriptions.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(subscriptions, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> subscribeBook(@RequestBody Subscription newSubscription) throws CloneNotSupportedException {
        if(ObjectUtils.isEmpty(newSubscription))
            return new ResponseEntity<>(Map.of(STATUS_MSG, "Given Pay-load Object is Empty!"), HttpStatus.BAD_REQUEST);
        RestTemplate restTemplate = new RestTemplate();
        String getBookURI = "http://localhost:8081/books?bookId=";
        ResponseEntity<List<BookView>> responseView = restTemplate.exchange(
                getBookURI + newSubscription.getBook_id(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<BookView>>() {});
        if(responseView.getStatusCode().equals(HttpStatus.NO_CONTENT))
            return new ResponseEntity<>(Map.of(STATUS_MSG, "Given book id is not exist!"), HttpStatus.BAD_REQUEST);

        //checking the book count
        BookView subscribedBook = responseView.getBody().get(0);
        if(subscribedBook.getAvailable_copies() == 0)
            return new ResponseEntity<>(Map.of(STATUS_MSG, "Book is not available to claim!"), HttpStatus.OK);

        //making changes in book available copies
        BookView prevBookState = (BookView) subscribedBook.clone();
        subscribedBook.setAvailable_copies(subscribedBook.getAvailable_copies() - 1);
        ResponseEntity<Map<String, Object>> bookUpdateResponse = bookFeignService.updateBook(subscribedBook);
        if(bookUpdateResponse.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR))
            return new ResponseEntity<>(Map.of(STATUS_MSG, bookUpdateResponse.getBody().get(STATUS_MSG)), HttpStatus.INTERNAL_SERVER_ERROR);

        // adding subscription for book
        Optional<Subscription> addedSubscription = subscriptionService.doSubscription(newSubscription);
        if(addedSubscription.isEmpty()){
            bookFeignService.updateBook(prevBookState);
            return new ResponseEntity<>(Map.of(STATUS_MSG, "Unable to Subscribe the book"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(Map.of(STATUS_MSG, "Success", RESPONSE_OBJ, newSubscription), HttpStatus.OK);
    }
}
