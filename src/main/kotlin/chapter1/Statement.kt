package org.example.chapter1

import java.text.NumberFormat
import java.util.*
import kotlin.math.floor

class Statement {

    fun statement(
        invoice: Invoice,
        plays: Map<String, Play>
    ): String {
        var totalAmount = 0
        var volumeCredits = 0
        var result = "청구 내역 (고객명: ${invoice.customer})\n"
        val format = NumberFormat.getCurrencyInstance(Locale.US)

        invoice.performances.forEach { perf ->
            val play = plays[perf.playId]!!
            val thisAmount = amountFor(play, perf)

            volumeCredits += maxOf(perf.audience - 30, 0)

            if ("comedy" == play.type) {
                volumeCredits += perf.audience / 5
            }
            result += "${play.name}: ${format.format(thisAmount / 100.0)} (${perf.audience} 석)\n"
            totalAmount += thisAmount
        }

        result += "총액: ${format.format(totalAmount / 100.0)}\n"
        result += "적립 포인트: ${volumeCredits}점\n"
        return result
    }

    private fun amountFor(play: Play, perf: Performance): Int {
        var thisAmount = 0
        when (play.type) {
            "tragedy" -> {
                thisAmount = 40000
                if (perf.audience > 30) {
                    thisAmount += 1000 * (perf.audience - 30)
                }
            }

            "comedy" -> {
                thisAmount = 30000
                if (perf.audience > 20) {
                    thisAmount += 1000 + 500 * (perf.audience - 20)
                }
                thisAmount += 300 * perf.audience
            }

            else -> throw IllegalArgumentException("알 수 없는 장르: ${play.type}")
        }
        return thisAmount
    }
}