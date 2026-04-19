package io.legado.app.ui.book.read.page.entities.column

import android.graphics.Canvas
import android.graphics.Rect
import androidx.annotation.Keep
import io.legado.app.help.config.ReadBookConfig
import io.legado.app.lib.theme.ThemeStore
import io.legado.app.ui.book.read.page.ContentTextView
import io.legado.app.ui.book.read.page.entities.TextLine
import io.legado.app.ui.book.read.page.entities.TextLine.Companion.emptyTextLine
import io.legado.app.ui.book.read.page.provider.ChapterProvider

@Keep
data class RubyColumn(
    override var start: Float,
    override var end: Float,
    val ruby: String,
) : BaseColumn {

    override var textLine: TextLine = emptyTextLine

    override fun draw(view: ContentTextView, canvas: Canvas) {
        if (ruby.isBlank() || textLine.rubyTopPadding <= 0f) {
            return
        }
        val rubyPaint = if (textLine.isTitle) {
            ChapterProvider.titleRubyPaint
        } else {
            ChapterProvider.contentRubyPaint
        }
        val textColor = if (textLine.isReadAloud) {
            ThemeStore.accentColor
        } else {
            ReadBookConfig.textColor
        }
        if (rubyPaint.color != textColor) {
            rubyPaint.color = textColor
        }
        val centerX = (start + end) / 2
        val baseline = textLine.rubyTopPadding - rubyPaint.fontMetrics.descent
        val rubyWidth = rubyPaint.measureText(ruby)
        val maxWidth = end - start
        val bounds = Rect()
        rubyPaint.getTextBounds(ruby, 0, ruby.length, bounds)
        val drawX = centerX - bounds.exactCenterX()
        if (rubyWidth > 0f && maxWidth > 0f && rubyWidth > maxWidth) {
            canvas.save()
            canvas.scale(maxWidth / rubyWidth, 1f, centerX, 0f)
            canvas.drawText(ruby, drawX, baseline, rubyPaint)
            canvas.restore()
        } else {
            canvas.drawText(ruby, drawX, baseline, rubyPaint)
        }
    }
}
