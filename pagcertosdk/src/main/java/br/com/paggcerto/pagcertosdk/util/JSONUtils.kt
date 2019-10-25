package br.com.paggcerto.pagcertosdk.util

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

internal object JSONUtils {

    fun removeArrays(jObject: JSONObject): JSONObject {

        val newJsonObject = JSONObject()
        val keys = jObject.keys()

        while (keys.hasNext()) {
            val key = keys.next() as String
            try {
                if (jObject.get(key) is JSONArray) {

                    val array = jObject.get(key) as JSONArray
                    for (i in 0 until array.length()) {
                        newJsonObject.put("$key[$i]", array.get(i))
                    }
                } else {
                    newJsonObject.put(key, jObject.get(key))
                }

            } catch (e: JSONException) {
                e.printStackTrace()
                return newJsonObject
            }

        }

        return newJsonObject
    }
}