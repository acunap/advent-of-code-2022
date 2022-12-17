import java.io.File

/**
 * Reads lines from the given input txt file.
 */
fun readLines(name: String) = File("src", "$name.txt").readLines()

/**
 * Reads text from the given input txt file.
 */
fun readText(name: String) = File("src", "$name.txt").readText()
