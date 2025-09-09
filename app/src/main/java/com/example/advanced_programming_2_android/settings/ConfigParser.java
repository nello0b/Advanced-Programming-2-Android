package com.example.advanced_programming_2_android.settings;

import static java.lang.Integer.parseInt;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Log;

import com.example.advanced_programming_2_android.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigParser {
    private static final String TAG = ConfigParser.class.getSimpleName();

    public static Map<String, String> parseConfig(Context context, int xmlResId) {
        Map<String, String> configMap = new HashMap<>();

        try (XmlResourceParser xmlParser = context.getResources().getXml(xmlResId)) {
            int eventType = xmlParser.getEventType();
            String tagName = null;
            String text = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        tagName = xmlParser.getName();
                        break;
                    case XmlPullParser.TEXT:
                        text = xmlParser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (tagName != null && text != null) {
                            configMap.put(tagName, text);
                            tagName = null;
                            text = null;
                        }
                        break;
                }

                eventType = xmlParser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            Log.e(TAG, "Error parsing XML: " + e.getMessage());
        }

        return configMap;
    }

    public static String getDefaultServerAddress(Context context) {
        Map<String, String> configMap = ConfigParser.parseConfig(context, R.xml.config);
        return configMap.get("server_address");
    }

    public static int getDefaultTheme(Context context){
        Map<String, String> configMap = ConfigParser.parseConfig(context, R.xml.config);
        String theme = configMap.get("theme");
        assert theme != null;
        return parseInt(theme);
    }
}
