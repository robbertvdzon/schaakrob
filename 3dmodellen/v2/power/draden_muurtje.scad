
difference(){
	union(){



        translate([15,1,2]){
           cube([50,7,4], center=false);
        }        
        translate([0,8,0]){
           cube([80,3,12], center=false);
        }        
             
        
	}
	union() {

        translate([5,20,7]){
            rotate([90,0,0]){
               cylinder(h=999, r=2, $fn=100, center=false);
            }
        }
        translate([75,20,7]){
            rotate([90,0,0]){
               cylinder(h=999, r=2, $fn=100, center=false);
            }
        }
     


	}
}
