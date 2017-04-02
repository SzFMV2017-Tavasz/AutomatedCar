package hu.oe.nik.szfmv17t.environment.utils;

/**
 * Created by Laszlo on 3/31/2017.
 */
public final class StringUtil {
    private StringUtil(){}

    public static String removeExtension(String fileName)
    {
        int pos = fileName.lastIndexOf(".");
        if (pos > 0) {
            fileName = fileName.substring(0, pos);
        }
        return fileName;
    }
}
