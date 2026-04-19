package io.legado.app.ui.book.read.page.entities

data class ReadAloudSegment(
    val displayText: String,
    val speakText: String,
    val speakToDisplayMap: IntArray,
    val displayToSpeakMap: IntArray,
) {

    fun mapSpeakOffset(offset: Int): Int {
        return speakToDisplayMap[offset.coerceIn(0, speakToDisplayMap.lastIndex)]
    }

    fun mapDisplayOffset(offset: Int): Int {
        return displayToSpeakMap[offset.coerceIn(0, displayToSpeakMap.lastIndex)]
    }

    fun trimDisplayStart(offset: Int): ReadAloudSegment {
        if (offset <= 0) {
            return this
        }
        if (offset >= displayText.length) {
            return empty
        }
        val speakOffset = mapDisplayOffset(offset)
        val newDisplayText = displayText.substring(offset)
        val newSpeakText = speakText.substring(speakOffset)
        val newSpeakToDisplayMap = IntArray(newSpeakText.length + 1) { index ->
            (speakToDisplayMap[speakOffset + index] - offset).coerceAtLeast(0)
        }
        val newDisplayToSpeakMap = IntArray(newDisplayText.length + 1) { index ->
            (displayToSpeakMap[offset + index] - speakOffset).coerceAtLeast(0)
        }
        return ReadAloudSegment(
            displayText = newDisplayText,
            speakText = newSpeakText,
            speakToDisplayMap = newSpeakToDisplayMap,
            displayToSpeakMap = newDisplayToSpeakMap
        )
    }

    companion object {
        val empty = ReadAloudSegment("", "", intArrayOf(0), intArrayOf(0))
    }
}
