
$dikte = 1;
$extraHoogte = 10;

difference(){
	union(){
        translate([-2-2-$dikte,-23+5-$dikte,0]){
          roundedcube([77+4+$dikte*2, 56+$dikte*2, 50+$dikte+$extraHoogte], false, 6, "zmax");
        }
       
        // assen: blok er onder

        translate([9.5+75/2,50,17.5]){
            rotate([90,0,0]){
//                cylinder(h=200, r=5, $fn=100, center=false);
            }
        }        
        translate([-10,-10+5,6.5]){
            rotate([0,90,0]){
  //             cylinder(h=200, r=5, $fn=100, center=false);
            }
        }  

        
	}
	union() {
        translate([-2-2,-23+5,-0.01]){
          roundedcube([77+4, 56, 50+$extraHoogte], false, 6, "zmax");
//          roundedcube([77, 56, 50], false, 6, "zmax");
//           cube([77,56,50]);
        }
        // gewone pakker
        translate([0,0,-0.01]){
           //include <oppakker.scad>;                
        }  
        // pakker + 7mm ruimte bovenin
        translate([0,0,7]){
           //include <oppakker.scad>;                
        }  


        
        // assen
        translate([-9.5+75/2,50,17.5]){
            rotate([90,0,0]){
                cylinder(h=200, r=6, $fn=100, center=false);
            }
            translate([-6,-115,-20]){
             cube([12,110,20]);
            }
        }
        translate([9.5+75/2,50,17.5]){
            rotate([90,0,0]){
                cylinder(h=200, r=6, $fn=100, center=false);
            }
            translate([-6,-115,-20]){
             cube([12,110,20]);
            }
        }        
        translate([-10,-10+5,6.5]){
            rotate([0,90,0]){
               cylinder(h=200, r=6, $fn=100, center=false);
            }
            translate([-5,-6,-20]){
             cube([110,12,20]);
            }
        }  


        
        // boorgaten voor deksel        
        translate([75/2,130,40]){
            rotate([90,0,0]){
                cylinder(h=120, r=1.2, $fn=100, center=false);
            }
        }               
        translate([75/2-20,-0,5]){
            rotate([90,0,0]){
                cylinder(h=20, r=1.2, $fn=100, center=false);
            }
        }               
        translate([75/2+20,-0,5]){
            rotate([90,0,0]){
                cylinder(h=20, r=1.2, $fn=100, center=false);
            }
        }  

	}
}



$fs = 0.15;

module roundedcube(size = [1, 1, 1], center = false, radius = 0.5, apply_to = "all") {
	// If single value, convert to [x, y, z] vector
	size = (size[0] == undef) ? [size, size, size] : size;

	translate_min = radius;
	translate_xmax = size[0] - radius;
	translate_ymax = size[1] - radius;
	translate_zmax = size[2] - radius;

	diameter = radius * 2;

	obj_translate = (center == false) ?
		[0, 0, 0] : [
			-(size[0] / 2),
			-(size[1] / 2),
			-(size[2] / 2)
		];

	translate(v = obj_translate) {
		hull() {
			for (translate_x = [translate_min, translate_xmax]) {
				x_at = (translate_x == translate_min) ? "min" : "max";
				for (translate_y = [translate_min, translate_ymax]) {
					y_at = (translate_y == translate_min) ? "min" : "max";
					for (translate_z = [translate_min, translate_zmax]) {
						z_at = (translate_z == translate_min) ? "min" : "max";

						translate(v = [translate_x, translate_y, translate_z])
						if (
							(apply_to == "all") ||
							(apply_to == "xmin" && x_at == "min") || (apply_to == "xmax" && x_at == "max") ||
							(apply_to == "ymin" && y_at == "min") || (apply_to == "ymax" && y_at == "max") ||
							(apply_to == "zmin" && z_at == "min") || (apply_to == "zmax" && z_at == "max")
						) {
							sphere(r = radius);
						} else {
							rotate = 
								(apply_to == "xmin" || apply_to == "xmax" || apply_to == "x") ? [0, 90, 0] : (
								(apply_to == "ymin" || apply_to == "ymax" || apply_to == "y") ? [90, 90, 0] :
								[0, 0, 0]
							);
							rotate(a = rotate)
							cylinder(h = diameter, r = radius, center = true, $fn=100);
						}
					}
				}
			}
		}
	}
}
