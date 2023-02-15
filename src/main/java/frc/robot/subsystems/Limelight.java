
package frc.robot.subsystems;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Limelight {
    double distance;
   
    public void findDistance(){
        Target target = getTarget();
        distance = target.tx;
        //SmartDashboard.putNumber("Distance", distance);
    }
//Limelight wont be centored so do math later when robot built, im sick and have bad brain fog get off my back for english
    public static class Target {
        public double tx;
        public double ty;
        public double tv;
        public double ta;
        public double ts;

        Target(double x, double y, double v, double a, double s) {
            tx = x;
            ty = y;
            tv = v;
            ta = a;
            ts = s;
        }
  }

  public static Target getTarget() {
    double tx = getTableEntry("tx").getDouble(0);
    double ty = getTableEntry("ty").getDouble(0);
    double ta = getTableEntry("ta").getDouble(0);
    double tv = getTableEntry("tv").getDouble(0);
    double ts = getTableEntry("ts").getDouble(0);
    return new Target(tx, ty, tv, ta, ts);
  }

  private static NetworkTableEntry getTableEntry(String entry) {
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry(entry);
  }

  // April Tag Things


  public void setPipeline(int pipe){
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(pipe);
  }

  public String whatPipeline(int pipe){
    String result= "Only 0 and 1 are valid pipelines!";
    if(pipe == 0) result= "April Tag Pipeline";
    else if (pipe == 1) result= "RetroReflector Pipeline";
    return result;
  }

  /**
   * Outputs the primary in-view April Tag seen by the robot
   * PIPELINE MUST BE SET TO 1
   * 
   * @return The April Tag data registered by the robot
   */
  public double[] getAprilTagData(){
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tid").getDoubleArray(new double[6]);
  }

  /**
   * Pipeline must be set to 1 and April Tag being tracked
   * 
   * @return The location of the April Tag in 3D space relative to the Camera
   */
  public static double[] getAprilTagCoord(){
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry("targetpose_cameraspace").getDoubleArray(new double[6]);
  }

}