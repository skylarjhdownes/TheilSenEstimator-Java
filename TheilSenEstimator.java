import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;
import org.apache.commons.math3.stat.StatUtils;

public class TheilSenEstimator
{
    private double _medianSlope;
    private double _medianYIntercept;

    public TheilSenEstimator(){

    }

    public void process(List<Point2D> points){
        List<Double> slopes = calcSlopes(points);
        _medianSlope = StatUtils.percentile(slopes.stream().mapToDouble(d -> d).toArray(), 50);
        List<Double> intercepts = calcIntercepts(points, _medianSlope);
        _medianYIntercept = StatUtils.percentile(intercepts.stream().mapToDouble(d -> d).toArray(), 50);
    //TODO: Handle empty lists, ex: when all the points have the same X value.
    }


    List calcSlopes(List<Point2D> points){
        List slopes = new ArrayList();
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                if (points.get(i).getX() != points.get(j).getX()) {
                    slopes.add((points.get(i).getY() - points.get(j).getY()) / (points.get(i).getX() - points.get(j).getX()));
                }
            }
        }
        return slopes;
    }

    List calcIntercepts(List<Point2D> points, double medianSlope){
        List intercepts = new ArrayList();
        points.forEach((point) -> intercepts.add(point.getY() - medianSlope * point.getX()));
        return intercepts;
    }

    Double getThielSenSlope(){
        return _medianSlope;
    }

    Double getThielSenYIntercept(){
        return _medianYIntercept;
    }
}



