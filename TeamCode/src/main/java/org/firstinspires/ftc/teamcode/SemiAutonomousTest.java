package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Rolling Thunder on 8/27/2017.
 * Last updated on 8/27/2017.
 */

@TeleOp(name="SemiAutonomousTest", group="Autonomous")
@Disabled
public class SemiAutonomousTest extends OpMode {
    DcMotor motorFrontLeft, motorFrontRight, motorBackLeft, motorBackRight;
    double throttle, strafe, direction;
    boolean releaseBButton, releaseXButton, releaseYButton;
    public void init () {
        motorFrontLeft = this.hardwareMap.dcMotor.get("motorFrontLeft");
        motorFrontRight = this.hardwareMap.dcMotor.get("motorFrontRight");
        motorBackLeft = this.hardwareMap.dcMotor.get("motorBackLeft");
        motorBackRight = this.hardwareMap.dcMotor.get("motorBackRight");
        motorFrontLeft.setDirection(DcMotor.Direction.REVERSE);
        motorBackLeft.setDirection(DcMotor.Direction.REVERSE);
        resetEncoders();
    }
    public void loop () {
        direction = gamepad1.right_trigger - gamepad1.left_trigger;
        throttle = -gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        motorFrontLeft.setPower(throttle + direction + strafe);
        motorFrontRight.setPower(throttle - direction - strafe);
        motorBackLeft.setPower(throttle + direction - strafe);
        motorBackRight.setPower(throttle - direction + strafe);
        if(gamepad1.left_stick_button)
            resetEncoders();
        if(!gamepad1.y && releaseYButton)
            moveForward(1, 1);
        releaseYButton = gamepad1.y;
        if(!gamepad1.x && releaseXButton)
            strafeLeft(1, 1);
        releaseXButton = gamepad1.x;
        if(!gamepad1.b && releaseBButton)
            strafeRight(1, 1);
        releaseBButton = gamepad1.b;
        telemetry.addData("MotorFrontLeft", motorFrontLeft.getCurrentPosition());
        telemetry.addData("MotorFrontRight", motorFrontRight.getCurrentPosition());
        telemetry.addData("MotorBackLeft", motorBackLeft.getCurrentPosition());
        telemetry.addData("MotorBackRight", motorBackRight.getCurrentPosition());
        telemetry.update();
    }

    public void moveForward(double rotations, double power) {
        motorFrontLeft.setPower(power);
        motorFrontRight.setPower(power);
        motorBackLeft.setPower(power);
        motorBackRight.setPower(power);
        double targetPosition = rotations * 1080;
        if(rotations > 0)
            while(motorFrontLeft.getCurrentPosition() < targetPosition && motorFrontRight.getCurrentPosition() < targetPosition && motorBackLeft.getCurrentPosition() < targetPosition && motorBackRight.getCurrentPosition() < targetPosition) {}
        else
            while (motorFrontLeft.getCurrentPosition() > targetPosition && motorFrontRight.getCurrentPosition() > targetPosition && motorBackLeft.getCurrentPosition() > targetPosition && motorBackRight.getCurrentPosition() > targetPosition) {}
        motorFrontLeft.setPower(0);
        motorFrontRight.setPower(0);
        motorBackLeft.setPower(0);
        motorBackRight.setPower(0);
        resetEncoders();
    }
    public void strafeLeft(double rotations, double power) {
        motorFrontLeft.setPower(-power);
        motorFrontRight.setPower(power);
        motorBackLeft.setPower(power);
        motorBackRight.setPower(-power);
        double targetPosition = rotations * 1080;
        while(motorFrontLeft.getCurrentPosition() > -targetPosition && motorFrontRight.getCurrentPosition() < targetPosition && motorBackLeft.getCurrentPosition() < targetPosition && motorBackRight.getCurrentPosition() > -targetPosition) {}
        motorFrontLeft.setPower(0);
        motorFrontRight.setPower(0);
        motorBackLeft.setPower(0);
        motorBackRight.setPower(0);
        resetEncoders();
    }
    public void strafeRight(double rotations, double power) {
        motorFrontLeft.setPower(power);
        motorFrontRight.setPower(-power);
        motorBackLeft.setPower(-power);
        motorBackRight.setPower(power);
        double targetPosition = rotations * 1080;
        while(motorFrontLeft.getCurrentPosition() < targetPosition && motorFrontRight.getCurrentPosition() > -targetPosition && motorBackLeft.getCurrentPosition() > -targetPosition && motorBackRight.getCurrentPosition() < targetPosition) {}
        motorFrontLeft.setPower(0);
        motorFrontRight.setPower(0);
        motorBackLeft.setPower(0);
        motorBackRight.setPower(0);
        resetEncoders();
    }
    public void resetEncoders() {
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
