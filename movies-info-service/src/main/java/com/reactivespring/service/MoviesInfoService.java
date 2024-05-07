package com.reactivespring.service;

import com.reactivespring.domain.MovieInfo;
import com.reactivespring.repository.MovieInfoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MoviesInfoService {

    private MovieInfoRepository movieInfoRepository;

    public MoviesInfoService(MovieInfoRepository movieInfoRepository) {
        this.movieInfoRepository = movieInfoRepository;
    }

    public Mono<MovieInfo> addMovieInfo(MovieInfo movieInfo) {

        movieInfo.setStatus("ACTIVE");
        return movieInfoRepository.save(movieInfo);
    }

    public Flux<MovieInfo> getMovieInfo() {
        return movieInfoRepository.findAll() ;
    }

    public Mono<MovieInfo> getById(String id) {
        return movieInfoRepository.findById(id) ;
    }

    public Mono<Void> deleteInfo(String id) {



         return movieInfoRepository.deleteById(id) ;
    }

    public Mono<MovieInfo> updateInfo(MovieInfo movieInfo , String id) {

        return
        movieInfoRepository.findById(id)
                .flatMap(me -> {
                    me.setCast(movieInfo.getCast());
                    me.setName(movieInfo.getName());
                    me.setRelease_date(movieInfo.getRelease_date());
                    return movieInfoRepository.save(me) ;
                }) ;
    }

    public Flux<MovieInfo> getMovieByYear(Integer year) {

        return movieInfoRepository.findByYear(year) ;
    }
}
