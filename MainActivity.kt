import java.text.SimpleDateFormat
import java.util.*

fun main() {
    val tracker = ExpenseTracker()
    val scanner = Scanner(System.`in`)

    while (true) {
        println("1. Show Balance\n2. Add Expense\n3. Add Income\n4. Undo Last Transaction\n5. Show History\n6. Add Category\n7. Show Balance By Category\n8. Show Balance By Period\n9. Exit")
        println("Enter command number:")
        when (scanner.nextInt()) {
            1 -> tracker.showBalance()
            2 -> {
                println("Enter amount:")
                val amount = scanner.nextDouble()
                // Consume the next line character
                scanner.nextLine()
                println("Enter category (optional, press enter to skip):")
                val category = scanner.nextLine().trim()
                tracker.addTransaction(-amount, category)
            }
            3 -> {
                println("Enter amount:")
                val amount = scanner.nextDouble()
                tracker.addTransaction(amount, "")
            }
            4 -> tracker.undoLastTransaction()
            5 -> tracker.showHistory()
            6 -> {
                println("Enter new category name:")
                val category = scanner.next()
                tracker.addCategory(category)
            }
            7 -> {
                println("Enter category name:")
                val category = scanner.next()
                tracker.showBalanceByCategory(category)
            }
            8 -> {
                println("Enter period (hour/day/month):")
                val period = scanner.next()
                tracker.showBalanceByPeriod(period)
            }
            9 -> return
            else -> {
                println("Invalid command, please try again.")
                scanner.nextLine() // Consume the invalid input
            }
        }
    }
}

class ExpenseTracker {
    private var balance: Double = 0.0
    private val transactions: MutableList<Transaction> = mutableListOf()
    private val categories: MutableList<String> = mutableListOf("Food", "Transport", "Utilities")
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    fun addTransaction(amount: Double, category: String) {
        if (category.isNotEmpty() && !categories.contains(category)) {
            println("Category does not exist. Please add it first.")
            return
        }
        val transaction = Transaction(amount, if (category.isEmpty()) "General" else category, Date())
        transactions.add(transaction)
        balance += amount
        println("Transaction added successfully.")
    }

    fun showBalance() {
        println("Current balance: $balance")
    }

    fun undoLastTransaction() {
        if (transactions.isNotEmpty()) {
            val lastTransaction = transactions.removeAt(transactions.size - 1)
            balance -= lastTransaction.amount
            println("Last transaction undone.")
        } else {
            println("No transactions to undo.")
        }
    }

    fun showHistory() {
        transactions.forEach {
            println("${dateFormat.format(it.dateTime)}: ${if (it.amount < 0) "Expense" else "Income"} of ${it.amount} in category ${it.category}")
        }
    }

    fun addCategory(category: String) {
        if (!categories.contains(category)) {
            categories.add(category)
            println("Category added successfully.")
        } else {
            println("Category already exists.")
        }
    }

    fun showBalanceByCategory(category: String) {
        if (!categories.contains(category)) {
            println("Category does not exist.")
            return
        }
        val categoryBalance = transactions.filter { it.category == category }.sumOf { it.amount }
        println("Balance for $category: $categoryBalance")
    }

    fun showBalanceByPeriod(period: String) {
        val now = Date()
        val periodStart = when (period.lowercase()) {
            "hour" -> Date(now.time - 3600 * 1000)
            "day" -> Date(now.time - 24 * 3600 * 1000)
            "month" -> Date(now.time - 30L * 24 * 3600 * 1000) // Approximation
            else -> {
                println("Invalid period. Use 'hour', 'day', or 'month'.")
                return
            }
        }
        val periodBalance = transactions.filter { it.dateTime.after(periodStart) && it.dateTime.before(now) }.sumOf { it.amount }
        println("Balance for the last $period: $periodBalance")
    }
}

data class Transaction(val amount: Double, val category: String, val dateTime: Date)
