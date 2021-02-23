
difference(){
	union(){
        translate([49/2,27/2,20]){
            rotate([0,0,0]){
                cylinder(h=2, r=13.5, $fn=100, center=false);
            }
        }
        
        translate([18,(27-6)/2,22]){
           cube([3,6,6], center=false);
        }        
        translate([24,(27-6)/2,22]){
           cube([3,6,6], center=false);
        }        

        
	}
	union() {
        translate([49/2,27/2,-1]){
            rotate([0,0,0]){
                translate([9.5,0,0]){
                    cylinder(h=500, r=1.5, $fn=100, center=false);
                }
            }
            rotate([0,0,120]){
                translate([9.5,0,0]){
                    cylinder(h=500, r=1.5, $fn=100, center=false);
                }
            }
            rotate([0,0,240]){
                translate([9.5,0,0]){
                    cylinder(h=500, r=1.5, $fn=100, center=false);
                }
            }
        }

        translate([17,27/2,25]){
            rotate([0,90,0]){
                cylinder(h=200, r=1.2, $fn=100, center=false);
            }
        }


	}
}
