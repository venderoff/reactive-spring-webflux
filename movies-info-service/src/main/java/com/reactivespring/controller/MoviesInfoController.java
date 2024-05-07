package com.reactivespring.controller;

import com.reactivespring.domain.MovieInfo;
import com.reactivespring.dto.Err;
import com.reactivespring.service.MoviesInfoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1")
public class MoviesInfoController {

    private MoviesInfoService moviesInfoService;

    public MoviesInfoController(MoviesInfoService moviesInfoService) {
        this.moviesInfoService = moviesInfoService;
    }

    @PostMapping("/movieinfos")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MovieInfo> addMovieInfo(@RequestBody @Valid MovieInfo movieInfo){

        return moviesInfoService.addMovieInfo(movieInfo).log();

    }

    @GetMapping("/movieinfos")
    @ResponseStatus(HttpStatus.OK)
    public Flux<MovieInfo> getMovieInfo(@RequestParam(value = "year",required = false)Integer year) {

        if (year != null) {
            return moviesInfoService.getMovieByYear(year) ;
        }

        return moviesInfoService.getMovieInfo() ;

    }

    @GetMapping("/movieinfos/{id}")
    public Mono<ResponseEntity<MovieInfo>> getById (@PathVariable  String id) {

//      return new ResponseEntity<>(moviesInfoService.getById(id).log(), HttpStatus.OK) ;

        return moviesInfoService.getById(id)
                .map(ResponseEntity.ok()::body)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()))
                .log() ;

    }

    @PutMapping("/movieinfos/{id}")
//    public ResponseEntity<Mono<MovieInfo>> updateDocs(@RequestBody MovieInfo movieInfo, @PathVariable String id){
    public Mono<ResponseEntity<MovieInfo>> updateDocs(@RequestBody MovieInfo movieInfo, @PathVariable String id){


        Err e = new Err() ;
        e.setError("not found");
        return moviesInfoService.updateInfo(movieInfo,id)
                .map(ResponseEntity.ok()::body)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()))

                .log() ;


//        return  new ResponseEntity<>(moviesInfoService.updateInfo(movieInfo, id), HttpStatus.OK) ;
//        return  new ResponseEntity<>(moviesInfoService.updateInfo(movieInfo, id), HttpStatus.OK) ;
    }



    @DeleteMapping("/movieinfos/{id}")
    public ResponseEntity<Mono<String>> deleteInfo(@PathVariable String id) {
                     moviesInfoService.deleteInfo(id) ;
          return new ResponseEntity<>(Mono.just("Deleted"), HttpStatus.OK) ;
    }
}
