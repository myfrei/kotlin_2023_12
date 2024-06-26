import com.fitness.helper.api.v1.models.*
import com.fitness.helper.apiV1Mapper
import org.junit.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import java.math.BigDecimal

class ResponseV1serializationTest {
    private val response = UserResponse(
        responseType = "user",
        result = ResponseResult.SUCCESS,
        errors = listOf(),
        user = User(
            id = "5",
            fullName = "John Doe",
            email = "john.doe@test.com",
            dateOfBirth = "1990-01-01",
            gender = "male",
            height = BigDecimal("180.0"),
            weight = BigDecimal("75.0"),
            activityLevel = "medium"
        )
    )

    @Test
    fun serializationTest() {
        val json = apiV1Mapper.writeValueAsString(response)

        assertContains(json, Regex("\"responseType\":\\s*\"user\""))
        assertContains(json, Regex("\"result\":\\s*\"success\""))
        assertContains(json, Regex("\"errors\":\\s*\\[]"))
        assertContains(json, Regex("\"id\":\\s*\"5\""))
        assertContains(json, Regex("\"fullName\":\\s*\"John Doe\""))
        assertContains(json, Regex("\"email\":\\s*\"john.doe@test.com\""))
        assertContains(json, Regex("\"dateOfBirth\":\\s*\"1990-01-01\""))
        assertContains(json, Regex("\"gender\":\\s*\"male\""))
        assertContains(json, Regex("\"height\":\\s*180.0"))
        assertContains(json, Regex("\"weight\":\\s*75.0"))
        assertContains(json, Regex("\"activityLevel\":\\s*\"medium\""))
    }

    @Test
    fun deserializationTest() {
        val json = apiV1Mapper.writeValueAsString(response)
        val obj = apiV1Mapper.readValue(json, IResponse::class.java) as UserResponse

        assertEquals(response, obj)
    }
}