// === parameters ===
svg_file = "svg/paard_dicht.svg";   // jouw bestand
svg_layer = "plate";                 // jouw layernaam (of weglaten als niet nodig)

// Je had een mirror + translate; die laten we staan.
// NB: in OpenSCAD is een SVG standaard in "SVG units" (meestal px).
// Als het formaat nog niet precies 40x50 mm is, zie schaalstukje verderop.

difference() {
  union() {
    // 2D import (géén extrude)
    mirror([1,0,0])
    translate([-27.5, -2])      // z-vertaling weg; 2D = alleen x,y
      import(file = svg_file, layer = svg_layer);  // levert 2D paths op
  }
}