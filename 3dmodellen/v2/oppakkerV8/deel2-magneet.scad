
difference(){
	union(){
      translate([-30/2,-36/2,0]){
            cube([30,36,3], center=false);
      }
      translate([-30/2+2.5,-36/2,0]){
            cube([25,3,28], center=false);
      }
      translate([-30/2+2.5,36/2-3,0]){
            cube([10,3,28], center=false);
      }
      translate([-30/2+15,36/2-3,0]){
            cube([10,3,28], center=false);
      }


	}
	union() {

        translate([0,0,-1]){
            rotate([0,0,0-90]){
                translate([11.5,0,0]){
                    cylinder(h=500, r=1.7, $fn=100, center=false);
                }
            }
            rotate([0,0,120-90]){
                translate([11.5,0,0]){
                    cylinder(h=500, r=1.7, $fn=100, center=false);
                }
            }
            rotate([0,0,240-90]){
                translate([11.5,0,0]){
                    cylinder(h=500, r=1.7, $fn=100, center=false);
                }
            }
        } 



    

      
	}
}
