import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class TheilSenEstimatorTest {

    public TheilSenEstimatorTest() {
    }

    @Test
    public void calcSlopesShouldReturnASlope() {
        List<Point2D> points = new ArrayList(){
            {
                add(new Point2D.Double(1.0, 1.0));
                add(new Point2D.Double(2.0, 2.0));
            }
        };
        TheilSenEstimator instance = new TheilSenEstimator();
        assertEquals(new ArrayList(){{add(1.0);}}, instance.calcSlopes(points));
    }

    @Test
    public void calcSlopesShouldReturnSeveralSlopes() {
        List<Point2D> points = new ArrayList(){
            {
                add(new Point2D.Double(1.0, 1.0));
                add(new Point2D.Double(2.0, 2.0));
                add(new Point2D.Double(3.0, 5.0));
            }
        };
        TheilSenEstimator instance = new TheilSenEstimator();
        assertEquals(new ArrayList(){{add(1.0);add(2.0);add(3.0);}}, instance.calcSlopes(points));
    }

    @Test
    public void calcSlopesShouldHandleMultiplePointsInTheSameX() {
        List<Point2D> points = new ArrayList(){
            {
                add(new Point2D.Double(1.0, 1.0));
                add(new Point2D.Double(2.0, 2.0));
                add(new Point2D.Double(1.0, 2.0));
                add(new Point2D.Double(1.0, 3.0));
            }
        };
        TheilSenEstimator instance = new TheilSenEstimator();
        assertEquals(new ArrayList(){{add(1.0);add(0.0);add(-1.0);}}, instance.calcSlopes(points));
    }


    @Test
    public void calcInterceptsShouldReturnAYIntercept() {
        List<Point2D> points = new ArrayList(){
            {
                add(new Point2D.Double(1.0, 1.0));
            }
        };
        double medianSlope = 2;
        TheilSenEstimator instance = new TheilSenEstimator();
        assertEquals(new ArrayList(){{add(-1.0);}}, instance.calcIntercepts(points, medianSlope));
    }

    @Test
    public void calcInterceptsShouldReturnSeveralYIntercepts() {
        List<Point2D> points = new ArrayList(){
            {
                add(new Point2D.Double(1.0, 1.0));
                add(new Point2D.Double(2.0, 2.0));
                add(new Point2D.Double(3.0, 5.0));
            }
        };
        double medianSlope = 2;
        TheilSenEstimator instance = new TheilSenEstimator();
        assertEquals(new ArrayList(){{add(-1.0);add(-2.0);add(-1.0);}}, instance.calcIntercepts(points, medianSlope));
    }

    @Test
    public void processShouldGiveASlopeAndYIntercept() {
        List<Point2D> points = new ArrayList(){
            {
                add(new Point2D.Double(1.0, 1.0));
                add(new Point2D.Double(2.0, 2.0));
                add(new Point2D.Double(3.0, 5.0));
            }
        };
        TheilSenEstimator instance = new TheilSenEstimator();
        instance.process(points);
        assertEquals(2.0, instance.getThielSenSlope(), 0.01);
        assertEquals(-1.0, instance.getThielSenYIntercept(), 0.01);
    }
}
