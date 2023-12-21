package com.github.aj8gh.aok.util

class Reader {
  fun read(file: String): List<String> {
    return this::class
      .java
      .classLoader
      .getResourceAsStream(file)!!.bufferedReader()
      .readLines()
  }
}
