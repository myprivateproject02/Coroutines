import kotlinx.coroutines.*
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.concurrent.thread

fun main() {
    println("Main Program = :${Thread.currentThread().name}")
    launchingWithContext()
}

suspend fun printDelay(message: String) {
    delay(1000)
    println(message)
}
fun exampleBlocks() = runBlocking {
    println("one")
    printDelay("two")
    println("three")
}
fun exampleBloakingDispatcher() {
    runBlocking(Dispatchers.Default) {
        println("one ${Thread.currentThread().name}")
        printDelay("two ${Thread.currentThread().name}")
    }
    println("Three ${Thread.currentThread().name}")
}
fun exampleLaunchingGlobal() = runBlocking {
    println("One ${Thread.currentThread().name}")
    GlobalScope.launch {
        println("Two ${Thread.currentThread().name}")
    }
    println("Three ${Thread.currentThread().name}")
    delay(3000)
}
fun exampleLaunchingGlobalWait() = runBlocking {
    println("One ${Thread.currentThread().name}")
    val job = GlobalScope.launch {
        delay(2000)
        println("Two ${Thread.currentThread().name}")
    }
    println("Three ${Thread.currentThread().name}")
    job.join()

}
fun exampleLaunchingCoroutineScope() = runBlocking {
    println("One ${Thread.currentThread().name}")
    launch(Dispatchers.IO) {
        delay(2000)
        println("Two ${Thread.currentThread().name}")
    }
    println("Three ${Thread.currentThread().name}")
}
fun launchingCorouting() = runBlocking {

    val startTime = System.currentTimeMillis()
    val defRef = async { calculateHardThings(10) }
    val defRef2 = async { calculateHardThings(20) }
    val defRef3 = async { calculateHardThings(30) }

    val sum = defRef.await() + defRef2.await() + defRef3.await()
    println("async/await result: ${sum}")
    val endTime = System.currentTimeMillis()
    println("Time Taken : ${endTime - startTime}")

}

suspend fun calculateHardThings(stringNum: Int): Int {
    delay(1000)
    return stringNum * 10
}

fun launchingWithContext() = runBlocking {

    val startTime = System.currentTimeMillis()
    val result1 = withContext(Dispatchers.Default) { calculateHardThings(10) }
    val result2 = withContext(Dispatchers.Default) { calculateHardThings(20) }
    val result3 = withContext(Dispatchers.Default) { calculateHardThings(30) }

    val sum = result1 + result2 + result3

    println("async/await result: ${sum}")

    val endTime = System.currentTimeMillis()

    println("Time Taken : ${endTime - startTime}")

}