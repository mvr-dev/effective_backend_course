package dev.effective.academy.blog

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTests(@Autowired val restTemplate: TestRestTemplate) {
    companion object {
        @JvmStatic
        @BeforeAll
        fun setup(): Unit {
            println(">>setup")
        }

        @JvmStatic
        @AfterAll
        fun teardown(): Unit {
            println(">> tear down")
        }
    }
    @Test
    fun `Assert blog page title, content and status code`(){
        println(">> Assert blog page title, content and status code");
        val entity = restTemplate.getForEntity<String>("/")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).contains("<h1>Blog</h1>")

    }



    @Test
    fun `Assert article page title, content and status code`() {
        println(">> TODO")
    }

}