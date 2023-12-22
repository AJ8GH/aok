package com.github.aj8gh.aok.y23.d1;

  private val regexes = listOf(
    Regex("\\D?(\\d).*"),
    Regex(".*(\\d)\\D?"),
  )

  fun part1(input: List<String>): Int =
    input.map { line ->
      regexes.map { regex ->
        regex.find(line)
          ?.groupValues
          ?.get(1)
      }.joinToString("")
    }.sumOf { s -> s.toInt() }

  fun part2(input: List<String>): Int = 0
