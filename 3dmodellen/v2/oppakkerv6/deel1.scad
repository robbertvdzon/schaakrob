
difference(){
	union(){

        translate([-4,-4,0]){
            cube([25,24,52], center=false);
        }         


        translate([-4,-4,0]){
            cube([38+4+21+25,24,5], center=false);
        }         

        translate([-4+38+25,-4,0]){
            cube([25,24,52], center=false);
        }         


	}
	union() {
        translate([0,0,-1]){
            cube([16,16,54], center=false);
        }         
        translate([0+38+16+10,0,-1]){
            cube([16,16,54], center=false);
        }         

        translate([-30,8,45]){
            rotate([0,90,0]){
                cylinder(h=200, r=1.2, $fn=100, center=false);
            }
        }
        translate([-30,8,35]){
            rotate([0,90,0]){
                cylinder(h=200, r=1.2, $fn=100, center=false);
            }
        }


        translate([(80)/2,-4+12,-1]){
            rotate([0,0,0]){
                cylinder(h=52, r=1.5, $fn=100, center=false);
            }
        }
        translate([(80)/2-10,-4+12,-1]){
            rotate([0,0,0]){
                cylinder(h=52, r=1.5, $fn=100, center=false);
            }
        }
        translate([(80)/2+10,-4+12,-1]){
            rotate([0,0,0]){
                cylinder(h=52, r=1.5, $fn=100, center=false);
            }
        }


        translate([8,40,50]){
            rotate([90,0,0]){
                cylinder(h=100, r=4, $fn=100, center=false);
            }
        }
        translate([72,40,50]){
            rotate([90,0,0]){
//                cylinder(h=100, r=4, $fn=100, center=false);
            }
        }




      
	}
}
