package simulations

import io.gatling.javaapi.core.CoreDsl.*
import io.gatling.javaapi.core.Simulation
import io.gatling.javaapi.http.HttpDsl.http
import io.gatling.javaapi.http.HttpDsl.status
import io.gatling.javaapi.http.HttpProtocolBuilder

class UserPaginationSimulation : Simulation() {
    private val httpConf: HttpProtocolBuilder = http
        .baseUrl("http://localhost:8080")
        .acceptHeader("application/json")

    private val scn = scenario("UserPagination")
        .exec { session ->
            session.set("page", 0)
        }
        .repeat(3, "n").on(
            exec(
                http("GetUsers")
                    .get("/user")
                    .queryParam("page", "#{page}")
                    .queryParam("size", "10")
                    .check(status().`is`(200))
            )
                .exec { session ->
                    val currentPage = if (session.contains("page")) session.getInt("page") else 0
                    session.set("page", currentPage + 1)
                }
                .pause(2)
        )

    init {
        setUp(
            scn.injectOpen(
                constantUsersPerSec(10.0).during(10)
            )
        ).protocols(httpConf)
    }
}
