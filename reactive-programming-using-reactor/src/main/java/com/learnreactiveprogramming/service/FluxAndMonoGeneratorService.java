package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class FluxAndMonoGeneratorService {
    public FluxAndMonoGeneratorService() {
    }

    public Flux<String> nameFlux() {


        return Flux.just("alex", "chloe")
                .map(String::toUpperCase)
                .log() ;

    }

    public Mono<String> nameMoono() {

        return Mono.just("mono - alex").log() ;

    }

    public static void main(String[] args) {

        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

        System.out.println("Priting Flux");

        fluxAndMonoGeneratorService.nameFlux()
                .subscribe(n -> {
                    System.out.println(n);
                }) ;

        System.out.println("Printing Mono");
        fluxAndMonoGeneratorService.nameMoono()
                .subscribe(n -> {
                    System.out.println(n);
                });
    }


//    public Flux<List<String>> split


}
