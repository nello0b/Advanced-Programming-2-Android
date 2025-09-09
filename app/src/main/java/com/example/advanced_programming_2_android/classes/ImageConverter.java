package com.example.advanced_programming_2_android.classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Utility class for converting images between Bitmap and Base64 formats.
 */
public class ImageConverter {

    /**
     * Converts a Base64-encoded image string into a Bitmap object.
     *
     * @param base64Image The Base64-encoded image string.
     * @return The decoded Bitmap object.
     */
    public static Bitmap convertBase64ToBitmap(String base64Image) {
        try {
            String imageData = base64Image.split(",")[1]; // Remove the MIME type prefix
            byte[] decodedString = Base64.decode(imageData, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Converts a Bitmap object into a Base64-encoded image string with the specified MIME type.
     *
     * @param bitmap   The Bitmap object to be encoded.
     * @param mimeType The MIME type of the image (e.g., "image/jpeg").
     * @return The Base64-encoded image string.
     */
    public static String convertBitmapToBase64(Bitmap bitmap, String mimeType) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String base64Image = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return "data:" + mimeType + ";base64," + base64Image;
    }

    /**
     * Converts an image from a URL represented by a content URI into a Base64-encoded image string.
     *
     * @param context   The context of the application.
     * @param imageUri  The URI of the image.
     * @return The Base64-encoded image string.
     */
    public static String convertImageToBase64(Context context, Uri imageUri) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();

            String base64Image = Base64.encodeToString(byteArray, Base64.DEFAULT);
            return "data:image/jpeg;base64," + base64Image;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
