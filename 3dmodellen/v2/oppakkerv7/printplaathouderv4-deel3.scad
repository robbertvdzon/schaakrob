

difference(){
	union(){          

        translate([-16.5,-30,10]){
              cube([33,48,3], center=false);
        }


        translate([-16.5,11,13]){
            rotate([0,90,0]){
                cylinder(h=33, r=3, $fn=100, center=false);
            }
        }           


        translate([9,-30+2.5+7,13]){
            rotate([0,0,90]){
                cylinder(h=2, r1=3.5, r2=1.5, $fn=100, center=false);
            }
        }           
        translate([-9,-30+2.5+7,13]){
            rotate([0,0,90]){
                cylinder(h=2, r1=3.5, r2=1.5, $fn=100, center=false);
            }
        }           
   

	}
	union() {

        translate([0,0,-2]){
            rotate([0,0,0]){
                cylinder(h=200, r=1.5, $fn=100, center=false);
            }
        }           
        translate([0,-10,-2]){
            rotate([0,0,0]){
                cylinder(h=200, r=1.5, $fn=100, center=false);
            }
        }           
        translate([0,-20,-2]){
            rotate([0,0,0]){
                cylinder(h=200, r=1.5, $fn=100, center=false);
            }
        }           


        translate([9.2,-30+2.5+7,-2]){
            rotate([0,0,0]){
                cylinder(h=200, r=1.5, $fn=100, center=false);
            }
        }           
        translate([-9.2,-30+2.5+7,-2]){
            rotate([0,0,0]){
                cylinder(h=200, r=1.5, $fn=100, center=false);
            }
        }           


        
        
	}
}
