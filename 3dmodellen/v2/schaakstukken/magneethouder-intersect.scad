layer_name = "plate";
dpi_val    = 96;
module common_shape2d() {
  intersection() {
      import(file="svg/dame_dicht.svg", layer=layer_name, dpi=dpi_val, center=true);
      import(file="svg/koning_dicht.svg", layer=layer_name, dpi=dpi_val, center=true);
      import(file="svg/loper_dicht.svg", layer=layer_name, dpi=dpi_val, center=true);
      import(file="svg/paard_dicht.svg", layer=layer_name, dpi=dpi_val, center=true);
      import(file="svg/pion_dicht.svg", layer=layer_name, dpi=dpi_val, center=true);
      import(file="svg/toren_dicht.svg", layer=layer_name, dpi=dpi_val, center=true);
  }
}

linear_extrude(height = 1)  // kies jouw hoogte
  common_shape2d();


  