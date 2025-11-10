package io.github.aj8gh.aok.y24.d9

import java.util.ArrayDeque

data class File(var id: Int, var size: Int)

fun part1(input: List<String>) = solve(input.first())

fun part2(input: List<String>) = 0L

private fun solve(input: String): Long {
  var formatted = ""
  val files = ArrayDeque<File>()
  val spaces = ArrayDeque<File>()
  for (i in input.indices) {
    if (i % 2 == 0) {
      files.add(File(i / 2, input[i].digitToInt()))
    } else {
      spaces.add(File(-1, input[i].digitToInt()))
    }
  }

  var i = 0
  var index = 0
  var total = 0L
  while (files.isNotEmpty()) {
    if (i % 2 == 0) {
      val fileFromBottom = files.removeFirst()
      repeat(fileFromBottom.size) {
        total += fileFromBottom.id * index
        index++
        formatted += fileFromBottom.id
      }
    } else {
      val space = spaces.removeFirst()
      var fileFromTop = files.last
      while (space.size > 0) {
        if (fileFromTop.size <= 0) {
          if (files.size == 1) {
            break
          }
          files.removeLast()
          fileFromTop = files.last
        }
        total += fileFromTop.id * index
        fileFromTop.size--
        space.size--
        index++
        formatted += fileFromTop.id
      }
    }
    i++
  }

  return total
}
