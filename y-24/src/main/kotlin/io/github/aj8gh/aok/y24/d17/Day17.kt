package io.github.aj8gh.aok.y24.d17

import kotlin.math.pow

data class Registers(
  var a: Long,
  var b: Long,
  var c: Long,
)

const val TWO = 2.0

val digits = Regex("\\d+")

fun part1(input: List<String>) = parse(input)
  .let { runProgram(it.first, it.second) }
  .joinToString(",")

fun part2(input: List<String>): Long {
  val parsed = parse(input)
  val program = parsed.second
  var initialA = 0L
  var digitsToCompare = 1

  while (true) {
    val out = runProgram(parsed.first, program, initialA)
    if (out == program) {
      return initialA
    }
    if (
      out.size > digitsToCompare
      && out.last() == program.last()
      && out.slice((out.lastIndex - digitsToCompare)..out.lastIndex)
      == program.slice((program.lastIndex - digitsToCompare)..program.lastIndex)
    ) {
      digitsToCompare++
      initialA *= 8
      continue
    }

    initialA++
  }
}

private fun combo(
  registers: Registers,
  operand: Long
) = when (operand) {
  4L -> registers.a
  5L -> registers.b
  6L -> registers.c
  else -> operand
}

private fun runProgram(reg: Registers, program: List<Long>, a: Long? = null): List<Long> {
  val out = mutableListOf<Long>()
  var i = 0
  if (a != null) reg.a = a
  while (i < program.lastIndex) {
    val opcode = program[i]
    val operand = program[i + 1]
    when (opcode) {
      0L -> reg.a /= TWO.pow(combo(reg, operand).toDouble()).toLong()
      1L -> reg.b = reg.b xor operand
      2L -> reg.b = combo(reg, operand) % 8
      3L -> {
        if (reg.a != 0L) {
          i = operand.toInt() / 2
          continue
        }
      }

      4L -> reg.b = reg.b xor reg.c
      5L -> out.add(combo(reg, operand) % 8L)
      6L -> reg.b = reg.a / TWO.pow(combo(reg, operand).toDouble()).toLong()
      7L -> reg.c = reg.a / TWO.pow(combo(reg, operand).toDouble()).toLong()
    }
    i += 2
  }
  return out
}

private fun parse(input: List<String>): Pair<Registers, List<Long>> {
  val nums = input.map { digits.findAll(it).map { r -> r.value.toLong() }.toList() }
    .filter { it.isNotEmpty() }
  val reg = nums.slice(0..2).flatten().let { Registers(it[0], it[1], it[2]) }
  val program = nums.last()
  return Pair(reg, program)
}
