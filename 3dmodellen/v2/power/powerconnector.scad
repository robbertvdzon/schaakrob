
difference(){
	union(){


        translate([0,-4,0]){
           cube([26.5,44,2], center=false);
        }        

        
	}
	union() {
        translate([26.5/2,36/2,-1]){
            rotate([0,0,0]){
               cylinder(h=10, r=12, $fn=100, center=false);
            }
        }
        translate([5,36-5,0-1]){
            rotate([0,0,0]){
               cylinder(h=10, r=1.5, $fn=100, center=false);
            }
        }
        translate([26.5-5,5,-1]){
            rotate([0,0,0]){
               cylinder(h=10, r=1.5, $fn=100, center=false);
            }
        }


	}
}
