package pl.grzywacz.jakub.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.grzywacz.jakub.model.Abonent;
import pl.grzywacz.jakub.service.SearchService;

import java.util.List;

@RestController
public class Controller {
    @Autowired
    private SearchService searchService;

    @GetMapping(value = "/abonent/search")
    public ResponseEntity<List<Abonent>> search (@RequestParam String querryParam){
        return ResponseEntity.ok().body(searchService.search(querryParam));
    }


}
