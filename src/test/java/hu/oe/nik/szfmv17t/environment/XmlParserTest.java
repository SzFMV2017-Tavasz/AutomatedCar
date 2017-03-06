package hu.oe.nik.szfmv17t.environment;

import hu.oe.nik.szfmv17t.environment.utils.XmlParser;

import org.junit.Test;

public class XmlParserTest {
    private XmlParser xmlParser;
    
    @org.junit.Before
	public void setUp() throws Exception {
		/* stuff written here runs before the tests */
		xmlParser = new XmlParser("AutomatedCar/src/main/resources/test_world.xml");
	}
        
}
