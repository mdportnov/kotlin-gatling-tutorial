package simulations

import io.gatling.javaapi.core.CoreDsl.*
import io.gatling.javaapi.core.Simulation
import io.gatling.javaapi.http.HttpDsl.http
import io.gatling.javaapi.http.HttpDsl.status
import kotlin.time.Duration.Companion.seconds

class UserCommonSimulation : Simulation() {
    val httpConf = http.baseUrl("http://localhost:8080")

    val scn = scenario("User Simulation")
        .exec(
            http("Get User by Id")
                .get("/user/1")
                .check(status().`is`(200))
        )
        .exec(
            http("Get All Users")
                .get("/user?page=0&size=20")
                .check(status().`is`(200))
        )
        .exec(
            http("Create User")
                .post("/user")
                .header("Content-Type", "application/json")
                .body(StringBody("{\"name\": \"Alice\", \"email\": \"alice@example.com\"}"))
                .check(status().`is`(200))
        )

    init {
        setUp(
            scn.injectOpen(
                constantUsersPerSec(100.0).during(3.seconds.inWholeSeconds)
            ).protocols(httpConf)
        )
    }
}
