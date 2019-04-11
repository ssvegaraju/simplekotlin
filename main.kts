// Explore a simple class

println("UW Homework: Simple Kotlin")

// write a "whenFn" that takes an arg of type "Any" and returns a String
fun whenFn(arg: Any): String {
    when(arg) {
        "Hello" -> return "world"
        is String -> return "Say what?"
        0 -> return "zero"
        1 -> return "one"
        in 2..10 -> return "low number"
        is Int -> return "a number"
        else -> return "I don't understand"
    }
}

// write an "add" function that takes two Ints, returns an Int, and adds the values
fun add(a: Int, b: Int): Int {
    return a + b
}
// write a "sub" function that takes two Ints, returns an Int, and subtracts the values
fun sub(a: Int, b: Int): Int {
    return a - b
}
// write a "mathOp" function that takes two Ints and a function (that takes two Ints and returns an Int), returns an Int, and applies the passed-in-function to the arguments
fun mathOp(a: Int, b: Int, fn: (Int, Int) -> Int): Int {
    return fn(a, b)
}

// write a class "Person" with first name, last name and age
class Person(var firstName: String, val lastName: String, var age: Int) {
    fun equals(other: Person): Boolean {
        return (firstName == other.firstName && lastName == other.lastName && age == other.age)
    }

    var hashCode: Int = 0;

    override fun hashCode(): Int {
        var result: Int = hashCode

        if (result == 0) {
            result = 17
            result = 31 * result + firstName.hashCode()
            result = 31 * result + age.hashCode()
            result = 31 * result + lastName.hashCode()
            hashCode = result
        }

        return hashCode
    }

    val debugString: String
        get() {
            return "[Person firstName:${firstName} lastName:${lastName} age:${age}]"
        }
}

// write a class "Money"
class Money() {

    constructor(amt: Int, cur: String) : this() {
        amount = amt
        currency = cur
    }

    val USDtoGBP: Double = 0.5
    val USDtoEUR: Double = 1.5
    val USDtoCAN: Double = 1.25
    val GBPtoEUR: Double = 3.0
    val GBPtoCAN: Double = 2.5
    val CANtoEUR: Double = 1.2

    private var _amount: Int = 0
    public var amount: Int
        get() {
            return _amount
        }
        set(value) {
            if (value < 0) {
                _amount = 0
            } else {
                _amount = value
            }
        }
    private var _currency: String = "USD"
    public var currency: String
        get() {
            return _currency
        }
        set(value) {
            if (value == "USD" || value != "EUR" || value != "CAN" || value != "GBP") {
                _currency = value
            }
        }

    fun convert(newCurrency: String): Money {
        when (newCurrency) {
            currency -> return Money(amount, currency)
            "GBP" -> {
                if (currency == "USD") {
                    return Money((amount * USDtoGBP).toInt(), newCurrency)
                } else if (currency == "EUR") {
                    return Money((amount * (1/GBPtoEUR)).toInt(), newCurrency)
                } else {
                    return Money((amount * (1/GBPtoCAN)).toInt(), newCurrency)
                }
            }
            "USD" -> {
                if (currency == "EUR") {
                    return Money((amount * (1/USDtoEUR)).toInt(), newCurrency)
                } else if (currency == "CAN") {
                    return Money((amount * (1/USDtoCAN)).toInt(), newCurrency)
                } else {
                    return Money((amount * (1/USDtoGBP)).toInt(), newCurrency)
                }
            }
            "EUR" -> {
                if (currency == "USD") {
                    return Money((amount * USDtoEUR).toInt(), newCurrency)
                } else if (currency == "CAN") {
                    return Money((amount * CANtoEUR).toInt(), newCurrency)
                } else {
                    return Money((amount * GBPtoEUR).toInt(), newCurrency)
                }
            }
            "CAN" -> {
                if (currency == "USD") {
                    return Money((amount * USDtoCAN).toInt(), newCurrency)
                } else if (currency == "EUR") {
                    return Money((amount * (1/CANtoEUR)).toInt(), newCurrency)
                } else {
                    return Money((amount * GBPtoCAN).toInt(), newCurrency)
                }
            }
            else -> return Money(0, "USD")
        }
    }

    operator fun plus(other: Money): Money {
        var temp: Money
        if (currency != other.currency) {
            temp = convert(currency);
        } else {
            temp = other
        }
        return Money(amount + temp.amount, currency);
    }
}

// ============ DO NOT EDIT BELOW THIS LINE =============

print("When tests: ")
val when_tests = listOf(
    "Hello" to "world",
    "Howdy" to "Say what?",
    "Bonjour" to "Say what?",
    0 to "zero",
    1 to "one",
    5 to "low number",
    9 to "low number",
    17.0 to "I don't understand"
)
for ((k,v) in when_tests) {
    print(if (whenFn(k) == v) "." else "!")
}
println("")

print("Add tests: ")
val add_tests = listOf(
    Pair(0, 0) to 0,
    Pair(1, 2) to 3,
    Pair(-2, 2) to 0,
    Pair(123, 456) to 579
)
for ( (k,v) in add_tests) {
    print(if (add(k.first, k.second) == v) "." else "!")
}
println("")

print("Sub tests: ")
val sub_tests = listOf(
    Pair(0, 0) to 0,
    Pair(2, 1) to 1,
    Pair(-2, 2) to -4,
    Pair(456, 123) to 333
)
for ( (k,v) in sub_tests) {
    print(if (sub(k.first, k.second) == v) "." else "!")
}
println("")

print("Op tests: ")
print(if (mathOp(2, 2, { l,r -> l+r} ) == 4) "." else "!")
print(if (mathOp(2, 2, ::add ) == 4) "." else "!")
print(if (mathOp(2, 2, ::sub ) == 0) "." else "!")
print(if (mathOp(2, 2, { l,r -> l*r} ) == 4) "." else "!")
println("")


print("Person tests: ")
val p1 = Person("Ted", "Neward", 47)
print(if (p1.firstName == "Ted") "." else "!")
p1.age = 48
print(if (p1.debugString == "[Person firstName:Ted lastName:Neward age:48]") "." else "!")
println("")

print("Money tests: ")
val tenUSD = Money(10, "USD")
val twelveUSD = Money(12, "USD")
val fiveGBP = Money(5, "GBP")
val fifteenEUR = Money(15, "EUR")
val fifteenCAN = Money(15, "CAN")
val convert_tests = listOf(
    Pair(tenUSD, tenUSD),
    Pair(tenUSD, fiveGBP),
    Pair(tenUSD, fifteenEUR),
    Pair(twelveUSD, fifteenCAN),
    Pair(fiveGBP, tenUSD),
    Pair(fiveGBP, fifteenEUR)
)
for ( (from,to) in convert_tests) {
    print(if (from.convert(to.currency).amount == to.amount) "." else "!")
}
val moneyadd_tests = listOf(
    Pair(tenUSD, tenUSD) to Money(20, "USD"),
    Pair(tenUSD, fiveGBP) to Money(20, "USD"),
    Pair(fiveGBP, tenUSD) to Money(10, "GBP")
)
for ( (pair, result) in moneyadd_tests) {
    print(if ((pair.first + pair.second).amount == result.amount &&
              (pair.first + pair.second).currency == result.currency) "." else "!")
}
println("")
