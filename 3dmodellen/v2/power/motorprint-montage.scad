
difference(){
	union(){



        translate([0,-2,0]){
           cube([52,12,2], center=false);
        }        
        translate([0,10,0]){
           cube([52,2,12], center=false);
        }        
             




        
	}
	union() {
        translate([10,2,-1]){
            rotate([0,0,0]){
               cylinder(h=999, r=1.5, $fn=100, center=false);
            }
        }
        translate([42,2,-1]){
            rotate([0,0,0]){
               cylinder(h=999, r=1.5, $fn=100, center=false);
            }
        }

        translate([5,20,7]){
            rotate([90,0,0]){
               cylinder(h=999, r=2, $fn=100, center=false);
            }
        }
        translate([47,20,7]){
            rotate([90,0,0]){
               cylinder(h=999, r=2, $fn=100, center=false);
            }
        }



	}
}
