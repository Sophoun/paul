package com.sophoun.utils.validation

import com.sophoun.utils.validation.CreditCardValidator.CardType.UNKNOWN

class CreditCardValidator private constructor() {

    enum class CardType {
        VISA {
            override fun brand() = "VISA CARD"
            override fun pattern(): String = "4[0-9]{12}(?:[0-9]{3})?"
        },
        MASTER_CARD {
            override fun brand() = "MASTER CARD"
            override fun pattern(): String = "5[1-5][0-9]{14}"
        },
        AMERICAN_EXPRESS {
            override fun brand() = "AMERICAN EXPRESS"
            override fun pattern(): String = "3[47][0-9]{13}"
        },
        DINNER_CLUB {
            override fun brand() = "DINNER CLUB"
            override fun pattern(): String = "3(?:0[0-5]|[68][0-9])?[0-9]{11}"
        },
        DISCOVER {
            override fun brand() = "DISCOVER"
            override fun pattern(): String = "6(?:011|5[0-9]{2})[0-9]{12}"
        },
        JCB {
            override fun brand() = "JCB"
            override fun pattern(): String = "(?:2131|1800|35[0-9]{3})[0-9]{11}"
        },
        UNKNOWN {
            override fun brand() = "UNKNOWN"
            override fun pattern(): String = ""
        };

        abstract fun brand(): String
        abstract fun pattern(): String

        companion object {
            fun patternList() = listOf(
                VISA,
                MASTER_CARD,
                AMERICAN_EXPRESS,
                DINNER_CLUB,
                DISCOVER,
                JCB
            )
        }
    }

    /**
     * Util methods to validate credit card number
     */
    companion object {

        /**
         * Get credit card type
         * @param card Card number (include [-] and [space] bot not any special characters)
         * @return [CardType]
         */
        fun getCardType(card: String): CardType {
            val cardNumber =
                card.replace(" ".toRegex(), "")
                    .replace("-".toRegex(), "")
            return CardType.patternList()
                .firstOrNull { cardNumber.matches(it.pattern().toRegex()) } ?: UNKNOWN
        }

        /**
         * Check credit card number is valid
         * @param card card number (include [-] and [space] bot not any special characters)
         * @return [true] (credit card is valid)
         */
        fun isValidCard(card: String): Boolean {
            return getCardType(card) != UNKNOWN
        }
    }
}

