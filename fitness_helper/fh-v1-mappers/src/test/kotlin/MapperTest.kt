import com.fitness.helper.FitnessContext
import com.fitness.helper.api.v1.models.*
import com.fitness.helper.fromTransport
import com.fitness.helper.models.*
import junit.framework.TestCase.assertEquals
import org.junit.Test
import kotlin.test.assertNotNull
import java.math.BigDecimal

class MapperTest {

    @Test
    fun fromTransportUserRegistrationRequest() {
        val request = UserRegistrationRequest(
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

        val context = FitnessContext()
        context.fromTransport(request)

        assertNotNull(context.userRequest)
        assertEquals("John Doe", context.userRequest.fullName)
        assertEquals("john.doe@test.com", context.userRequest.email)
        assertEquals("password123", context.userRequest.password)
        assertEquals("1990-01-01", context.userRequest.dateOfBirth)
        assertEquals("male", context.userRequest.gender)
        assertEquals(BigDecimal("180.0"), context.userRequest.height)
        assertEquals(BigDecimal("75.0"), context.userRequest.weight)
        assertEquals("medium", context.userRequest.activityLevel)
    }

    @Test
    fun fromTransportUserLoginRequest() {
        val request = UserLoginRequest(
            requestType = "login",
            email = "john.doe@test.com",
            password = "password123"
        )

        val context = FitnessContext()
        context.fromTransport(request)

        assertNotNull(context.userRequest)
        assertEquals("john.doe@test.com", context.userRequest.email)
        assertEquals("password123", context.userRequest.password)
    }

    @Test
    fun fromTransportFoodEntryRequest() {
        val request = FoodEntryRequest(
            requestType = "addFoodEntry",
            userId = 1,
            foodName = "Apple",
            calories = 52.0,
            proteins = 0.3,
            fats = 0.2,
            carbohydrates = 14.0
        )

        val context = FitnessContext()
        context.fromTransport(request)

        assertNotNull(context.foodEntryRequest)
        assertEquals(UserId("1"), context.foodEntryRequest.userId)
        assertEquals("Apple", context.foodEntryRequest.foodName)
        assertEquals(52.0, context.foodEntryRequest.calories)
        assertEquals(0.3, context.foodEntryRequest.proteins)
        assertEquals(0.2, context.foodEntryRequest.fats)
        assertEquals(14.0, context.foodEntryRequest.carbohydrates)
    }
}