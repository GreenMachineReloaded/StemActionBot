package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by User on 8/30/2015.
 */

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class NxttankDrive extends OpMode {

    DcMotor motorRight;
    DcMotor motorLeft;
    DcMotorController wheelController;
    DcMotorController.DeviceMode devMode;

    double scale_motor_power (double p_power)
    {
        //
        // Assume no scaling.
        //
        double l_scale = 0.0f;

        //
        // Ensure the values are legal.
        //
        double l_power = Range.clip (p_power, -1, 1);

        double[] l_array =
                { 0.00, 0.05, 0.09, 0.10, 0.12
                        , 0.15, 0.18, 0.24, 0.30, 0.36
                        , 0.43, 0.50, 0.60, 0.72, 0.85
                        , 1.00, 1.00
                };

        //
        // Get the corresponding index for the specified argument/parameter.
        //
        int l_index = (int) (l_power * 16.0);
        if (l_index < 0)
        {
            l_index = -l_index;
        }
        else if (l_index > 16)
        {
            l_index = 16;
        }

        if (l_power < 0)
        {
            l_scale = -l_array[l_index];
        }
        else
        {
            l_scale = l_array[l_index];
        }

        return l_scale;

    } // PushBotManual::scale_motor_power

    @Override
    public void init()
    {
        motorRight = hardwareMap.dcMotor.get("motor_2");
        motorLeft = hardwareMap.dcMotor.get("motor_1");
        wheelController = hardwareMap.dcMotorController.get("wheels");
        devMode = DcMotorController.DeviceMode.WRITE_ONLY;

    }

    public void loop()
    {

        float l_gp1_left_stick_y = -gamepad1.left_stick_y;
        float right
                = (float)scale_motor_power (l_gp1_left_stick_y);

        float l_gp1_right_stick_y = -gamepad1.right_stick_y;
        float left
                = (float)scale_motor_power (l_gp1_right_stick_y);

        motorRight.setPower(right);
        motorLeft.setPower(left);

        //telemetry.addData("left motor", motorLeft.getPower());
        //telemetry.addData("right motor", motorRight.getPower());
    }
}
