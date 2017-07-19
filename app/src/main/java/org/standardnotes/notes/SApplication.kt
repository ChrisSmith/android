package org.standardnotes.notes

import android.app.Application
import android.content.Context
import com.google.gson.*
import net.danlew.android.joda.JodaTimeAndroid
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.ISODateTimeFormat
import org.standardnotes.notes.store.NoteStore
import org.standardnotes.notes.store.ValueStore
import java.lang.reflect.Type


class SApplication : Application() {

    val valueStore: ValueStore by lazy { ValueStore(this) }
    val gson: Gson by lazy { GsonBuilder().registerTypeAdapter(DateTime::class.java, DateTimeDeserializer()).setPrettyPrinting().create() }
    val noteStore: NoteStore by lazy { NoteStore() }

    override fun onCreate() {
        super.onCreate()
        instance = this
        JodaTimeAndroid.init(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    companion object {

        lateinit var instance: SApplication
            private set
    }

    class DateTimeDeserializer : JsonDeserializer<DateTime>, JsonSerializer<DateTime> {

        @Throws(JsonParseException::class)
        override fun deserialize(je: JsonElement, type: Type,
                                 jdc: JsonDeserializationContext): DateTime? {
            if (je.asString.isEmpty()) {
                return null
            } else {
                return DATE_TIME_FORMATTER.parseDateTime(je.asString)
            }
        }

        override fun serialize(src: DateTime?, typeOfSrc: Type,
                               context: JsonSerializationContext): JsonElement {
            return JsonPrimitive(if (src == null) "" else DATE_TIME_FORMATTER.print(src))
        }

        companion object {
            val DATE_TIME_FORMATTER: org.joda.time.format.DateTimeFormatter = ISODateTimeFormat.dateTime().withZone(DateTimeZone.UTC)
        }
    }
}
