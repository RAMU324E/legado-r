package io.legado.app.help.book

data class RubySpan(
    val start: Int,
    val end: Int,
    val ruby: String,
)

data class RubyParseResult(
    val text: String,
    val spans: List<RubySpan>,
)

object RubyText {
    private const val START = '\uE000'
    private const val SEPARATOR = '\uE001'
    private const val END = '\uE002'

    fun encode(base: String, ruby: String): String {
        if (base.isEmpty() || ruby.isBlank()) {
            return base
        }
        return buildString(base.length + ruby.length + 3) {
            append(START)
            append(base)
            append(SEPARATOR)
            append(ruby)
            append(END)
        }
    }

    fun parse(text: String): RubyParseResult {
        val plainText = StringBuilder(text.length)
        val spans = arrayListOf<RubySpan>()
        var index = 0
        while (index < text.length) {
            if (text[index] != START) {
                plainText.append(text[index])
                index++
                continue
            }
            val separatorIndex = text.indexOf(SEPARATOR, index + 1)
            val endIndex = if (separatorIndex > index) {
                text.indexOf(END, separatorIndex + 1)
            } else {
                -1
            }
            if (separatorIndex <= index || endIndex <= separatorIndex) {
                plainText.append(text[index])
                index++
                continue
            }
            val base = text.substring(index + 1, separatorIndex)
            val ruby = text.substring(separatorIndex + 1, endIndex)
            val start = plainText.length
            plainText.append(base)
            if (base.isNotEmpty() && ruby.isNotBlank()) {
                spans.add(RubySpan(start, plainText.length, ruby))
            }
            index = endIndex + 1
        }
        return RubyParseResult(plainText.toString(), spans)
    }
}
