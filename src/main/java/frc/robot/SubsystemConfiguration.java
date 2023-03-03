package frc.robot;

public class SubsystemConfiguration
{
  public Boolean IsEnabled = true;
  public int DiagnosticLevel = 0;

  public SubsystemConfiguration Disable() {
    IsEnabled = false;
    return this;
  }
  public SubsystemConfiguration Enable() {
    IsEnabled = true;
    return this;
  }
  
  public SubsystemConfiguration EnableDiagnostics(int level) {
    DiagnosticLevel = level;
    return this;
  }




}