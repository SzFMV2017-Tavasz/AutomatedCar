package hu.oe.nik.szfmv17t.environment;

import hu.oe.nik.szfmv17t.environment.utils.XmlParser;
import static junit.framework.Assert.assertEquals;

import org.junit.Test;

public class XmlParserTest {
    private XmlParser xmlParser;
    
    @org.junit.Before
	public void setUp() throws Exception {
		/* stuff written here runs before the tests */
		xmlParser = new XmlParser("src/main/resources/test_world.xml");
	}
    
    @Test
	public void testMapHeightGetter(){
		assertEquals(xmlParser.getMapHeight(), 3000);
	}
        
    @Test
	public void testMapWidthGetter(){
		assertEquals(xmlParser.getMapWidth(), 5120);
	}
}
