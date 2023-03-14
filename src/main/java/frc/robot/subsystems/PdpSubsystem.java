package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PdpSubsystem extends SubsystemBase {
    PowerDistribution m_pdp;

    public PdpSubsystem() {
        m_pdp = new PowerDistribution(9, ModuleType.kRev);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("V (bat)", m_pdp.getVoltage());
        SmartDashboard.putNumber("A (tot)", m_pdp.getTotalCurrent());
        SmartDashboard.putData(m_pdp);
    }
}
