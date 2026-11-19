package io.github.aj8gh.aok.y20.d5

private const val ROW_MULTIPLIER = 8
private const val MAX_ROW = 127
private const val MAX_SEAT = 7
private const val BACK = 'B'
private const val FRONT = 'F'
private const val LEFT = 'L'
private const val RIGHT = 'R'

fun part1(input: List<String>) = input.maxOf { findSeatIdBinaryDecoding(it) }

fun part2(input: List<String>): Int {
  val seatIds = IntArray(MAX_ROW * ROW_MULTIPLIER + MAX_SEAT + 1)
  for (search in input) seatIds[findSeatIdBinaryDecoding(search)] = 1
  for (i in seatIds.lastIndex - 1 downTo 1)
    if (seatIds[i] == 0 && seatIds[i + 1] == 1 && seatIds[i - 1] == 1)
      return i
  error("Not found")
}

private fun findSeatIdBinaryDecoding(search: String) =
  search.map { if (it == FRONT || it == LEFT) '0' else '1' }
    .joinToString("")
    .toInt(2)

private fun findSeatIdBinarySearch(search: String): Int {
  var rowMinBound = 0
  var rowMaxBound = MAX_ROW
  var seatMinBound = 0
  var seatMaxBound = MAX_SEAT
  for (char in search) {
    when (char) {
      BACK -> rowMinBound += (rowMaxBound - rowMinBound) / 2 + 1
      FRONT -> rowMaxBound -= ((rowMaxBound - rowMinBound) / 2) + 1
      RIGHT -> seatMinBound += ((seatMaxBound - seatMinBound) / 2) + 1
      LEFT -> seatMaxBound -= ((seatMaxBound - seatMinBound) / 2) + 1
    }
  }
  return rowMinBound * ROW_MULTIPLIER + seatMaxBound
}
