package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.libraries.LimelightHelpers;

public class Limelight { // NOT a subsysyem

static int pipe=0;
static double distance;

static final double LOAD_STATION_TAG_HEIGHT = .71;
static final double GRID_TAG_HEIGHT = .48;
static final double LIMELIGHT_HEIGHT = .63;
static final double TAG_SIZE = .15;
static final double LIMELIGHT_FOV_DEGREES = 59.6;//Per Limelight docs
static final double LIMELIGHT_RES_HORIZONTAL = 320;//Per Limelight docs
static final double DEGREES_PER_PIXEL = LIMELIGHT_FOV_DEGREES/LIMELIGHT_RES_HORIZONTAL;

   
public static double findDistance(){
  Target target = getTarget();
  double side_pixels = target.thor;
  double id = target.tid;
  double valid = target.tv;
  double distance = 1000;//Max out distance if target is not valid

  if(valid == 1){
    double angle_deg = (side_pixels/2)*DEGREES_PER_PIXEL;
    double angle_rad = Math.toRadians(angle_deg);
    distance = (TAG_SIZE/2)/(Math.tan(angle_rad));
  }

  SmartDashboard.putNumber("Tag ID", id);
  SmartDashboard.putNumber("Pixels", side_pixels);
  SmartDashboard.putNumber("Calculated Distance", distance);
  
  return(distance);
}
    
//Limelight wont be centored so do math later when robot built, im sick and have bad brain fog get off my back for english
    public static class Target {
        public double tx;
        public double ty;
        public double tv;
        public double ta;
        public double ts;
        public double tid;
        public double thor;

        Target(double x, double y, double v, double a, double s, double id, double hor) {
            tx = x;
            ty = y;
            tv = v;
            ta = a;
            ts = s;
            tid = id;
            thor = hor;
        }
  }

  public static Target getTarget() {
    double tx = getTableEntry("tx").getDouble(0.0);//Horizontal offset from crosshair to target (degrees)
    double ty = getTableEntry("ty").getDouble(0.0);//Vertical offset from crosshair to target (degrees)
    double ta = getTableEntry("ta").getDouble(0.0);//Target area
    double tv = getTableEntry("tv").getDouble(0.0);//Whether the Limelight has valid targets (0 or 1)
    double tid = getTableEntry("tid").getDouble(0.0);
    double ts = getTableEntry("ts").getDouble(0.0);
    double thor = getTableEntry("thor").getDouble(0.0);
    return new Target(tx, ty, tv, ta, ts, tid, thor);
  }

  private static NetworkTableEntry getTableEntry(String entry) {
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry(entry);
  }

  // April Tag Things

  public static void changePipeline(){
  pipe++;
  badPipeline(pipe);
  setPipeline(pipe);
  }

  public static void setPipeline(int pipe){
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(pipe);    
  }

  public static int getPipeline() {
    return pipe;
  }

  public static String whatPipeline(int pipe){
    String result= "Only 0 and 1 and 2 are valid pipelines!";
    if(pipe == 0) result= "April Tag Pipeline";
    else if (pipe == 1) result= "RetroReflector Pipeline";
    else{
      result="Light off";
    }
    return result;
  }

  public static void badPipeline(int pipe){
    if(pipe >2) pipe = 0;
  }
  /**
   * Outputs the primary in-view April Tag seen by the robot
   * PIPELINE MUST BE SET TO 0
   * 
   * @return The April Tag data registered by the robot
   */
  public static double getAprilTagData(){
    return LimelightHelpers.getFiducialID("limelight");
  }

  /**
   * (tx, ty, ta)
   * 
   * @return The X and Y coordinates of the fidual marker being tracked and the area of the camera the marker takes
  */
  public static double[] getTagPos(){
    double[] tagPos = new double[3];
    tagPos[0] = LimelightHelpers.getTX("limelight");
    tagPos[1] = LimelightHelpers.getTY("limelight");
    tagPos[2] = LimelightHelpers.getTA("limelight");
    return tagPos;
  }

  /**
   * Pipeline must be set to 0 and April Tag being tracked
   * 
   * @return The location of the April Tag in 3D space relative to the Camera
   */
  public static double[] getAprilTagCoord(){
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry("targetpose_cameraspace").getDoubleArray(new double[6]);
  }

  /**
   * Pose relative to the robot
   * @return (x, y, z, qw, qx, qy, qz)
   */
  public static double[] getTargetPose(){
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry("targetPose").getDoubleArray(new double[7]);
  }

  /**
   * Pitch of target in degrees (positive up)
   * Yaw of target in degrees (positive right)
   * Percent of bounding box in screen (0-100)
   * Skew of target in degrees (counter-clockwise positive)
   * 
   * @return (targetPitch, targetYaw, targetArea, targetSkew)
   */
  public static double[] getTargetData(){
    double[] ret = new double[4];
    ret[0] = NetworkTableInstance.getDefault().getTable("limelight").getEntry("targetPitch").getDouble(0.0);
    ret[1] = NetworkTableInstance.getDefault().getTable("limelight").getEntry("targetYaw").getDouble(0.0);
    ret[2] = NetworkTableInstance.getDefault().getTable("limelight").getEntry("targetArea").getDouble(0.0);
    ret[3] = NetworkTableInstance.getDefault().getTable("limelight").getEntry("targetSkew").getDouble(0.0);

    SmartDashboard.putNumber("targetArea", ret[2]);

    return ret;
  }
}