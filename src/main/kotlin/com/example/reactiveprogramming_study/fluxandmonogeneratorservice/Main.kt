package com.example.reactiveprogramming_study.fluxandmonogeneratorservice

fun main(args: Array<String>) {
    val fluxAndMonoGeneratorService: FluxAndMonoGeneratorService = FluxAndMonoGeneratorService()

    fluxAndMonoGeneratorService.namesFlux()
        .subscribe { name -> println("Name is :$name") }// 원소에 접근 가능, 구독

    fluxAndMonoGeneratorService.nameMono()
        .subscribe{name -> println("Mono Name is :$name")}
}
