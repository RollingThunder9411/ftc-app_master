package org.firstinspires.ftc.teamcode;

import android.content.Context;
import android.media.MediaPlayer;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;


/**
 * Created by Rolling Thunder on 8/19/2017.
 * Last updated on 8/19/2017.
 */

@TeleOp(name="TeleOp", group="TeleOp")
@Disabled
public class TeleOpMainOld extends OpMode {
    /*
     _______  _______ _________ _        _______  _______  _______  _______  _______
    (       )(  ___  )\__   __/( (    /|(  ____ \(  ____ )(  ___  )(       )(  ____ \
    | () () || (   ) |   ) (   |  \  ( || (    \/| (    )|| (   ) || () () || (    \/ _
    | || || || (___) |   | |   |   \ | || (__    | (____)|| (___) || || || || (__    (_)
    | |(_)| ||  ___  |   | |   | (\ \) ||  __)   |     __)|  ___  || |(_)| ||  __)
    | |   | || (   ) |   | |   | | \   || (      | (\ (   | (   ) || |   | || (       _
    | )   ( || )   ( |___) (___| )  \  || )      | ) \ \__| )   ( || )   ( || (____/\(_)
    |/     \||/     \|\_______/|/    )_)|/       |/   \__/|/     \||/     \|(_______/

              _______  _______  _        _______  ______
    |\     /|(  ___  )(  ____ \| \    /\(  ____ \(  __  \
    | )   ( || (   ) || (    \/|  \  / /| (    \/| (  \  )
    | (___) || (___) || |      |  (_/ / | (__    | |   ) |
    |  ___  ||  ___  || |      |   _ (  |  __)   | |   | |
    | (   ) || (   ) || |      |  ( \ \ | (      | |   ) |
    | )   ( || )   ( || (____/\|  /  \ \| (____/\| (__/  )
    |/     \||/     \|(_______/|_/    \/(_______/(______/
     */
    // Defines application context for use in the MediaPlayers
    Context appContext = FtcRobotControllerActivity.appContext;
    // Defines MediaPlayers
    MediaPlayer suh1 = MediaPlayer.create(appContext, R.raw.suh1);
    MediaPlayer suh2 = MediaPlayer.create(appContext, R.raw.suh2);
    MediaPlayer suh3 = MediaPlayer.create(appContext, R.raw.suh3);
    MediaPlayer suh4 = MediaPlayer.create(appContext, R.raw.suh4);
    MediaPlayer suh5 = MediaPlayer.create(appContext, R.raw.suh5);
    MediaPlayer suh6 = MediaPlayer.create(appContext, R.raw.suh6);
    MediaPlayer suh7 = MediaPlayer.create(appContext, R.raw.suh7);
    MediaPlayer suh8 = MediaPlayer.create(appContext, R.raw.suh8);
    MediaPlayer succ = MediaPlayer.create(appContext, R.raw.succ);
    MediaPlayer allStar = MediaPlayer.create(appContext, R.raw.allstar);
    MediaPlayer seizeProduction = MediaPlayer.create(appContext, R.raw.seizeproduction);
    MediaPlayer reblexDeath = MediaPlayer.create(appContext, R.raw.reblexdeath);
    MediaPlayer yeahBoy = MediaPlayer.create(appContext, R.raw.yeahboy);
    MediaPlayer isCenaSure = MediaPlayer.create(appContext, R.raw.iscenasure);
    MediaPlayer noice = MediaPlayer.create(appContext, R.raw.noice);
    MediaPlayer shotsFired = MediaPlayer.create(appContext, R.raw.shotsfired);
    MediaPlayer filthyRishi = MediaPlayer.create(appContext, R.raw.filthyrishi);
    MediaPlayer krabs = MediaPlayer.create(appContext, R.raw.krabs);
    MediaPlayer zooweemama = MediaPlayer.create(appContext, R.raw.zooweemama);
    // Defines DcMotors
    DcMotor motorBackLeft, motorBackRight, motorFrontLeft, motorFrontRight, flywheelMotor, ballCollectorMotor, ballConveyorMotor, lift;
    Servo ballServo, lockServo;
    ColorSensor colorSensorBottom, colorSensorSide;
    OpticalDistanceSensor distanceSensor;
    //Defines the integers for determining a random suh to be played
    int randomSuh, previousSuh, startPosition, endPosition;
    long startTime;
    // Defines drive variables
    double throttle, direction, strafe, flywheelPower, flywheelSpeed = 0;
    String mode;
    // Defines booleans for toggles, button releases, and modes
    boolean flywheels, ballCollector, releaseAButton, g2ReleaseRightStick, releaseXButton,releaseYButton,
    releaseDPadDown, releaseLeftBumper, runLockServo, releaseRightBumper, reverseLockServo, reverseBallCollector = false;
    boolean soundMode, resetRotation = true;
    //GyroSensor g;
    public void init() {
        //g = this.hardwareMap.gyroSensor.get("gyroSensor");
        // Initializes motors
        motorFrontLeft = this.hardwareMap.dcMotor.get("motorFrontLeft");
        motorFrontRight = this.hardwareMap.dcMotor.get("motorFrontRight");
        motorBackLeft = this.hardwareMap.dcMotor.get("motorBackLeft");
        motorBackRight = this.hardwareMap.dcMotor.get("motorBackRight");
        flywheelMotor = this.hardwareMap.dcMotor.get("flywheel");
        ballCollectorMotor = this.hardwareMap.dcMotor.get("ballCollectorMotor");
        lift = this.hardwareMap.dcMotor.get("lift");
        ballServo = this.hardwareMap.servo.get("ballServo");
        lockServo = this.hardwareMap.servo.get("lockServo");
        distanceSensor = this.hardwareMap.opticalDistanceSensor.get("distanceSensor");
        colorSensorBottom = this.hardwareMap.colorSensor.get("colorSensorBottom");
        colorSensorSide = this.hardwareMap.colorSensor.get("colorSensorSide");
        colorSensorBottom.setI2cAddress(I2cAddr.create8bit(0x4c));
        colorSensorSide.setI2cAddress(I2cAddr.create8bit(0x3c));
        colorSensorSide.enableLed(false);
        // Reverses motors that need to run in reverse
        ballCollectorMotor.setDirection(DcMotor.Direction.REVERSE);
        motorFrontLeft.setDirection(DcMotor.Direction.REVERSE);
        motorBackLeft.setDirection(DcMotor.Direction.REVERSE);
        distanceSensor.enableLed(true);
        flywheelMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        flywheelMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lockServo.setPosition(.5);
        ballServo.setPosition(1);
    }
    public void loop() {
        if (resetRotation && flywheels) {
            startPosition = flywheelMotor.getCurrentPosition();
            startTime = System.currentTimeMillis();
            resetRotation = false;
        }
        if (System.currentTimeMillis() >= startTime + 1000) {
            endPosition = flywheelMotor.getCurrentPosition();
            flywheelSpeed = (endPosition - startPosition) / 1080.0;
            resetRotation = true;
        }
        // Sets direction based on left stick x value
        direction = gamepad1.right_trigger - gamepad1.left_trigger;
        throttle = -gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        // Sets the power of the drive motors
        motorFrontLeft.setPower(throttle + direction + strafe);
        motorFrontRight.setPower(throttle - direction - strafe);
        motorBackLeft.setPower(throttle + direction - strafe);
        motorBackRight.setPower(throttle - direction + strafe);
        // Sets the lift power based on the right stick y value
        lift.setPower(gamepad1.right_stick_y);
        // Toggles ball collector boolean based on X button release
        if (!gamepad1.x && releaseXButton) {
            ballCollector = !ballCollector;
            reverseBallCollector = false;
        }
        releaseXButton = gamepad1.x;
        if(!gamepad1.y && releaseYButton) {
            reverseBallCollector = !reverseBallCollector;
            ballCollector = false;
        }
        releaseYButton = gamepad1.y;
        if(!gamepad2.dpad_down && releaseDPadDown)
            soundMode = !soundMode;
        releaseDPadDown = gamepad2.dpad_down;
        if (runLockServo)
            lockServo.setPosition(0);
        else if(reverseLockServo)
            lockServo.setPosition(1);
        else
            lockServo.setPosition(0.5);
        // Sets power of the flywheels to 1 based on the corresponding boolean, or 0 if false
        if (flywheels)
            flywheelPower = 0.5;
        else if(flywheelPower > 0 && !flywheels) {
            flywheelPower -= 0.001;
            flywheelSpeed = 0;
        }
        flywheelMotor.setPower(flywheelPower);
        // Sets power of the ball collector to 1 or -1 if the corresponding boolean is true, and 0 if false
        if (ballCollector)
            ballCollectorMotor.setPower(1);
        else if (reverseBallCollector)
            ballCollectorMotor.setPower(-1);
        else
            ballCollectorMotor.setPower(0);
        // Plays sound based on button pressed
        if(soundMode) {
            if (gamepad2.right_trigger > 0)
                playSound(reblexDeath);
            if(gamepad2.dpad_up)
                playSound(seizeProduction);
            if(gamepad2.dpad_left)
                playSound(succ);
            if(gamepad2.dpad_right)
                playSound(allStar);
            if(gamepad2.left_bumper)
                playSound(shotsFired);
            if(gamepad2.right_bumper)
                playSound(krabs);
            if(gamepad2.a)
                playSound(noice);
            if(gamepad2.x && !gamepad2.y)
                playSound(yeahBoy);
            if(gamepad2.y && !gamepad2.x)
                playSound(isCenaSure);
            if(gamepad2.left_stick_button)
                playSound(filthyRishi);
            if (gamepad2.x && gamepad2.y)
                playSound(zooweemama);
            if(!gamepad2.right_stick_button && g2ReleaseRightStick) {
                // Stops any suhs that are currently playing
                if(suh1.isPlaying())
                    suh1.pause();
                if(suh2.isPlaying())
                    suh2.pause();
                if(suh3.isPlaying())
                    suh3.pause();
                if(suh4.isPlaying())
                    suh4.pause();
                if(suh5.isPlaying())
                    suh5.pause();
                if(suh6.isPlaying())
                    suh6.pause();
                if(suh7.isPlaying())
                    suh7.pause();
                if(suh8.isPlaying())
                    suh8.pause();
                // Uses the previous value of the random suh to ensure no repeats
                while (randomSuh == previousSuh)
                    randomSuh = (int) (Math.random() * 8 + 1);
                previousSuh = randomSuh;
                // Plays the suh corresponding to the random suh value
                if(randomSuh == 1)
                    playSound(suh1);
                if(randomSuh == 2)
                    playSound(suh2);
                if(randomSuh == 3)
                    playSound(suh3);
                if(randomSuh == 4)
                    playSound(suh4);
                if(randomSuh == 5)
                    playSound(suh5);
                if(randomSuh == 6)
                    playSound(suh6);
                if(randomSuh == 7)
                    playSound(suh7);
                if(randomSuh == 8)
                    playSound(suh8);
            }
            g2ReleaseRightStick = gamepad2.right_stick_button;
            // Stops all sounds
            if (gamepad2.b) {
                if (isCenaSure.isPlaying())
                    isCenaSure.pause();
                if (yeahBoy.isPlaying())
                    yeahBoy.pause();
                if (noice.isPlaying())
                    noice.pause();
                if (krabs.isPlaying())
                    krabs.pause();
                if (shotsFired.isPlaying())
                    shotsFired.pause();
                if (filthyRishi.isPlaying())
                    filthyRishi.pause();
                if (zooweemama.isPlaying())
                    zooweemama.pause();
                if (suh1.isPlaying())
                    suh1.pause();
                if (suh2.isPlaying())
                    suh2.pause();
                if (suh3.isPlaying())
                    suh3.pause();
                if (suh4.isPlaying())
                    suh4.pause();
                if (suh5.isPlaying())
                    suh5.pause();
                if (suh6.isPlaying())
                    suh6.pause();
                if (suh7.isPlaying())
                    suh7.pause();
                if (suh8.isPlaying())
                    suh8.pause();
                if (succ.isPlaying())
                    succ.pause();
                if (allStar.isPlaying())
                    allStar.pause();
                if (seizeProduction.isPlaying())
                    seizeProduction.pause();
                if (reblexDeath.isPlaying())
                    reblexDeath.pause();
                if (succ.isPlaying())
                    succ.pause();
                if (zooweemama.isPlaying())
                    zooweemama.pause();
            }
        } else {
            // Toggles flywheels boolean based on A button release
            if (!gamepad2.a && releaseAButton)
                flywheels = !flywheels;
            releaseAButton = gamepad2.a;
            // Toggles the ball servo based on left and right bumper release
            if (!gamepad2.left_bumper && releaseLeftBumper) {
                reverseLockServo = false;
                runLockServo = !runLockServo;
            }
            releaseLeftBumper = gamepad2.left_bumper;
            if (!gamepad2.right_bumper && releaseRightBumper) {
                reverseLockServo = !reverseLockServo;
                runLockServo = false;
            }
            releaseRightBumper = gamepad2.right_bumper;
            if (gamepad2.right_stick_button)
                ballServo.setPosition(0.3);
            else
                ballServo.setPosition(1);
        }
        if (colorSensorBottom.red() > 15 && colorSensorBottom.blue() > 15 && colorSensorBottom.green() > 15)
            telemetry.addLine("It's h'white.");
        if (soundMode)
            mode = "Sound";
        else
            mode = "Robot";
        telemetry.addData("Gamepad 2 Mode", mode);
        telemetry.addData("Flywheel Speed", flywheelSpeed);
        telemetry.addData("Distance", distanceSensor.getLightDetected());
        telemetry.addData("motorFrontLeft", throttle + direction + strafe);
        telemetry.addData("motorFrontRight", throttle - direction - strafe);
        telemetry.addData("motorBackLeft", throttle + direction - strafe);
        telemetry.addData("motorBackRight", throttle - direction + strafe);
        telemetry.addData("side red", colorSensorSide.red());
        telemetry.addData("side blue", colorSensorSide.blue());
        telemetry.addData("side green", colorSensorSide.green());
        // Updates the telemetry
        telemetry.update();
    }
    public void stop() {
        // Stops all sound
        isCenaSure.stop();
        yeahBoy.stop();
        noice.stop();
        krabs.stop();
        zooweemama.stop();
        shotsFired.stop();
        filthyRishi.stop();
        suh1.stop();
        suh2.stop();
        suh3.stop();
        suh4.stop();
        suh5.stop();
        suh6.stop();
        suh7.stop();
        suh8.stop();
        succ.stop();
        allStar.stop();
        seizeProduction.stop();
        reblexDeath.stop();
        zooweemama.stop();
    }
    public void playSound(MediaPlayer mp) {
        // Resets and plays the sound given
        mp.seekTo(0);
        mp.start();
    }
}