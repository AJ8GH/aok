package io.github.aj8gh.aok.y24.d9

data class File(var id: Int, var size: Int)

fun part1(input: List<String>) = solve(input.first())

fun part2(input: List<String>) = 0L

private fun solve(input: String): Long {
  var total = 0L

  var i = 0
  var finalIndex = 0
  var j = input.length - 1
  var endFile = File(id = j / 2, size = input[j].digitToInt())
  val processedI = mutableSetOf<Int>()
  val processedJ = mutableSetOf<Int>()
  var reformated = ""

  while (i < input.length && j > 0) {
    if (i % 2 == 0) {
      if (processedJ.contains(i)) {
        i++
        continue
      }
      val startFile = File(id = i / 2, size = input[i].digitToInt())
      repeat(startFile.size) {
        reformated += startFile.id
        total += startFile.id * finalIndex++
      }
      processedI.add(i)
    } else {
      if (processedI.contains(j)) {
        i++
        continue
      }
      processedJ.add(j)
      var space = input[i].digitToInt()
      end@ while (space-- > 0) {
        if (endFile.size <= 0) {
          j -= 2
          if (processedI.contains(j)) {
            break@end
          }
          endFile = File(id = j / 2, size = input[j].digitToInt())
        }
        reformated += endFile.id
        total += endFile.id * finalIndex++
        endFile.size--
      }
    }
    i++
  }
  return total
}
