package org.example.util;

import org.example.model.BookView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(value="bookService", url="http://localhost:8081/books")
public interface BookFeignService {
    @RequestMapping(method = RequestMethod.PUT, value = "/")
    ResponseEntity<Map<String, Object>> updateBook(@RequestBody BookView bookWithChanges);
}