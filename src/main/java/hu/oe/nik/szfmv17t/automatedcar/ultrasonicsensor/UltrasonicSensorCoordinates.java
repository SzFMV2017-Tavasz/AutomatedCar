package hu.oe.nik.szfmv17t.automatedcar.ultrasonicsensor;

/**
 * Created by NyiroGabor on 2017.04.01..
 */
public class UltrasonicSensorCoordinates {
        private int mainX;
        private int mainY;
        private int leftX;
        private int leftY;
        private int rightX;
        private int rightY;

        public UltrasonicSensorCoordinates(){}

        public int getMainX() {
                return mainX;
        }
        public int getMainY() {
                return mainY;
        }
        public int getLeftX() {
                return leftX;
        }
        public int getLeftY() {
                return leftY;
        }
        public int getRightX() {
                return rightX;
        }
        public int getRightY() {
                return rightY;
        }

        public void setMainCoordinates(int x, int y) {
                mainX = x;
                mainY = y;
        }
        public void setLeftCoordinates(int x, int y) {
                leftX = x;
                leftY = y;
        }
        public void setRightCoordinates(int x, int y) {
                rightX = x;
                rightY = y;
        }
}
