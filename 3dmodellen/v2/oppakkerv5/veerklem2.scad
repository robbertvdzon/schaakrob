
difference(){
	union(){


        translate([0,0,0]){
           cube([6,10,2], center=false);
        }        
        translate([0,0,0]){
           cube([6,2,10], center=false);
        }        


        translate([3,0,0]){
            rotate([-45,0,0]){
               cylinder(h=10, r=2, $fn=100, center=false);
            }
        }

        
	}
	union() {
        translate([0,0,-2]){
           cube([6,10,2], center=false);
        }        
        translate([0,-2,0]){
           cube([6,2,10], center=false);
        }        


	}
}
