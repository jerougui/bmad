package com.google.mediapipe.examples.objectdetection

import android.content.Context
import android.util.Log
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

private const val TAG = "MappingConfig"

data class AxisMapping(
    val parameter: String,
    val notes: List<String>? = null,
    val filterRange: List<Int>? = null,
    val smoothing: Double = 0.1,
    val glissando: Boolean = false
)

data class VelocityMapping(
    val parameter: String,
    val min: Double = 0.3,
    val max: Double = 1.0,
    val attack: Double = 0.05,
    val release: Double = 0.2,
    val threshold: Double = 10.0
)

data class MappingConfig(
    val scale: String = "pentatonic_major",
    val rootNote: String = "C4",
    val customScale: List<Int>? = null,
    val mapping: MappingSections = MappingSections(),
    val objectClassInstruments: Map<String, String> = mapOf(
        "default" to "sine"
    ),
    val instrument: String = "default"
) {
    data class MappingSections(
        val xAxis: AxisMapping = AxisMapping(
            parameter = "pitch",
            notes = listOf("C4", "D4", "E4", "G4", "A4"),
            smoothing = 0.1,
            glissando = true
        ),
        val yAxis: AxisMapping = AxisMapping(
            parameter = "timbre",
            filterRange = listOf(200, 2000),
            smoothing = 0.05
        ),
        val velocity: VelocityMapping = VelocityMapping(
            parameter = "volume",
            min = 0.3,
            max = 1.0,
            attack = 0.05,
            release = 0.2,
            threshold = 10.0
        )
    )
}

object MappingConfigLoader {

    @Volatile
    private var currentConfig: MappingConfig = MappingConfig()

    val config: MappingConfig
        get() = currentConfig

    fun load(context: Context): MappingConfig {
        val json = readJsonFromAssets(context, "music-config.json")
        return if (json != null) {
            try {
                parse(json)
            } catch (e: Exception) {
                Log.w(TAG, "Failed to parse music-config.json, using defaults", e)
                MappingConfig()
            }
        } else {
            Log.w(TAG, "music-config.json not found, using defaults")
            MappingConfig()
        }.also { currentConfig = it }
    }

fun reload(context: Context) {
         load(context)
     }

     fun setInstrument(instrument: String) {
         currentConfig = currentConfig.copy(instrument = instrument)
     }

     private fun readJsonFromAssets(context: Context, fileName: String): String? {
        return try {
            context.assets.open(fileName).use { stream ->
                BufferedReader(InputStreamReader(stream)).use { reader ->
                    reader.readText()
                }
            }
        } catch (e: IOException) {
            Log.w(TAG, "Asset $fileName not found: ${e.message}")
            null
        }
    }

    private fun parse(json: String): MappingConfig {
        val root = JSONObject(json)

        val scale = root.optString("scale", "pentatonic_major")
        val rootNote = root.optString("rootNote", "C4")

        val customScaleArray = root.optJSONArray("customScale")
        val customScale: List<Int>? = if (customScaleArray != null) {
            List(customScaleArray.length()) { i -> customScaleArray.getInt(i) }
        } else null

        val mappingObj = root.optJSONObject("mapping") ?: JSONObject()
        val mapping = parseMappingSections(mappingObj)

        val instrumentsObj = root.optJSONObject("objectClassInstruments")
        val instruments = mutableMapOf<String, String>()
        if (instrumentsObj != null) {
            instrumentsObj.keys().forEach { key ->
                instruments[key] = instrumentsObj.getString(key)
            }
        }

return MappingConfig(
             scale = scale,
             rootNote = rootNote,
             customScale = customScale,
             mapping = mapping,
             objectClassInstruments = instruments,
             instrument = root.optString("instrument", "sine")
         )
    }

    private fun parseMappingSections(obj: JSONObject): MappingConfig.MappingSections {
        val xAxisObj = obj.optJSONObject("xAxis") ?: JSONObject()
        val xNotes = xAxisObj.optJSONArray("notes")?.let { arr ->
            List(arr.length()) { i -> arr.getString(i) }
        } ?: listOf("C4", "D4", "E4", "G4", "A4")
        val xAxis = AxisMapping(
            parameter = xAxisObj.optString("parameter", "pitch"),
            notes = xNotes,
            filterRange = null,
            smoothing = xAxisObj.optDouble("smoothing", 0.1),
            glissando = xAxisObj.optBoolean("glissando", false)
        )

        val yAxisObj = obj.optJSONObject("yAxis") ?: JSONObject()
        val yFilterRange = yAxisObj.optJSONArray("filterRange")?.let { arr ->
            List(arr.length()) { i -> arr.getInt(i) }
        } ?: listOf(200, 2000)
        val yAxis = AxisMapping(
            parameter = yAxisObj.optString("parameter", "timbre"),
            notes = null,
            filterRange = yFilterRange,
            smoothing = yAxisObj.optDouble("smoothing", 0.05),
            glissando = false
        )

        val velObj = obj.optJSONObject("velocity") ?: JSONObject()
        val velocity = VelocityMapping(
            parameter = velObj.optString("parameter", "volume"),
            min = velObj.optDouble("min", 0.3),
            max = velObj.optDouble("max", 1.0),
            attack = velObj.optDouble("attack", 0.05),
            release = velObj.optDouble("release", 0.2),
            threshold = velObj.optDouble("threshold", 10.0)
        )

        return MappingConfig.MappingSections(xAxis, yAxis, velocity)
    }
}
