package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.simulation.PDPSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Joysticks;
import frc.robot.Robot;

public class PdpSubsystem extends SubsystemBase {
    PowerDistribution m_pdp;
    PDPSim m_simPdp;

    public PdpSubsystem() {
        if (Robot.isReal()) {
            m_pdp = new PowerDistribution(9, ModuleType.kRev);
        } else {
            m_pdp = new PowerDistribution();
            m_simPdp = new PDPSim(m_pdp);
        }
    }

    double voltage = 0;
    double totalCurrent = 0;
    double totalPower = 0;
    double totalEnergy = 0;
    double energySum = 0;
    
    @Override
    public void periodic() {
        voltage = m_pdp.getVoltage();
        SmartDashboard.putNumber("V (bat)", voltage);

        // Get the total current of all channels.
        totalCurrent = m_pdp.getTotalCurrent();
        SmartDashboard.putNumber("Total Current (A)", totalCurrent);

        // Get the total power of all channels.
        // Power is the bus voltage multiplied by the current with the units Watts.
        totalPower = m_pdp.getTotalPower();
        SmartDashboard.putNumber("Total Power (W)", totalPower);
        
        energySum += totalPower * 0.02; // may be equal to getTotalEnergy() -- not tested
        batteryEnergy -= energySum;
        // Get the total energy of all channels.
        // Energy is the power summed over time with units Joules.
        totalEnergy = m_pdp.getTotalEnergy();  // 0 during sim ??
        SmartDashboard.putNumber("Total Energy (J)", totalEnergy);
        
        SmartDashboard.putData(m_pdp);

        // TODO: Do something with this info, e.g., cut max power if not going to make it to end of match
        // see https://docs.wpilib.org/en/stable/docs/software/roborio-info/roborio-brownouts.html for more brownout info

        // From earlier commit [lesserc]
        var currentLF_Drive = m_pdp.getCurrent(0);
        var currentRF_Drive = m_pdp.getCurrent(19);
        var currentRL_Drive = m_pdp.getCurrent(2);
        var currentRR_Drive = m_pdp.getCurrent(17);
    
        //Drive Motor Currents
        SmartDashboard.putNumber("FL Drive Current", currentLF_Drive);
        SmartDashboard.putNumber("FR Drive Current", currentRF_Drive);
        SmartDashboard.putNumber("RL Drive Current", currentRL_Drive);
        SmartDashboard.putNumber("RR Drive Current", currentRR_Drive);
    
    }
    
    // SIMULATION -- Press F5, Sim Gui is enough (don't need SimDS)
    // GUI will appear. Hit pause and set up plots, etc.
    double elapsedTime = 0;
    double batteryVoltage = 12.6; // basic voltage with minimal current draw
    double batteryEnergyStart = 18 /* Ah */ * 12 /* V */ * 60 /* m/h */ * 60 /* s/m */; // Watt-seconds (J)
    double batteryEnergy = 18 /* Ah */ * 12 /* V */ * 60 /* m/h */ * 60 /* s/m */; // Watt-seconds (J)
    double neoWatts = 380;
    double neo550Watts = 279;
    double previousDirection = 0;

    @Override
    public void simulationPeriodic() {
        elapsedTime += 0.02;
        var leftX = MathUtil.applyDeadband(Joysticks.DRIVE_CONTROLLER.getLeftX(), 0.1);
        var leftY = MathUtil.applyDeadband(Joysticks.DRIVE_CONTROLLER.getLeftY(), 0.1);
        var direction = Math.atan2(leftY, leftX);
        var speed = Math.sqrt(leftY*leftY + leftX*leftX);
        //var drivePower = neoWatts * speed;
        var driveCurrent = 40 * speed;
        //var turnPower = neoWatts * Math.abs(previousDirection - direction)/Math.PI;
        var turnCurrent = 40 * Math.abs(previousDirection - direction)/Math.PI;
        previousDirection = direction;
    
        m_simPdp.setCurrent(0, driveCurrent); // fictional channels
        m_simPdp.setCurrent(1, turnCurrent);
        m_simPdp.setCurrent(2, driveCurrent);
        m_simPdp.setCurrent(3, turnCurrent);
        m_simPdp.setCurrent(4, driveCurrent);
        m_simPdp.setCurrent(5, turnCurrent);
        m_simPdp.setCurrent(6, driveCurrent);
        m_simPdp.setCurrent(7, turnCurrent);
        m_simPdp.setCurrent(10, 2.0);
        
        double voltageDrop = 0.011 * totalCurrent;
        m_simPdp.setVoltage(Math.max(0, batteryVoltage - voltageDrop));

        batteryVoltage -= (batteryEnergyStart - batteryEnergy)/batteryEnergyStart/5000.0;
    }
}
