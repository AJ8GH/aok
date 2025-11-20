package io.github.aj8gh.aok.y24.d17

import kotlin.math.pow

data class Registers(
  var a: Int,
  var b: Int,
  var c: Int,
)

val digits = Regex("\\d+")
const val TWO = 2.0

fun part1(input: List<String>): String {
  val nums = input.map { digits.findAll(it).map { it.value.toInt() }.toList() }
    .filter { it.isNotEmpty() }
  val reg = nums.slice(0..2).flatten().let { Registers(it[0], it[1], it[2]) }
  val program = nums.last()
  val out = mutableListOf<Int>()

  var i = 0
  while (i < program.lastIndex) {
    val opcode = program[i]
    val operand = program[i + 1]
    when (opcode) {
      0 -> reg.a /= TWO.pow(combo(reg, operand).toDouble()).toInt()
      1 -> reg.b = reg.b xor operand
      2 -> reg.b = combo(reg, operand) % 8
      3 -> {
        if (reg.a != 0) {
          i = operand / 2
          continue
        }
      }

      4 -> reg.b = reg.b xor reg.c
      5 -> out.add(combo(reg, operand) % 8)
      6 -> reg.b /= TWO.pow(combo(reg, operand).toDouble()).toInt()
      7 -> reg.c /= TWO.pow(combo(reg, operand).toDouble()).toInt()
    }
    i += 2
  }
  return out.joinToString(",")
}

fun part2(input: List<String>): String = TODO()

private fun combo(
  registers: Registers,
  operand: Int
) = when (operand) {
  4 -> registers.a
  5 -> registers.b
  6 -> registers.c
  else -> operand
}
