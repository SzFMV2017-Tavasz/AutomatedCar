package hu.oe.nik.szfmv17t.automatedcar.ultrasonicsensor;

/**
 * Created by NyiroGabor on 2017.04.01..
 */
public class UltrasonicSensorCoordinates {
        private double mainX;
        private double mainY;
        private double leftX;
        private double leftY;
        private double rightX;
        private double rightY;

        public UltrasonicSensorCoordinates(){}

        public double getMainX() {
                return mainX;
        }
        public double getMainY() {
                return mainY;
        }
        public double getLeftX() {
                return leftX;
        }
        public double getLeftY() {
                return leftY;
        }
        public double getRightX() {
                return rightX;
        }
        public double getRightY() {
                return rightY;
        }

        public void setMainCoordinates(double x, double y) {
                mainX = x;
                mainY = y;
        }
        public void setLeftCoordinates(double x, double y) {
                leftX = x;
                leftY = y;
        }
        public void setRightCoordinates(double x, double y) {
                rightX = x;
                rightY = y;
        }
}
