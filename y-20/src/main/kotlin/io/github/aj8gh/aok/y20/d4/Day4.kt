package io.github.aj8gh.aok.y20.d4

import io.github.oshai.kotlinlogging.KotlinLogging

private const val BIRTH_YEAR = "byr"
private const val ISSUE_YEAR = "iyr"
private const val EXPIRATION_YEAR = "eyr"
private const val HEIGHT = "hgt"
private const val CM = "cm"
private const val INCHES = "in"
private const val HAIR_COLOUR = "hcl"
private const val EYE_COLOUR = "ecl"
private const val PASSPORT_ID = "pid"
private const val COUNTRY_ID = "cid"
private const val SPACE = " "
private const val COLON = ":"

private val VALID_EYE_COLOURS = setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
private val VALID_HAIR_COLOUR_PATTERN = Regex("#[0-9a-f]{6}")
private val VALID_BIRTH_YEARS = 1920..2002
private val VALID_ISSUE_YEARS = 2010..2020
private val VALID_EXPIRATION_YEARS = 2020..2030
private val VALID_CMS = 150..193
private val VALID_INCHES = 59..76
private val VALID_PASSPORT_ID_PATTERN = Regex("[0-9]{9}")

private val log = KotlinLogging.logger { }

fun part1(input: List<String>) = countValid(input)

fun part2(input: List<String>) = countValid(input, true)

private fun countValid(
  input: List<String>,
  validateValues: Boolean = false
): Int {
  var valid = 0
  var fields = 0

  for (i in input.indices) {
    if (input[i].isNotEmpty()) {
      for (field in input[i].split(SPACE)) {
        if (validateValues && !isValid(field)) break
        if (!field.startsWith(COUNTRY_ID)) fields++
      }
    }
    if (i == input.lastIndex || input[i].isEmpty()) {
      if (fields >= 7) valid++
      fields = 0
      continue
    }
  }
  return valid
}

private fun isValid(field: String): Boolean {
  val (key, value) = field.split(COLON)
  return when (key) {
    BIRTH_YEAR -> value.toIntOrNull() in VALID_BIRTH_YEARS
    ISSUE_YEAR -> value.toIntOrNull() in VALID_ISSUE_YEARS
    EXPIRATION_YEAR -> value.toIntOrNull() in VALID_EXPIRATION_YEARS
    HEIGHT -> if (value.endsWith(CM)) value.substring(0, value.length - 2).toIntOrNull() in VALID_CMS
    else value.substring(0, value.length - 2).toIntOrNull() in VALID_INCHES

    HAIR_COLOUR -> VALID_HAIR_COLOUR_PATTERN.matches(value)
    EYE_COLOUR -> value in VALID_EYE_COLOURS
    PASSPORT_ID -> VALID_PASSPORT_ID_PATTERN.matches(value)
    COUNTRY_ID -> true
    else -> false
  }
}
