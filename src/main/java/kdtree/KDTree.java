package kdtree;

import geometry.Coordinate;
import geometry.CoordinateComparator;
import geometry.DistanceComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * KDTree: Data structure that organizes set of Coordinates
 * into a K-Dimensional binary tree. Structure has the ability to
 * query for nearest neighbors to Coordinates and Coordinates
 * within a certain range.
 *
 * @param <T> is the tpe of Coordinate contained in the tree.
 */
public class KDTree<T extends Coordinate> {
  private KDNode<T> root;
  private int k;
  private ArrayList<CoordinateComparator> comparators;
  private int maxSize;
  private HashMap<String, T> map = new HashMap<>();

  /**
   * Contructor for the KD tree. Creates a KD tree
   * with given dimension and points.
   *
   * @param k      the dimensionality of the tree
   * @param points the initial set of points to construct the tree with.
   * @throws IllegalArgumentException if dimension of the tree is less than 1
   */
  public KDTree(int k, ArrayList<T> points)
          throws IllegalArgumentException {
    if (k < 1) {
      throw new IllegalArgumentException();
    }
    this.k = k;
    this.maxSize = points.size();
    comparators = new ArrayList<>();
    for (int i = 0; i < k; i++) {
      comparators.add(new CoordinateComparator(i));
    }
    this.root = this.recursiveKDConstruction(points, 0);
  }

  /**
   * Method to recursivley construct the tree by sorting and
   * diving the current sublist
   * in half on the median, then recursing on the left and right
   * neighbors until there are no objects.
   *
   * @param objs  The objs to construct the current subtree with
   * @param depth The current depth that we are recurring on
   * @return The median node that has been split on
   * @throws IllegalArgumentException if there are objects
   *                                  * with different dimensions entered.
   */
  public KDNode<T> recursiveKDConstruction(ArrayList<T> objs, int depth)
          throws IllegalArgumentException {
    if (objs.size() == 0) { //base case there are no more objects for the node
      return null;
    }
    int axis = depth % this.k;
    //sort the current sublist on the relevant axis.
    try {
      Collections.sort(objs, comparators.get(axis));
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new IllegalArgumentException();
    }

    int med = objs.size() / 2;
    T medianPoint = objs.get(med);
    if (medianPoint.getIdentifier() == null
            || medianPoint.getCoordinates().size() < this.k) {
      throw new IllegalArgumentException();
    }
    if (!map.containsKey(medianPoint.getIdentifier())) {
      map.put(medianPoint.getIdentifier(), medianPoint);
    } else {
      throw new IllegalArgumentException();
    }

    KDNode median = new KDNode(medianPoint, axis);
    //split list into left and right sublists
    ArrayList<T> leftObjs = new ArrayList<T>(objs.subList(0, med));
    ArrayList<T> rightObjs = new ArrayList<T>(objs.subList((med + 1),
            objs.size()));
    //recurs on the left and right with the left and right sublists.
    median.setLeft(recursiveKDConstruction(leftObjs, depth + 1));
    median.setRight(recursiveKDConstruction(rightObjs, depth + 1));

    return median;
  }

  /**
   * Gets all the stars within a certain radius of the given
   * target. Iterativley recurses through parts of the subtree to
   * see if nodes are within the radius, and skips over parts of the tree
   * that could not be within the radius of the target.
   *
   * @param target target to find radius about
   * @param radius radius to look for around the target
   * @return Priority queue that contains the nodes
   * within the radius specified around the target
   */
  public PriorityQueue getRadius(T target, double radius) {
    //create new priority queue with max size of
    // the number of points in the tree.
    PriorityQueue aq;
    if (this.root != null) {
      aq = new PriorityQueue<T>(maxSize,
              new DistanceComparator<>(target, true));
      radiusHelper(this.root, target, radius, aq);
    } else {
      aq = new PriorityQueue<T>();
    }
    return aq;
  }

