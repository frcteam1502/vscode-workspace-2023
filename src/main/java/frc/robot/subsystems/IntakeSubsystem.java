package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class IntakeSubsystem extends SubsystemBase {
  public DoubleSolenoid DoublePH = new DoubleSolenoid(1, PneumaticsModuleType.REVPH,frc.robot.Constants.PneumaticsConstants.kforwardchannel,frc.robot.Constants.PneumaticsConstants.kreversechannel);
  public Compressor phCompressor = new Compressor(1, PneumaticsModuleType.REVPH);
  
  public DigitalInput LimitSwitch = new DigitalInput(20); //TODO: get actual channel

  final static class INTAKE_CONSTANTS {  
    // PID coefficients (sample values, TBD)
    public final static double kP = 0.1;
    public final static double kI = 1e-4;
    public final static double kD = 1;
    public final static double kIz = 0;
    public final static double kFF = 0;
    public final static double kMaxOutput = 1;
    public final static double kMinOutput = -1;
  }
  
  private SparkMaxPIDController pidControllerIntake;
  private final CANSparkMax intakeMotor = Constants.Motors.INTAKE_MOTOR;
  
  public IntakeSubsystem() {
    intakeMotor.restoreFactoryDefaults();

    pidControllerIntake = intakeMotor.getPIDController();

    // set PID coefficients
    pidControllerIntake.setP(INTAKE_CONSTANTS.kP);
    pidControllerIntake.setI(INTAKE_CONSTANTS.kI);
    pidControllerIntake.setD(INTAKE_CONSTANTS.kD);
    pidControllerIntake.setIZone(INTAKE_CONSTANTS.kIz);
    pidControllerIntake.setFF(INTAKE_CONSTANTS.kFF);
    pidControllerIntake.setOutputRange(INTAKE_CONSTANTS.kMinOutput, INTAKE_CONSTANTS.kMaxOutput);
  }

  public void setIn() {
    DoublePH.set(Value.kReverse);
  }  
  
  public void setOut() {
      DoublePH.set(Value.kForward);
  }

  public void SetIntake(double rotations) {
    pidControllerIntake.setReference(rotations, CANSparkMax.ControlType.kPosition);
  }

  public void TurnOnIntake() {
    // the intake pneumatic program below
    // adjust speed- acording to game piece color
    intakeMotor.set(0.2);
  }

  public void TurnOffIntake() {
    // turn off intake
    intakeMotor.set(0);
  }

  public void ReverseIntake() {
    // spit out game piece
    intakeMotor.set(-0.2);
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