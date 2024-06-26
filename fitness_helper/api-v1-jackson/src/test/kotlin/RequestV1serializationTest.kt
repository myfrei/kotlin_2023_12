import com.fitness.helper.api.v1.models.*
import com.fitness.helper.apiV1Mapper
import org.junit.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import java.math.BigDecimal

class RequestV1serializationTest {
    private val request = UserRegistrationRequest(
        requestType = "register",
        fullName = "John Doe",
        email = "john.doe@test.com",
        password = "password123",
        dateOfBirth = "1990-01-01",
        gender = "male",
        height = BigDecimal("180.0"),
        weight = BigDecimal("75.0"),
        activityLevel = "medium"
    )

    @Test
    fun `serialize request`() {
        val json = apiV1Mapper.writeValueAsString(request)

        assertContains(json, Regex("\"requestType\":\\s*\"register\""))
        assertContains(json, Regex("\"fullName\":\\s*\"John Doe\""))
        assertContains(json, Regex("\"email\":\\s*\"john.doe@test.com\""))
        assertContains(json, Regex("\"password\":\\s*\"password123\""))
        assertContains(json, Regex("\"dateOfBirth\":\\s*\"1990-01-01\""))
        assertContains(json, Regex("\"gender\":\\s*\"male\""))
        assertContains(json, Regex("\"height\":\\s*180.0"))
        assertContains(json, Regex("\"weight\":\\s*75.0"))
        assertContains(json, Regex("\"activityLevel\":\\s*\"medium\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(request)
        val obj = apiV1Mapper.readValue(json, IRequest::class.java) as UserRegistrationRequest

        assertEquals(request, obj)
    }

    @Test
    fun deserializeObj() {
        val jsonString = """
            {
                "requestType": "register",
                "fullName": "John Doe",
                "email": "john.doe@test.com",
                "password": "password123",
                "dateOfBirth": "1990-01-01",
                "gender": "male",
                "height": 180.0,
                "weight": 75.0,
                "activityLevel": "medium"
            } 
        """.trimIndent()

        val obj = apiV1Mapper.readValue(jsonString, UserRegistrationRequest::class.java)

        assertEquals(request, obj)
    }
}