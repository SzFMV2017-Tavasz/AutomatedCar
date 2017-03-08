package hu.oe.nik.szfmv17t.environment.domain;

import hu.oe.nik.szfmv17t.environment.interfaces.IWorldObject;
import hu.oe.nik.szfmv17t.environment.utils.XmlParser;
import java.util.ArrayList;
import java.util.List;


public class World {
	private int width = 0;
	private int height = 0;
	private List<IWorldObject> worldObjects = new ArrayList<>();
        private XmlParser xmlParser;

	public World(String pathToXml) {
		xmlParser = new XmlParser(pathToXml);
                width = xmlParser.getMapWidth();
                height = xmlParser.getMapHeight();
                worldObjects = xmlParser.getWorldObjects();
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public List<IWorldObject> getWorldObjects() {
		return worldObjects;
	}

	public void addObjectToWorld(IWorldObject o){
		worldObjects.add(o);
	}
}
