package com.fitness.helper.app.spring.stub

import FhContext
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import com.fitness.helper.app.spring.config.ExerciseConfig
import com.fitness.helper.app.spring.controllers.ExerciseController
import com.fitness.helper.biz.FhExerciseProcessor
import ru.white.api.models.*
import toTransportCreate
import toTransportDelete
import toTransportRead
import toTransportSearch
import toTransportUpdate
import kotlin.test.Test

@WebFluxTest(ExerciseController::class, ExerciseConfig::class)
internal class ExerciseControllerTest {
    @Autowired
    private lateinit var webClient: WebTestClient

    @Suppress("unused")
    @MockBean
    private lateinit var processor: FhExerciseProcessor

    @Test
    fun createExercise() = testStubExercise(
        "/exercise/create",
        ExerciseCreateRequest(),
        FhContext().toTransportCreate().copy(responseType = "create")
    )

    @Test
    fun readExercise() = testStubExercise(
        "/exercise/read",
        ExerciseReadRequest(),
        FhContext().toTransportRead().copy(responseType = "read")
    )

    @Test
    fun updateExercise() = testStubExercise(
        "/exercise/update",
        ExerciseUpdateRequest(),
        FhContext().toTransportUpdate().copy(responseType = "update")
    )

    @Test
    fun deleteExercise() = testStubExercise(
        "/exercise/delete",
        ExerciseDeleteRequest(),
        FhContext().toTransportDelete().copy(responseType = "delete")
    )

    @Test
    fun searchExercise() = testStubExercise(
        "/exercise/search",
        ExerciseSearchRequest(),
        FhContext().toTransportSearch().copy(responseType = "search")
    )

    private inline fun <reified Req : Any, reified Res : Any> testStubExercise(
        url: String,
        requestObj: Req,
        responseObj: Res,
    ) {
        webClient
            .post()
            .uri(url)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(requestObj))
            .exchange()
            .expectStatus().isOk
            .expectBody(Res::class.java)
            .value {
                println("RESPONSE: $it")
                assertThat(it).isEqualTo(responseObj)
            }
    }
}