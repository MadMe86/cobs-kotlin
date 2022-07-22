class cobs {
    fun encode(input:ByteArray):ByteArray{
        val result = input.toMutableList()
        result.add(0,0) // Add leading pointer byte
        result.add(0) // Add closing byte

        var pointerByte = 0
        var counter = 1

        while (pointerByte + counter < result.size) {
            while (result[pointerByte + counter] != 0.toByte()) {
                if (counter == 255) {                                   // break if 255 is hit
                    result.add(pointerByte + 255, 42.toByte())    // this is the next pointer byte the value doesn't matter
                    break
                }
                counter++
            }

            result[pointerByte] = counter.toByte()
            pointerByte += counter
            counter = 1
        }

        return result.toByteArray()
    }


    fun decode(input:ByteArray):ByteArray{
        val result = input.toMutableList()
        var pointer = (result[0]-1).toUByte().toInt()
        var jumpValue = result[0].toUByte().toByte()
        result.removeAt(0)
        while (pointer < result.size -1)
        {
            if (jumpValue == 255.toByte())
            {
                jumpValue = (result[pointer].toUByte().toInt() - 1).toByte()
                result.removeAt(pointer)
            }
            else {
                jumpValue = result[pointer].toUByte().toByte()
                result[pointer] = 0
            }

            pointer += jumpValue
        }

        if (result.last() == 0.toByte())
            result.removeLast()

        return result.toByteArray()
    }
}