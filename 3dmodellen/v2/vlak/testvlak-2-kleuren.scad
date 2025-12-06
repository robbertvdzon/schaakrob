
plate_w = 60;
plate_h = 40;
plate_t = 3;

circle_d = 20;
circle_t = 3; // zelfde dikte als plaat

// Basisplaat
translate([0,0,0])
    cube([plate_w, plate_h, plate_t], center=false);

// Rondje (apart object)
translate([plate_w/2 - circle_d/2, plate_h/2 - circle_d/2, 0])
    cylinder(h=circle_t, d=circle_d, center=false, $fn=96);
