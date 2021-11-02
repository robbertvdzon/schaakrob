
difference(){
	union(){



        translate([0,0,0]){
           cube([30,18,2], center=false);
        }        
        translate([0,0,0]){
           cube([30,2,15], center=false);
        }        




        
	}
	union() {
        translate([15,14,-1]){
            rotate([0,0,0]){
               cylinder(h=999, r=2, $fn=100, center=false);
            }
        }

	}
}
