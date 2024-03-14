import org.junit.jupiter.api.Assertions
import ru.myfrei.com.printText
import kotlin.test.Test

class SimpleTextTest {
    @Test
    fun testGreetingText() {
        Assertions.assertNotEquals( "Hello world", printText())
    }
}