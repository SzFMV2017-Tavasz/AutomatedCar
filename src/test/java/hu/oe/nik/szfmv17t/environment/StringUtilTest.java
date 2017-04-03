package hu.oe.nik.szfmv17t.environment;

import hu.oe.nik.szfmv17t.environment.utils.StringUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Laszlo on 4/1/2017.
 */
public class StringUtilTest {

    @Test
    public void testRemoveExtension(){assertEquals("src/main/resources/test1", StringUtil.removeExtension("src/main/resources/test1.jpg"));}

    @Test
    public void testRemoveExtensionPathIncludesDots(){assertEquals("src/main/resources/test.folder/test1", StringUtil.removeExtension("src/main/resources/test.folder/test1.jpg"));}

    @Test
    public void testRemoveExtensionDoubleExtension(){assertEquals("src/main/resources/test1.jpg", StringUtil.removeExtension("src/main/resources/test1.jpg.jpg"));}

}
