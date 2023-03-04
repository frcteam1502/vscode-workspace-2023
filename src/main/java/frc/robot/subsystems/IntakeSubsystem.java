package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;


public class IntakeSubsystem extends SubsystemBase {
  public DoubleSolenoid DoublePH = new DoubleSolenoid(15, PneumaticsModuleType.REVPH,frc.robot.Constants.PneumaticsConstants.kforwardchannel,frc.robot.Constants.PneumaticsConstants.kreversechannel);

  private final CANSparkMax intakeMotor = Constants.Motors.INTAKE_MOTOR;
  
  public IntakeSubsystem() {
    if (Constants.SystemMap.IntakeSubsystem.IsEnabled) {
      intakeMotor.restoreFactoryDefaults();
      intakeMotor.setSmartCurrentLimit(60);
      DoublePH.set(Value.kReverse);
    }
  }

  public void setIn() {
    DoublePH.set(Value.kReverse);
  }  
  
  public void setOut() {
    DoublePH.set(Value.kForward);
  }

  public void TurnOnIntake() {
    intakeMotor.set(-1);
  }

  public void TurnOffIntake() {
    intakeMotor.set(0);
  }

  public void Toggle() {
    if (DoublePH.get() == Value.kForward)
    {
      setIn();
      TurnOffIntake();
    }
    else
    {
      setOut();
      TurnOnIntake();
    }
  }

  public void OnPressed() {
    TurnOnIntake();
    setOut();
  }

  public void OnReleased() {
    TurnOffIntake();
    setIn();
  }
}
