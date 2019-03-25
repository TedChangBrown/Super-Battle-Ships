package geometry;


import java.util.List;

/**
 * Coordinate: Abstract class that defines the
 * properties of an object in order to be stored within
 * a KD node.
 */
public abstract class Coordinate {
  /**
   * Gets the coordinates that represent the location of the KD object.
   *
   * @return coordinates of the object.
   */
  public abstract List<Double> getCoordinates();

  /**
   * Gets the euclidian distance between one KD object and another.
   *
   * @param target the target who's distance
   *               from the current object will be returned.
   * @return the distance beteween the two
   * @throws IllegalArgumentException if the Coordinates are not the same size
   */
  public abstract double getEuclidianDistance(Coordinate target)
          throws IllegalArgumentException;

  /**
   * Gets the distance between the object and a target along a particular axis.
   *
   * @param target : the target who will be measured
   * @param axis   : the axis along which the distance will be measured
   * @return the axial distance between the two
   * @throws IllegalArgumentException if the Coordinates are not the same size
   */
  public abstract double getAxisDistance(Coordinate target, int axis)
          throws IllegalArgumentException;

  /**
   * Gets the unique identifier for the object.
   *
   * @return the string identifier of the object.
   */
  public abstract String getIdentifier();

}
