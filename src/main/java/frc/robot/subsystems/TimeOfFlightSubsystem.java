package frc.robot.subsystems;

import com.playingwithfusion.TimeOfFlight;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TimeOfFlightSubsystem extends SubsystemBase {
    private TimeOfFlight tof;

    public TimeOfFlightSubsystem() {
        tof = new TimeOfFlight(0);
    }

    @Override
    public void periodic() {
        var range = tof.getRange();
        SmartDashboard.putNumber("TOF Range (mm)", range);
    }
}
