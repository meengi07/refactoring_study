package org.example.chapter1

import java.text.NumberFormat
import java.util.*

class Statement {

    fun statement(
        invoice: Invoice,
        plays: Map<String, Play>
    ): String {
        fun playFor(aPerformance: Performance): Play {
            return plays[aPerformance.playId]!!
        }

        fun amountFor(aPerformance: Performance): Int {
            var result = 0
            when (playFor(aPerformance).type) {
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

                else -> throw IllegalArgumentException("알 수 없는 장르: ${playFor(aPerformance).type}")
            }
            return result
        }

        fun volumeCreditsFor(aPerformance: Performance): Int {
            var result = 0
            result += maxOf(aPerformance.audience - 30, 0)

            if ("comedy" == playFor(aPerformance).type) {
                result += aPerformance.audience / 5
            }
            return result
        }

        var totalAmount = 0
        var volumeCredits = 0
        var result = "청구 내역 (고객명: ${invoice.customer})\n"
        val format = NumberFormat.getCurrencyInstance(Locale.US)

        invoice.performances.forEach { aPerformance ->
            volumeCredits += volumeCreditsFor(aPerformance)

            result += "${playFor(aPerformance).name}: ${format.format(amountFor(aPerformance) / 100.0)} (${aPerformance.audience} 석)\n"
            totalAmount += amountFor(aPerformance)
        }

        result += "총액: ${format.format(totalAmount / 100.0)}\n"
        result += "적립 포인트: ${volumeCredits}점\n"
        return result
    }


}