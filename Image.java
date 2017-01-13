/**
 * Created by marcusshannon on 11/27/15.
 */
public class Image {

  double region_centroid_col;
  double region_centroid_row;
  double region_pixel_count;
  double short_line_density_5;
  double short_line_density_2;
  double vedge_mean;
  double vedge_sd;
  double hedge_mean;
  double hedge_sd;
  double intensity_mean;
  double rawred_mean;
  double rawblue_mean;
  double rawgreen_mean;
  double exred_mean;
  double exblue_mean;
  double exgreen_mean;
  double value_mean;
  double saturation_mean;
  double hue_mean;
  String type;
  Image parent;
  int rank;
  boolean clustered;

  public Image(double region_centroid_col, double region_centroid_row, double region_pixel_count,
               double short_line_density_5, double short_line_density_2, double vedge_mean,
               double vedge_sd, double hedge_mean, double hedge_sd, double intensity_mean,
               double rawred_mean, double rawblue_mean, double rawgreen_mean, double exred_mean,
               double exblue_mean, double exgreen_mean, double value_mean, double saturation_mean,
               double hue_mean, String type) {
    this.region_centroid_col = region_centroid_col;
    this.region_centroid_row = region_centroid_row;
    this.region_pixel_count = region_pixel_count;
    this.short_line_density_5 = short_line_density_5;
    this.short_line_density_2 = short_line_density_2;
    this.vedge_mean = vedge_mean;
    this.vedge_sd = vedge_sd;
    this.hedge_mean = hedge_mean;
    this.hedge_sd = hedge_sd;
    this.intensity_mean = intensity_mean;
    this.rawred_mean = rawred_mean;
    this.rawblue_mean = rawblue_mean;
    this.rawgreen_mean = rawgreen_mean;
    this.exred_mean = exred_mean;
    this.exblue_mean = exblue_mean;
    this.exgreen_mean = exgreen_mean;
    this.value_mean = value_mean;
    this.saturation_mean = saturation_mean;
    this.hue_mean = hue_mean;
    this.type = type;
    this.parent = this;
    this.rank = 0;
    this.clustered = false;
  }

  double distance(Image second) {
    double distance = Math.pow(this.region_centroid_col - second.region_centroid_col, 2);
    distance += Math.pow(this.region_centroid_row - second.region_centroid_row, 2);
    distance += Math.pow(this.region_pixel_count - second.region_pixel_count, 2);
    distance += Math.pow(this.short_line_density_5 - second.short_line_density_5, 2);
    distance += Math.pow(this.short_line_density_2 - second.short_line_density_2, 2);
    distance += Math.pow(this.vedge_mean - second.vedge_mean, 2);
    distance += Math.pow(this.vedge_sd - second.vedge_sd, 2);
    distance += Math.pow(this.hedge_mean - second.hedge_mean, 2);
    distance += Math.pow(this.hedge_sd - second.hedge_sd, 2);
    distance += Math.pow(this.intensity_mean - second.intensity_mean, 2);
    distance += Math.pow(this.rawred_mean - second.rawred_mean, 2);
    distance += Math.pow(this.rawblue_mean - second.rawblue_mean, 2);
    distance += Math.pow(this.rawgreen_mean - second.rawgreen_mean, 2);
    distance += Math.pow(this.exred_mean - second.exred_mean, 2);
    distance += Math.pow(this.exblue_mean - second.exblue_mean, 2);
    distance += Math.pow(this.exgreen_mean - second.exgreen_mean, 2);
    distance += Math.pow(this.value_mean - second.value_mean, 2);
    distance += Math.pow(this.saturation_mean - second.saturation_mean, 2);
    distance += Math.pow(this.hue_mean - second.hue_mean, 2);
    return Math.sqrt(distance);
  }

  Image find() {
    if (this.parent != this) {
      this.parent = this.parent.find();
    }
    return this.parent;
  }
}
