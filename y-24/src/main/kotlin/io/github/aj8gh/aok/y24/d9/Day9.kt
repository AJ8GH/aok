package io.github.aj8gh.aok.y24.d9

import io.github.aj8gh.aok.y24.d9.BlockType.FILE
import io.github.aj8gh.aok.y24.d9.BlockType.FREE
import java.util.ArrayDeque

data class Block(
  var id: Int,
  var size: Int,
  val type: BlockType = FILE,
  var processed: Boolean = false,
)

enum class BlockType {
  FILE,
  FREE,
}

fun part1(input: List<String>) = solve(input.first())

fun part2(input: List<String>) = solveLevel2(input.first())

private fun solve(input: String): Long {
  var formatted = ""
  val files = ArrayDeque<Block>()
  val spaces = ArrayDeque<Block>()
  for (i in input.indices) {
    if (i % 2 == 0) {
      files.add(Block(i / 2, input[i].digitToInt()))
    } else {
      spaces.add(Block(-1, input[i].digitToInt()))
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
  val blocks = mutableListOf<Block>()
  for (i in input.indices) {
    val type = if (i % 2 == 0) FILE else FREE
    blocks.add(Block(id = i / 2, type = type, size = input[i].digitToInt()))
  }

  val rearranged = mutableListOf<Block>()
  for (i in blocks.indices) {
    val block = blocks[i]
    when (block.type) {
      FILE -> {
        rearranged.add(block)
        block.processed = true
      }

      FREE -> {
        move@ for (j in blocks.lastIndex downTo i + 1) {
          val toMove = blocks[j]
          if (!toMove.processed && block.size >= toMove.size && toMove.type == FILE) {
            rearranged.add(toMove)
            toMove.processed = true
            block.size -= toMove.size
            blocks[j] = Block(id = toMove.id, size = toMove.size, type = FREE)
            if (block.size <= 0) {
              break@move
            }
          }
        }
        if (block.size > 0) {
          rearranged.add(block)
        }
      }
    }
  }

  var total = 0L
  var i = 0
  for (b in rearranged) {
    when (b.type) {
      FILE -> repeat(b.size) { total += i++ * b.id }
      FREE -> i += b.size
    }
  }
  return total
}
