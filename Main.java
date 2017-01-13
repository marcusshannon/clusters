import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by marcusshannon on 11/27/15.
 */
public class Main {

  static class EdgeCompare implements Comparator<Edge> {
    @Override
    public int compare(Edge edge1, Edge edge2) {
      if (edge1.d - edge2.d > 0) {
        return 1;
      }
      if (edge1.d - edge2.d < 0) {
        return -1;
      }
      return 0;
    }
  }

  public static void main(String[] args) throws FileNotFoundException {
    int k = Integer.valueOf(args[1]);
    Readable readable = new FileReader(args[0]);
    Scanner scanner = new Scanner(readable);
    for (int i = 0; i < 96; i = i + 1) {
      scanner.nextLine();
    }
    List<Image> images = new ArrayList<>();
    while (scanner.hasNext()) {
      String[] imageData = scanner.next().split(",");
      double region_centroid_col = Double.parseDouble(imageData[0]);
      double region_centroid_row = Double.parseDouble(imageData[1]);
      double region_pixel_count = Double.parseDouble(imageData[2]);
      double short_line_density_5 = Double.parseDouble(imageData[3]);
      double short_line_density_2 = Double.parseDouble(imageData[4]);
      double vedge_mean = Double.parseDouble(imageData[5]);
      double vedge_sd = Double.parseDouble(imageData[6]);
      double hedge_mean = Double.parseDouble(imageData[7]);
      double hedge_sd = Double.parseDouble(imageData[8]);
      double intensity_mean = Double.parseDouble(imageData[9]);
      double rawred_mean = Double.parseDouble(imageData[10]);
      double rawblue_mean = Double.parseDouble(imageData[11]);
      double rawgreen_mean = Double.parseDouble(imageData[12]);
      double exred_mean = Double.parseDouble(imageData[13]);
      double exblue_mean = Double.parseDouble(imageData[14]);
      double exgreen_mean = Double.parseDouble(imageData[15]);
      double value_mean = Double.parseDouble(imageData[16]);
      double saturation_mean = Double.parseDouble(imageData[17]);
      double hue_mean = Double.parseDouble(imageData[18]);
      String type = imageData[19];
      images.add(new Image(region_centroid_col, region_centroid_row, region_pixel_count,
              short_line_density_5, short_line_density_2, vedge_mean, vedge_sd, hedge_mean,
              hedge_sd, intensity_mean, rawred_mean, rawblue_mean, rawgreen_mean, exred_mean,
              exblue_mean, exgreen_mean, value_mean, saturation_mean, hue_mean, type));
    }
    ArrayList<Edge> edges = new ArrayList<>();
    for (int one = 0; one < images.size(); one = one + 1) {
      int index = one;
      Image first = images.get(index);
      for (int two = index + 1; two < images.size(); two = two + 1) {
        Image second = images.get(two);
        edges.add(new Edge(first, second, first.distance(second)));
      }
    }
    edges.sort(new EdgeCompare());
    List<Edge> solution = new ArrayList<>();
    for (Edge edge : edges) {
      if (edge.first.find() != edge.second.find()) {
        solution.add(edge);
        edge.union();
      }
    }
    List<Edge> clusterEdges = solution.subList(0, solution.size() - (k - 1));
    Set<Set<Image>> clusters = new HashSet<>();
    for (Image image : images) {
      if (!image.clustered) {
        Set<Image> cluster = new HashSet<>();
        cluster.add(image);
        clusters.add(cluster);
        boolean run = true;
        while (run) {
          run = false;
          for (Edge edge : clusterEdges) {
            if (!edge.first.clustered || !edge.second.clustered) {
              if (cluster.contains(edge.first) || cluster.contains(edge.second)) {
                if (!cluster.contains(edge.first)) {
                  cluster.add(edge.first);
                  edge.first.clustered = true;
                  run = true;
                }
                if (!cluster.contains(edge.second)) {
                  cluster.add(edge.second);
                  edge.second.clustered = true;
                  run = true;
                }
              }
            }
          }
        }
      }
    }
    double correct = 0;
    int count = 1;
    for (Set<Image> cluster : clusters) {
      System.out.println("Cluster " + count + " (size: " + cluster.size() + "):");
      count++;
      int brickface = 0;
      int sky = 0;
      int foliage = 0;
      int cement = 0;
      int window = 0;
      int path = 0;
      int grass = 0;
      int max = 0;
      for (Image image : cluster) {
        System.out.println(image.region_centroid_col + "," + image.region_centroid_row
                + "," + image.region_pixel_count + "," + image.short_line_density_5
                + "," + image.short_line_density_2 + "," + image.vedge_mean + "," + image.vedge_sd
                + "," + image.hedge_mean + "," + image.hedge_sd + "," + image.intensity_mean
                + "," + image.rawred_mean + "," + image.rawblue_mean + "," + image.rawgreen_mean
                + "," + image.exred_mean + "," + image.exblue_mean + "," + image.exgreen_mean
                + "," + image.value_mean + "," + image.saturation_mean + "," + image.hue_mean
                + "," + image.type);
        switch (image.type) {
          case "brickface":
            ++brickface;
            if (brickface > max) {
              max = brickface;
            }
            break;
          case "sky":
            ++sky;
            if (sky > max) {
              max = sky;
            }
            break;
          case "foliage":
            ++foliage;
            if (foliage > max) {
              max = foliage;
            }
            break;
          case "cement":
            ++cement;
            if (cement > max) {
              max = cement;
            }
            break;
          case "window":
            ++window;
            if (window > max) {
              max = window;
            }
            break;
          case "path":
            ++path;
            if (path > max) {
              max = path;
            }
            break;
          case "grass":
            ++grass;
            if (grass > max) {
              max = grass;
            }
            break;
        }
      }
      correct = correct + max;
      System.out.println();
    }
    double purity = correct / images.size();
    System.out.println("Purity: " + purity);
  }
}