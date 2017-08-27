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
 * Last updated on 8/27/2017.
 */

@TeleOp(name="TeleOpMain", group="TeleOp")
@Disabled
public class TeleOpMain extends OpMode {
    // ***System variables***
    // Gets the application context from the main activity for sounds
    Context appContext = FtcRobotControllerActivity.appContext;
    // Initializes and creates media players for sounds
    MediaPlayer filthyRishi = MediaPlayer.create(appContext, R.raw.filthyrishi);
    // ***Robot variables***
    // Initializes DC motors on the robot
    DcMotor motorFrontLeft, motorFrontRight, motorBackLeft, motorBackRight;
    // ***Control variables***
    // Initializes doubles for getting controller values
    double direction, throttle, strafe;
    // Initializes booleans for button releases
    boolean releaseAButton, releaseBButton;
    public void init() {
        // Defines motors from config file
        motorFrontLeft = this.hardwareMap.dcMotor.get("motorFrontLeft");
        motorFrontRight = this.hardwareMap.dcMotor.get("motorFrontRight");
        motorBackLeft = this.hardwareMap.dcMotor.get("motorBackLeft");
        motorBackRight = this.hardwareMap.dcMotor.get("motorBackRight");
        // Reverses motors mounted in the opposite direction
        motorFrontLeft.setDirection(DcMotor.Direction.REVERSE);
        motorBackLeft.setDirection(DcMotor.Direction.REVERSE);
    }
    public void loop() {
        // Sets direction based on trigger values
        direction = gamepad1.right_trigger - gamepad1.left_trigger;
        // Sets throttle based on left joystick y value
        throttle = -gamepad1.left_stick_y;
        // Sets strafe based on left joystick x value
        strafe = gamepad1.left_stick_x;
        // Sets the power of the drive motors based on throttle, direction, and strafe values
        motorFrontLeft.setPower(throttle + direction + strafe);
        motorFrontRight.setPower(throttle - direction - strafe);
        motorBackLeft.setPower(throttle + direction - strafe);
        motorBackRight.setPower(throttle - direction + strafe);
        if(!gamepad1.a && releaseAButton)
            playSound(filthyRishi);
        releaseAButton = gamepad1.a;
        if(!gamepad1.b && releaseBButton) {
            if (filthyRishi.isPlaying())
                filthyRishi.pause();
        }
        releaseBButton = gamepad1.b;
        telemetry.addData("a", gamepad1.a);
        telemetry.addData("rel", releaseAButton);
        // Updates the telemetry
        telemetry.update();
    }
    public void stop() {
        // Stops all sound
        filthyRishi.stop();
    }
    public void playSound(MediaPlayer mp) {
        // Resets and plays the sound given
        mp.seekTo(0);
        mp.start();
    }
}
