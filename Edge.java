/**
 * Created by marcusshannon on 11/27/15.
 */
public class Edge {

  Image first;
  Image second;
  double d;

  Edge(Image first, Image second, double d) {
    this.first = first;
    this.second = second;
    this.d = d;
  }

  void union() {
    first.find().parent = second.find();
  }
}

