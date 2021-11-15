
difference(){
	union(){



        translate([5,-2-25,0]){
           cube([42,12+25,4], center=false);
        }        
        translate([-10,8,0]){
           cube([72,4,12], center=false);
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

        translate([-5,20,7]){
            rotate([90,0,0]){
               cylinder(h=999, r=2, $fn=100, center=false);
            }
        }
        translate([57,20,7]){
            rotate([90,0,0]){
               cylinder(h=999, r=2, $fn=100, center=false);
            }
        }

        translate([15,-7-20-10,-1]){
           cube([52-30,17+20,6], center=false);
        }  


	}
}
