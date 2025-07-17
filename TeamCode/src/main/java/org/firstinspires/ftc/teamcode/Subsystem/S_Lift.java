package org.firstinspires.ftc.teamcode.Subsystem;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import static java.util.Base64.getEncoder;

import android.widget.Checkable;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.arcrobotics.ftclib.controller.PIDFController;

public class S_Lift implements Checkable {
    private boolean status = false;
    private boolean initialized = false;

    private DcMotor leftLift, rightLift;
    private PIDFController controller;
    private static S_Lift m_Instance;

    private S_Lift() {
        DcMotor leftLift = hardwareMap.get(DcMotor.class, "leftLift");
        DcMotor rightLift = hardwareMap.get(DcMotor.class, "rightLift");

        leftLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftLift.setDirection(DcMotorSimple.Direction.REVERSE);
        rightLift.setDirection(DcMotorSimple.Direction.FORWARD);

        controller = new PIDFController(0,0,0,0);
        controller.setTolerance(0);

        leftLift.getCurrentPosition();
        rightLift.getCurrentPosition();

        initialized = true;
        status = true;
    }

    public static S_Lift getInstance() {
        if(m_Instance == null) {
            m_Instance = new S_Lift();
        }
        return m_Instance;
    }

    public void set(double speed) {
        leftLift.setPower(speed);
        rightLift.setPower(speed);
    }

    public void setSetpoint(double setpoint) {
        controller.setSetPoint(setpoint);
    }

    public void moveToSetpoint(){
        set(controller.calculate(getEncoder()));
    }

    public void stop() {
        leftLift.setPower(0);
        rightLift.setPower(0);
    }

    public double getEncoder() {
        return (rightLift.getCurrentPosition() - leftLift.getCurrentPosition()) / 2;
    }

    public boolean atSetpoint() {
        return controller.atSetPoint();
    }

    public boolean getInitialized() {
        return initialized;
    }

    public boolean checkSubsystem() {
        status = getInitialized();

        return status;
    }
    @Override
    public void setChecked(boolean b) {
    }
    @Override
    public boolean isChecked() {
        return false;
    }
    @Override
    public void toggle() {
    }
}
