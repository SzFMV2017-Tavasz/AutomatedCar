package hu.oe.nik.szfmv17t.environment.utils;

import hu.oe.nik.szfmv17t.environment.interfaces.IWorldObject;
import hu.oe.nik.szfmv17t.environment.domain.ParkingLot;
import hu.oe.nik.szfmv17t.environment.domain.Road;
import hu.oe.nik.szfmv17t.environment.domain.Sign;
import hu.oe.nik.szfmv17t.environment.domain.Tree;
import hu.oe.nik.szfmv17t.environment.domain.Turn;
import hu.oe.nik.szfmv17t.environment.domain.ZebraCrossing;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Krisztian Juhasz <OE-NIK> *
 * Modified by: Budai Krisztián, Molnár Attila on 2017. 04. 09.
 */
public class XmlParser {

    private List<IWorldObject> mapObjects;
    private int mapHeight;
    private int mapWidth;

    public XmlParser(String newPathToXml) {
        mapObjects = new ArrayList<IWorldObject>();
        ReadXml(newPathToXml);
    }

    private void ReadXml(String pathToXml) {
        try {
            Document doc = getDocument(pathToXml);
            Element rootElement = getRootElement(doc);
            setMapDimension(rootElement);
            processXMLObjects(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Document getDocument(String pathToXml) throws ParserConfigurationException, SAXException, IOException {
        File fXmlFile = new File(pathToXml);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        return dBuilder.parse(fXmlFile);
    }

    private Element getRootElement(Document doc) {
        return (Element) doc.getElementsByTagName("Scene").item(0);
    }

    private void setMapDimension(Element rootElement) {
        mapHeight = Integer.parseInt(rootElement.getAttribute("height"));
        mapWidth = Integer.parseInt(rootElement.getAttribute("width"));
    }

    private void processXMLObjects(Document doc) {
        NodeList objectList = doc.getElementsByTagName("Object");
        for (int i = 0; i < objectList.getLength(); i++) {
            Node objectNode = objectList.item(i);
            if (objectNode.getNodeType() == Node.ELEMENT_NODE) {
                Element objectElement = (Element) objectNode;
                createObjectFromElement(objectElement);
            }
        }
    }

    private void createObjectFromElement(Element objectElement) {
        NamedNodeMap positionAttributes = objectElement.getElementsByTagName("Position").item(0).getAttributes();
        double posX = Double.parseDouble(positionAttributes.item(0).getTextContent());
        double posY = Double.parseDouble(positionAttributes.item(1).getTextContent());



        NamedNodeMap transformAttributes = objectElement.getElementsByTagName("Transform").item(0).getAttributes();
        double m11 = Double.parseDouble(transformAttributes.item(0).getTextContent());
        double m12 = Double.parseDouble(transformAttributes.item(1).getTextContent());
        double m21 = Double.parseDouble(transformAttributes.item(2).getTextContent());
        double m22 = Double.parseDouble(transformAttributes.item(3).getTextContent());

        double axisAngle = convertMatrixToRadians(m11, m12, m21, m22);

        int roadPainting1 = 1;
        int roadPainting2 = 1;
        int roadPainting3 = 1;

        if (objectElement.getElementsByTagName("Parameter").getLength() != 0) {
            NamedNodeMap firstParamAttributes = objectElement.getElementsByTagName("Parameter").item(0).getAttributes(); //A NEVE AZ XML-BOL VAN
            roadPainting1 = Integer.parseInt(firstParamAttributes.item(1).getTextContent()); //VALUE

            NamedNodeMap secondParamAttributes = objectElement.getElementsByTagName("Parameter").item(1).getAttributes();
            roadPainting2 = Integer.parseInt(firstParamAttributes.item(1).getTextContent()); //VALUE

            NamedNodeMap thirdParamAttributes = objectElement.getElementsByTagName("Parameter").item(2).getAttributes();
            roadPainting3 = Integer.parseInt(firstParamAttributes.item(1).getTextContent()); //VALUE
        }

        Vector2d referencePoint = new Vector2d(posX, posY);
        switch (objectElement.getAttribute("type")) {
            case "road_2lane_straight":
                mapObjects.add(new Road(posX, posY, 350, 350, axisAngle, 0, "road_2lane_straight.png", axisAngle, roadPainting1, roadPainting2, roadPainting3,posX, posY));
                break;

            case "road_2lane_90right":
                referencePoint = calculateRight90(posX, posY, axisAngle);
                mapObjects.add(new Turn(referencePoint.getX(), referencePoint.getY(), 525, 525, axisAngle, 0, "road_2lane_90right.png", axisAngle, roadPainting1, roadPainting2, roadPainting3,posX, posY));
                break;
            case "road_2lane_90left":
                referencePoint = calculateLeft90(posX, posY, axisAngle);
                mapObjects.add(new Turn(referencePoint.getX(), referencePoint.getY(), 525, 525, axisAngle, 0, "road_2lane_90left.png", axisAngle, roadPainting1, roadPainting2, roadPainting3,posX, posY));
                break;

            case "road_2lane_tjunctionleft":
                referencePoint = calculateTJunctionLeft(posX, posY, axisAngle);
                mapObjects.add(new Turn(referencePoint.getX(), referencePoint.getY(), 875, 1400, axisAngle, 0, "road_2lane_tjunctionleft.png", axisAngle, roadPainting1, roadPainting2, roadPainting3,posX, posY));
                break;
            case "road_2lane_tjunctionright":
                mapObjects.add(new Turn(posX, posY, 875, 1400, axisAngle, 0, "road_2lane_tjunctionright.png", axisAngle, roadPainting1, roadPainting2, roadPainting3,posX, posY));
                break;

            case "road_2lane_45right":
                referencePoint = calculateRight45(posX, posY, axisAngle);
                mapObjects.add(new Turn(referencePoint.getX(), referencePoint.getY(), 401, 371, axisAngle, 0, "road_2lane_45right.png", axisAngle, roadPainting1, roadPainting2, roadPainting3,posX, posY));
                break;
            case "road_2lane_45left":
                referencePoint = calculateLeft45(posX, posY, axisAngle);
                mapObjects.add(new Turn(referencePoint.getX(), referencePoint.getY(), 401, 371, axisAngle, 0, "road_2lane_45left.png", axisAngle, roadPainting1, roadPainting2, roadPainting3,posX, posY));
                break;

            case "parking_space_parallel":
                mapObjects.add(new ParkingLot(posX, posY, 138, 621, axisAngle, 0, "parking_space_parallel.png", axisAngle,posX, posY));
                break;

            case "crosswalk":
                mapObjects.add(new ZebraCrossing(posX, posY, 336, 197, axisAngle, 1, "crosswalk.png", axisAngle,posX, posY));
                break;

            case "roadsign_parking_right":
                mapObjects.add(new Sign(posX, posY, 80, 80, axisAngle, 2, "roadsign_parking_right.png", 10, 0, axisAngle,posX, posY));
                break;
            case "roadsign_priority_stop":
                mapObjects.add(new Sign(posX, posY, 80, 80, axisAngle, 2, "roadsign_priority_stop.png", 10, 0, axisAngle,posX, posY));
                break;
            case "roadsign_speed_40":
                mapObjects.add(new Sign(posX, posY, 80, 80, axisAngle, 2, "roadsign_speed_40.png", 10, 0, axisAngle,posX, posY));
                break;
            case "roadsign_speed_50":
                mapObjects.add(new Sign(posX, posY, 80, 80, axisAngle, 2, "roadsign_speed_50.png", 10, 0, axisAngle,posX, posY));
                break;
            case "roadsign_speed_60":
                mapObjects.add(new Sign(posX, posY, 80, 80, axisAngle, 2, "roadsign_speed_60.png", 10, 0, axisAngle,posX, posY));
                break;

            case "parking_bollard":
                mapObjects.add(new Sign(posX,posY,21,61,axisAngle,2,"bollard.png",20,0,axisAngle,posX, posY)); break;
            case "boundary":
                mapObjects.add(new Sign(posX,posY,51,86,axisAngle,2,"boundary.png",100,0,axisAngle,posX, posY)); break;
            case "garage":
                mapObjects.add(new Sign(posX,posY,336,294,axisAngle,2,"garage.png",10000,0,axisAngle,posX, posY)); break;
            case "parking_space_perpendicular":
                mapObjects.add(new ParkingLot(posX,posY,295,469,axisAngle,0,"parking_90.png",axisAngle,posX, posY)); break;
            case "road_2lane_6right":
                referencePoint = calculateRight6(posX, posY, axisAngle);
                mapObjects.add(new Turn(referencePoint.getX(), referencePoint.getY(),369,368,axisAngle,0,"road_2lane_6right.png",axisAngle, roadPainting1, roadPainting2, roadPainting3,posX, posY));
            case "road_2lane_6left":
                referencePoint = calculateLeft6(posX, posY, axisAngle);
                mapObjects.add(new Turn(referencePoint.getX(), referencePoint.getY(),369,368,axisAngle,0,"road_2lane_6left.png",axisAngle, roadPainting1, roadPainting2, roadPainting3,posX, posY)
);

            case "tree":
                mapObjects.add(new Tree(posX,posY,142,160,axisAngle,2,"tree.png",20,0,axisAngle,posX, posY)); break;
        }
    }

    private Vector2d calculateLeft90 (double posX, double posY, double angle)
    {
        Vector2d originalReferencePoint = new Vector2d(posX, posY);
        Vector2d calculatedReferencePoint = new Vector2d(posX - 176, posY - 525);

        return Vector2d.rotateAroundPoint(calculatedReferencePoint, originalReferencePoint, angle);
    }

    private Vector2d calculateRight90 (double posX, double posY, double angle)
    {
        Vector2d originalReferencePoint = new Vector2d(posX, posY);
        Vector2d calculatedReferencePoint = new Vector2d(posX - 350, posY - 525);

        return Vector2d.rotateAroundPoint(calculatedReferencePoint, originalReferencePoint, angle);
    }

    private Vector2d calculateLeft45 (double posX, double posY, double angle)
    {
        Vector2d originalReferencePoint = new Vector2d(posX, posY);
        Vector2d calculatedReferencePoint = new Vector2d(posX - 53, posY - 371);

        return Vector2d.rotateAroundPoint(calculatedReferencePoint, originalReferencePoint, angle);
    }

    private Vector2d calculateRight45 (double posX, double posY, double angle)
    {
        Vector2d originalReferencePoint = new Vector2d(posX, posY);
        Vector2d calculatedReferencePoint = new Vector2d(posX - 350, posY - 371);

        return Vector2d.rotateAroundPoint(calculatedReferencePoint, originalReferencePoint, angle);
    }

    private Vector2d calculateTJunctionLeft (double posX, double posY, double angle)
    {
        Vector2d originalReferencePoint = new Vector2d(posX, posY);
        Vector2d calculatedReferencePoint = new Vector2d(posX - 875, posY);

        return Vector2d.rotateAroundPoint(calculatedReferencePoint, originalReferencePoint, angle);
    }

    private Vector2d calculateLeft6 (double posX, double posY, double angle)
    {
        Vector2d originalReferencePoint = new Vector2d(posX, posY);
        Vector2d calculatedReferencePoint = new Vector2d(posX - 19, posY - 365);

        return Vector2d.rotateAroundPoint(calculatedReferencePoint, originalReferencePoint, angle);
    }

    private Vector2d calculateRight6 (double posX, double posY, double angle)
    {
        Vector2d originalReferencePoint = new Vector2d(posX, posY);
        Vector2d calculatedReferencePoint = new Vector2d(posX - 349, posY - 365);

        return Vector2d.rotateAroundPoint(calculatedReferencePoint, originalReferencePoint, angle);
    }

    private Vector2d calculateRotary (double posX, double posY, double angle)
    {
        Vector2d originalReferencePoint = new Vector2d(posX, posY);
        Vector2d calculatedReferencePoint = new Vector2d(posX - 1, posY - 874);

        return Vector2d.rotateAroundPoint(calculatedReferencePoint, originalReferencePoint, angle);
    }

    private Vector2d calculateCrossroad1 (double posX, double posY, double angle)
    {
        Vector2d originalReferencePoint = new Vector2d(posX, posY);
        Vector2d calculatedReferencePoint = new Vector2d(posX - 1, posY - 874);

        return Vector2d.rotateAroundPoint(calculatedReferencePoint, originalReferencePoint, angle);
    }

    private Vector2d calculateCrossroad2 (double posX, double posY, double angle)
    {
        Vector2d originalReferencePoint = new Vector2d(posX, posY);
        Vector2d calculatedReferencePoint = new Vector2d(posX - 1, posY - 874);

        return Vector2d.rotateAroundPoint(calculatedReferencePoint, originalReferencePoint, angle);
    }

    private double convertMatrixToRadians(double m11, double m12, double m21, double m22) {
        //formula of the angle between the two vectors: a * b = |a| * |b| * cos(beta)
        //where a * b is the scalarProduct
        //Our zero degree will be the horizontal right:
        double defaultX = 1;
        double defaultY = 0;

        double transformedX = m11 * defaultX + m12 * defaultY;
        double transformedY = m21 * defaultX + m22 * defaultY;

        double scalarProduct = defaultX * transformedX + defaultY * transformedY;

        double defaultVectorLength = Math.sqrt(defaultX * defaultX + defaultY * defaultY);
        double transformedVectorLength = Math.sqrt(transformedX * transformedX + transformedY * transformedY);

        double angleInRad = Math.acos(scalarProduct / (defaultVectorLength * transformedVectorLength));
        if (transformedY < 0) {
            angleInRad = 2 * Math.PI - angleInRad;
        }
        //If angle is NaN as a result of transformedVectorLength=0, Math.round() returns 0. It is correct in our cases.
        //angleInRad = Math.round(angleInRad * 100.0) / 100.0;
        return angleInRad;
//        double m13 = 0;
//        double m23 = 0;
//        double m33 = 1;
//        return 6.2831853072d - Math.acos((m11 + m22) / 2);
    }

    public List<IWorldObject> getWorldObjects() {
        return mapObjects;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }
}
