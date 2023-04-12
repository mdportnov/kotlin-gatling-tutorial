package simulations;

import io.gatling.javaapi.core.CoreDsl.*
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpDsl.http
import io.gatling.javaapi.http.HttpDsl.status
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration

class MySimulation : Simulation() {
    val httpProtocol = http
        .baseUrl("http://localhost:8080")
        .acceptHeader("application/json")

    val scn = scenario("My Scenario")
        .repeat(5).on(
            exec(
                http("My Request")
                    .get("/my-endpoint")
                    .check(status().`is`(200))
            )
                .pause(1.seconds.toJavaDuration())
        )
        .pace(2.seconds.toJavaDuration())

    init {
        setUp(
            scn.injectOpen(
                constantUsersPerSec(10.0).during(1.minutes.toJavaDuration())
            )
        ).protocols(httpProtocol)
    }
}
