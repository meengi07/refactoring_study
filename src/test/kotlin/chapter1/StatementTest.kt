package chapter1

import kotlinx.serialization.json.Json
import org.example.chapter1.Invoice
import org.example.chapter1.Play
import org.example.chapter1.Statement
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.io.File

class StatementTest {

    @Test
    fun fileReadTest() {
        val invoiceJson: String = File("src/main/resources/json/invoices.json").readText()
        val invoice = Json.decodeFromString<List<Invoice>>(invoiceJson)

        val playJson: String = File("src/main/resources/json/plays.json").readText()
        val play = Json.decodeFromString<Map<String, Play>>(playJson)

        assertEquals(invoice.size, 1)
        assertEquals(play.size, 3)
    }

    @Test
    fun statementTest() {
        val invoiceJson: String = File("src/main/resources/json/invoices.json").readText()
        val invoice = Json.decodeFromString<List<Invoice>>(invoiceJson)

        val playJson: String = File("src/main/resources/json/plays.json").readText()
        val play = Json.decodeFromString<Map<String, Play>>(playJson)

        val statement = Statement()
        val result = statement.statement(invoice[0], play).trimIndent()

        val text = """
            청구 내역 (고객명: BigCo)
            Hamlet: $650.00 (55 석)
            As You Like it: $490.00 (35 석)
            Othello: $500.00 (40 석)
            총액: $1,640.00
            적립 포인트: 47점
        """.trimIndent()
        assertEquals(result, text)
    }
}