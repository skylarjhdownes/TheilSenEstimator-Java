import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.stat.StatUtils;

public class TheilSenEstimator
{
    private double _medianSlope;
    private double _medianYIntercept;

    public TheilSenEstimator(){

    }

    public void process(List<Point2D> points) throws InsufficientXCoordinatesException {
        List<Double> slopes = calcSlopes(points);
        _medianSlope = StatUtils.percentile(slopes.stream().mapToDouble(d -> d).toArray(), 50);
        if (Double.isNaN(_medianSlope)){
            throw new InsufficientXCoordinatesException();
        }
        List<Double> intercepts = calcIntercepts(points, _medianSlope);
        _medianYIntercept = StatUtils.percentile(intercepts.stream().mapToDouble(d -> d).toArray(), 50);
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

    static class InsufficientXCoordinatesException extends Exception
    {
        @Override
        public String getMessage(){
            return "Theil-Sen estimation requires at least two points with differing x coordinates.";
        }
    }
}
