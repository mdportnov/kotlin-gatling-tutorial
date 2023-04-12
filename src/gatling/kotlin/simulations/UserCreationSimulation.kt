package simulations

import io.gatling.javaapi.core.CoreDsl.*
import io.gatling.javaapi.core.OpenInjectionStep
import io.gatling.javaapi.core.ScenarioBuilder
import io.gatling.javaapi.core.Simulation
import io.gatling.javaapi.http.HttpDsl.http
import io.gatling.javaapi.http.HttpDsl.status
import io.gatling.javaapi.http.HttpProtocolBuilder
import kotlin.time.Duration.Companion.seconds

class UserCreationSimulation : Simulation() {
    private val httpConf: HttpProtocolBuilder = http
        .baseUrl("http://localhost:8080")
        .acceptHeader("application/json")
        .contentTypeHeader("application/json")

    private val userCreationJson = """
        {
            "name": "John",
            "email": "john.doe@example.com",
        }
    """.trimIndent()

    private val scn: ScenarioBuilder = scenario("UserCreationSimulation")
        .exec(
            http("Create User")
                .post("/users")
                .body(StringBody(userCreationJson)).asJson()
                .check(status().`is`(201))
        )

    init {
        val injectionStep: OpenInjectionStep = constantUsersPerSec(10.0).during(1.seconds.inWholeSeconds)
        setUp(scn.injectOpen(injectionStep).protocols(httpConf))
    }
}
