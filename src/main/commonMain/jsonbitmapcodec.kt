
fun encodeJsonBitmap(input: ByteArray, bitmap: IntArray, quoteState: Int, escapeCounter: Int): Pair<Int, Int> {
    var quoteState = quoteState
    var escapeCounter = escapeCounter

    for (i in input.indices) {
        val byte = input[i].toInt() and 0xFF

        // Update quoteState using bitwise operations
        val quoteMask = (byte xor '"'.toInt()) + 1 shr 8
        quoteState = quoteState xor quoteMask

        // Update escapeCounter using bitwise operations to handle escape characters and UTF8 initiators of different lengths
        val escapeMask = (((byte xor '\\'.toInt()) + 1 shr 8) or
                ((byte and 0b11100000) xor 0b11000000 shr 5) or
                ((byte and 0b11110000) xor 0b11100000 shr 4) or
                ((byte and 0b11111000) xor 0b11110000 shr 3)) and
                (escapeCounter - 1 shr 31)
        escapeCounter = (escapeCounter or escapeMask) and (escapeCounter - 1 or -escapeMask)

        val mask1 = 0b01011011
        val mask2 = 0b01011101
        val mask3 = 0b00101100

        // Apply damping mask using bitwise operations
        val dampingMask = quoteState - 1 shr 31
        val class1 = (((byte xor mask1) and mask1.inv()) + 1 ushr 31) and dampingMask.inv()
        val class2 = (((byte xor mask2) and mask2.inv()) + 1 ushr 31) and dampingMask.inv()
        val class3 = ((byte xor mask3) + 1 ushr 31) and dampingMask.inv()

        // Update bitmap using bitwise operations
        val index = i / 16
        val shift = (i % 16) * 2
        bitmap[index] = bitmap[index] xor (class1.toInt() xor (class2.toInt() shl 1) xor (class3.toInt() shl 1)) shl shift
    }

    return Pair(quoteState, escapeCounter)
}
