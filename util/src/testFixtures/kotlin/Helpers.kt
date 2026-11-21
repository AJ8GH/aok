import io.github.aj8gh.aok.util.InputReader.Reader.readExample
import io.github.aj8gh.aok.util.InputReader.Reader.readInput
import org.junit.jupiter.params.provider.Arguments

fun getInput(day: Int, vararg answers: Any) =
  answers.mapIndexed { idx, ans ->
    when (idx) {
      answers.lastIndex -> Arguments.of(readInput(day), ans)
      0 -> Arguments.of(readExample(day), ans)
      else -> Arguments.of(readExample(day, idx + 1), ans)
    }
  }
