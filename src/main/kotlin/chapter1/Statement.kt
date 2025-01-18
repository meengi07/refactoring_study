package org.example.chapter1

import java.text.NumberFormat
import java.util.*

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

    private fun amountFor(play: Play, aPerformance: Performance): Int {
        var result = 0
        when (play.type) {
            "tragedy" -> {
                result = 40000
                if (aPerformance.audience > 30) {
                    result += 1000 * (aPerformance.audience - 30)
                }
            }

            "comedy" -> {
                result = 30000
                if (aPerformance.audience > 20) {
                    result += 1000 + 500 * (aPerformance.audience - 20)
                }
                result += 300 * aPerformance.audience
            }

            else -> throw IllegalArgumentException("알 수 없는 장르: ${play.type}")
        }
        return result
    }
}