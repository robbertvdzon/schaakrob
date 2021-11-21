
difference(){
	union(){


        translate([0,0,0]){
           cube([30,90,3], center=false);
        }        

        
	}
	union() {
        translate([15,70,-1]){
            rotate([0,0,0]){
               cylinder(h=10, r=10, $fn=100, center=false);
            }
        }

        translate([5.5,15,-1]){
           cube([19,30,5], center=false);
        }        
        translate([15,10,-1]){
            rotate([0,0,0]){
               cylinder(h=10, r=1.5, $fn=100, center=false);
            }
        }
        translate([15,50,-1]){
            rotate([0,0,0]){
               cylinder(h=10, r=1.5, $fn=100, center=false);
            }
        }


        translate([3,3,-1]){
            rotate([0,0,0]){
               cylinder(h=10, r=1.5, $fn=100, center=false);
            }
        }
        translate([27,3,-1]){
            rotate([0,0,0]){
               cylinder(h=10, r=1.5, $fn=100, center=false);
            }
        }
        translate([3,87,-1]){
            rotate([0,0,0]){
               cylinder(h=10, r=1.5, $fn=100, center=false);
            }
        }
        translate([27,87,-1]){
            rotate([0,0,0]){
               cylinder(h=10, r=1.5, $fn=100, center=false);
            }
        }

	}
}
