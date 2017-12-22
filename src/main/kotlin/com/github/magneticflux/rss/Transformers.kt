package com.github.magneticflux.rss

import org.simpleframework.xml.transform.Transform
import org.threeten.bp.DayOfWeek
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.DateTimeFormatterBuilder
import org.threeten.bp.format.TextStyle
import org.threeten.bp.temporal.ChronoField
import java.net.MalformedURLException
import java.net.URL
import java.util.Locale

/**
 * @author Mitchell Skaggs
 * @since 1.0.0
 */
object DayOfWeekTransform : Transform<DayOfWeek> {
    override fun read(value: String): DayOfWeek {
        return DayOfWeek.from(FORMATTER.parse(value))
    }

    override fun write(value: DayOfWeek): String {
        return FORMATTER.format(value)
    }

    private val FORMATTER: DateTimeFormatter = DateTimeFormatterBuilder()
            .appendText(ChronoField.DAY_OF_WEEK, TextStyle.FULL)
            .toFormatter(Locale.US)
}

/**
 * @author Mitchell Skaggs
 * @since 1.0.1
 */
object ZonedDateTimeTransform : Transform<ZonedDateTime> {
    override fun read(value: String): ZonedDateTime {
        return ZonedDateTime.from(DateTimeFormatter.RFC_1123_DATE_TIME.parse(value))
    }

    override fun write(value: ZonedDateTime): String {
        return DateTimeFormatter.RFC_1123_DATE_TIME.format(value)
    }
}

/**
 * @author Mitchell Skaggs
 * @since 1.0.1
 */
object LocaleLanguageTransform : Transform<Locale> {
    override fun read(value: String): Locale {
        return Locale.forLanguageTag(value)
    }

    override fun write(value: Locale): String {
        return value.toLanguageTag()
    }
}

/**
 * @author Mitchell Skaggs
 * @since 1.0.1
 */
object URLTransform : Transform<URL> {
    override fun read(value: String): URL {
        return try {
            URL(value)
        } catch (e: MalformedURLException) {
            URL("http", null, -1, value)
        }
    }

    override fun write(value: URL): String {
        return if (value.authority == null)
            value.file
        else
            value.toExternalForm()
    }
}