  /**
   * Helper function that performs recursison on any nodes
   * that could possible be within the radius or have
   * children that could be within the radius.
   *
   * @param node   The current node that is being recursed on
   * @param target the target coordinate we are searching around
   * @param radius the radius that we are searching within
   * @param q      the priority queue holding the current nodes
   */
  private void radiusHelper(KDNode<T> node, T target,
                            double radius, PriorityQueue<T> q) {
    if (node == null) {
      //recursed on a node that doesn't exist so exit
      return;
    }
    //if searching by name don't include the node we searched on
    Boolean isNode = target.equals(node.getData());
    if (!isNode) {
      double d = node.getData().getEuclidianDistance(target);
      if (d <= radius) {
        q.add(node.getData());
      }
    }
    //reached a leaf node so uwind recursion
    if (node.getLeft() == null && node.getRight() == null) {
      return;
    }

    KDNode<T> nextNode;
    KDNode<T> secondCheck;
    //follow the KD tree to see which node to recurse on next
    if (target.getCoordinates().get(node.getAxis())
            > node.getData().getCoordinates().get(node.getAxis())) {
      nextNode = node.getRight();
      secondCheck = node.getLeft();
    } else {
      nextNode = node.getLeft();
      secondCheck = node.getRight();
    }

    radiusHelper(nextNode, target, radius, q);
    //recur on other branch if subtree could contain possible node
    double axisDistance = target.getAxisDistance(
            node.getData(), node.getAxis());

    if (axisDistance < radius) {
      radiusHelper(secondCheck, target, radius, q);
    }
  }

  /**
   * Gets the K nearest neighbors to the target node. Recursivly
   * goes through parts of the subtree to
   * see if nodes are closer than the current nodes and
   * skips over parts of the tree
   * that could not be any closer.
   *
   * @param target the target that we are searching
   *               nearest neighbors around
   * @param num    the number of nearest neighbors we want
   * @return priority queue containing the nearest neighbors specified
   */
  public PriorityQueue getKNN(T target, int num) {
    if (num == 0 || this.root == null) {
      return new PriorityQueue<T>(1);
    }
    //create priority queue that organizes from greatest distance to least
    PriorityQueue<T> dq = new PriorityQueue<T>(num,
            new DistanceComparator<T>(target, false));
    helperKNN(this.root, target, num, dq);
    //to get the nodes in ascending order
    // create a new priority queue that organizes by acsending order
    PriorityQueue<T> aq = new PriorityQueue<T>(num,
            new DistanceComparator<T>(target, true));
    while (!dq.isEmpty()) {
      aq.add(dq.poll()); //move the nodes into the ascending queue
    }
    return aq;
  }

  /**
   * Helper function that performs recursison on any nodes
   * that are closer than the farthest neighbor in the queue
   * or are within the natural path of the tree.
   *
   * @param node   The current node that is being recursed on
   * @param target the target coordinate we are searching around
   * @param num    the number of nearest neighbors we want
   * @param q      the priority queue holding the current nodes
   */
  private void helperKNN(KDNode<T> node, T target,
                         int num, PriorityQueue<T> q) {
    if (node == null) {
      return;
    }
    //prevent adding specific object searched for to queue
    Boolean isNode = target.equals(node.getData());
    if (!isNode) {
      if (q.size() >= num) { //if we have K neighbors already
        // then check if current node is closer

        double largestDistance = q.peek().getEuclidianDistance(target);
        double currDistance = node.getData().getEuclidianDistance(target);
        if (currDistance < largestDistance) {
          q.poll();
          q.add(node.getData());
        }
      } else {
        //if we don't have K potential neighbors then just add the node
        q.add(node.getData());
      }
    }
    //if at leaf node unwind recursion
    if (node.getLeft() == null && node.getRight() == null) {
      return;
    }
    KDNode<T> nextNode;
    KDNode<T> secondCheck;
    //folow tree to find next node to recurse on
    if (target.getCoordinates().get(node.getAxis())
            <= node.getData().getCoordinates().get(node.getAxis())) {
      nextNode = node.getLeft();
      secondCheck = node.getRight();
    } else {
      nextNode = node.getRight();
      secondCheck = node.getLeft();
    }
    helperKNN(nextNode, target, num, q);
    double currentLargest = q.peek().getEuclidianDistance(target);
    double axisDistance = target.getAxisDistance(
            node.getData(), node.getAxis());
    //if node is closer than farthest potential node
    // on the axis then recurse on other subtree
    if (axisDistance < currentLargest || q.size() < num) {
      helperKNN(secondCheck, target, num, q);
    }
  }

  /**
   * Accessor for the root of the tree.
   *
   * @return the root of the tree.
   */
  public KDNode<T> getRoot() {
    return this.root;
  }
}
