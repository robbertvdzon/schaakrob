

difference(){
	union(){


        translate([0,1.5,0]){
            rotate([0,0,90]){
                cylinder(h=3, r=3, $fn=100, center=false);
            }
        }
        translate([8,1.5,0]){
            rotate([0,0,90]){
                cylinder(h=3, r=3, $fn=100, center=false);
            }
        }
        translate([16,1.5,0]){
            rotate([0,0,90]){
                cylinder(h=3, r=3, $fn=100, center=false);
            }
        }

        translate([0,0,0]){
           cube([16,3,3], center=false);
        }
        
	}
	union() {

        translate([0,1.5,-1]){
            rotate([0,0,90]){
                cylinder(h=5, r=1.5, $fn=100, center=false);
            }
        }
        translate([8,1.5,-1]){
            rotate([0,0,90]){
                cylinder(h=5, r=1.5, $fn=100, center=false);
            }
        }
        translate([16,1.5,-1]){
            rotate([0,0,90]){
                cylinder(h=5, r=1.5, $fn=100, center=false);
            }
        }

        


	}
}
