package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;


public class IntakeSubsystem extends SubsystemBase {
  public DoubleSolenoid DoublePH = new DoubleSolenoid(1, PneumaticsModuleType.REVPH,frc.robot.Constants.PneumaticsConstants.kforwardchannel,frc.robot.Constants.PneumaticsConstants.kreversechannel);
  public Compressor phCompressor = new Compressor(1, PneumaticsModuleType.REVPH);

  private final CANSparkMax intakeMotor = Constants.Motors.INTAKE_MOTOR;
  
  public IntakeSubsystem() {
    intakeMotor.restoreFactoryDefaults();
  }

  public void setIn() {
    DoublePH.set(Value.kReverse);
  }  
  
  public void setOut() {
      DoublePH.set(Value.kForward);
  }

  public void TurnOnIntake() {
    intakeMotor.set(0.2);
  }

  public void TurnOffIntake() {
    intakeMotor.set(0);
  }

  public void OnPressed() {
    TurnOnIntake();
    setOut();
  }

  public void OnReleased() {
    TurnOffIntake();
    setIn();
  }

  public void OnPressed() {
    TurnOnIntake();
    if(LimitSwitch.get()) setOut();
  }

  public void OnReleased() {
    TurnOffIntake();
    if(!LimitSwitch.get()) setIn();
  }
}
