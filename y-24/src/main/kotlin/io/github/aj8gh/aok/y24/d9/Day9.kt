package io.github.aj8gh.aok.y24.d9

import java.util.ArrayDeque

data class File(
  var id: Int,
  var size: Int,
  val moved: MutableList<File> = mutableListOf(),
  var processed: Boolean = false,
)

fun part1(input: List<String>) = solve(input.first())

fun part2(input: List<String>) = solveLevel2(input.first())

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

private fun solveLevel2(input: String): Long {
  var total = 0L
  var formatted = ""

  val files = mutableListOf<File>()
  for (i in input.indices) {
    val fileId = if (i % 2 == 0) i / 2 else -1
    files.add(File(fileId, input[i].digitToInt()))
  }

  for (i in files.lastIndex downTo 0) {
    val file = files[i]
    if (file.processed || file.id == -1) {
      continue
    }

    spaces@ for (block in files) {
      if (block.id == -1 && block.size >= file.size) {
        block.moved.add(File(file.id, file.size))
        file.processed = true
        block.size -= file.size
        break@spaces
      }
    }
  }

  var i = 0
  for (file in files) {
    if (file.id == -1) {
      for (m in file.moved) {
        repeat(m.size) {
          total += i * m.id
          formatted += m.id
          i++
        }
      }
      repeat(file.size) {
        formatted += "."
        i++
      }
    } else {
      if (file.processed) {
        repeat(file.size) {
          formatted += "."
          i++
        }
      } else {
        repeat(file.size) {
          total += i * file.id
          formatted += file.id
          i++
        }
      }
    }
  }

  return total
}
