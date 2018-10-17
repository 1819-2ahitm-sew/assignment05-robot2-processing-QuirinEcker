package at.htl.robot.gui;

import at.htl.robot.model.Direction;
import at.htl.robot.model.Mode;
import at.htl.robot.model.Robot;
import processing.core.PApplet;


public class Main extends PApplet {

    // Hier die Member-Attribute eintragen

    private float upperMargin = 50;
    private float leftMargin = 50;
    private float celllength = 50;
    private float tableLength = celllength * 10;
    private Robot robot = new Robot();
    private int x = 0;
    private int y = 0;

    public static void main(String[] args) {
        PApplet.main("at.htl.robot.gui.Main", args);
    }

    public void settings() {
        size(800, 800);
    }

    public void setup() {
        background(209); //https://processing.org/tutorials/color/
        robot.setY(0);
        robot.setX(0);
    }

    /**
     * Diese Methode wird iterativ durchlaufen (wie loop() beim Arduino)
     */
    public void draw() {
        deleteAll();
        drawTable();
        drawRobot();
    }

    private void drawTable() {
        for (int i = 1; i < 12; i++) {
            line(leftMargin, i * upperMargin, leftMargin + tableLength, i * upperMargin);
            line(leftMargin * i, upperMargin, leftMargin * i, upperMargin + tableLength);
        }
    }

    /**
     * Erstellen Sie eine eigene Methode, mittels der der Roboter am Bildschirm gezeichnet wird
     * Die Angabe zu Position des Roboters am Spielfeld erhalten Sie aus dem Roboter-Objekt, welches
     * als Parameter übergeben wird.
     */
    private void drawRobot() {
        if (robot.getMode() == Mode.RESTRICTED) {
            if (x < 0) {
                x = 0;
                robot.setX(0);
            } else if (y < 0) {
                y = 0;
                robot.setY(0);
            } else if (x > 9) {
                x = 9;
                robot.setX(9);
            } else if (y > 9) {
                y = 9;
                robot.setY(9);
            }
        } else {
            if (x < 0) {
                x = 9;
                robot.setX(9);
            } else if (y < 0) {
                y = 9;
                robot.setY(9);
            } else if (x > 9) {
                x = 0;
                robot.setX(0);
            } else if (y > 9) {
                y = 0;
                robot.setY(0);
            }
        }
        drawRobotBody();
        drawRobotLetter();
    }

    private void drawRobotLetter() {
        char letter = getLetter();
        fill(0);
        drawLetter(letter);
        fill(255);
    }

    private void drawLetter(char letter) {
        float directionDisplayDistance = 15;
        float repositionMiddle = 3;

        if (robot.getDirection() == Direction.SOUTH) {
            text(
                    letter,
                    leftMargin + (celllength / 2) + (celllength * x - repositionMiddle),
                    upperMargin + (celllength / 2) + (celllength * y + directionDisplayDistance + repositionMiddle)
            );
        } else if (robot.getDirection() == Direction.EAST) {
            text(
                    letter,
                    leftMargin + (celllength / 2) + (celllength * x + directionDisplayDistance - repositionMiddle),
                    upperMargin + (celllength / 2) + (celllength * y + repositionMiddle)
            );
        } else if (robot.getDirection() == Direction.NORTH) {
            text(
                    letter,
                    leftMargin + (celllength / 2) + (celllength * x - repositionMiddle),
                    upperMargin + (celllength / 2) + (celllength * y - directionDisplayDistance + repositionMiddle)
            );
        } else if (robot.getDirection() == Direction.WEST) {
            text(
                    letter,
                    leftMargin + (celllength / 2) + (celllength * x - directionDisplayDistance - repositionMiddle),
                    upperMargin + (celllength / 2) + (celllength * y + repositionMiddle)
            );
        }
    }

    private char getLetter() {
        char letter = ' ';
        if (robot.getMode() == Mode.RESTRICTED) {
            letter = 'R';
        } else if (robot.getMode() == Mode.TELEPORT) {
            letter = 'T';
        }
        return letter;
    }

    private void drawRobotBody() {
        ellipse(leftMargin + (celllength / 2) + (celllength * x), upperMargin + (celllength / 2) + (celllength * y), celllength, celllength);
    }

    /**
     * Erstellen Sie eine eigene Methode zum Löschen des Bildschirms
     */
    private void deleteAll() {
        background(209);
    }

    /**
     * In dieser Methode reagieren Sie auf die Tasten
     */
    public void keyPressed() {
        println("pressed " + key + " " + keyCode);

        if (key == 'f' || key == 'F') {
            robot.stepForward();
            x = robot.getX();
            y = robot.getY();
            System.out.println(y);
        } else if (key == 'l' || key == 'L') {
            robot.rotateLeft();
        } else if (key == 'm' || key == 'M') {
            robot.switchMode();
        }
    }
}




