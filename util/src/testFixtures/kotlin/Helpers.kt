import io.github.aj8gh.aok.util.InputReader.Reader.readExample
import io.github.aj8gh.aok.util.InputReader.Reader.readInput
import org.junit.jupiter.params.provider.Arguments

fun getInput(day: Int, vararg answers: Int) =
  answers.mapIndexed { idx, ans ->
    when (idx) {
      0 -> Arguments.of(readExample(day), ans)
      answers.lastIndex -> Arguments.of(readInput(day), ans)
      else -> Arguments.of(readExample(day, idx + 1), ans)
    }
  }
