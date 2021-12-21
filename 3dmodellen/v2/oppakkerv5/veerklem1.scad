
difference(){
	union(){


        translate([0,0,0]){
           cube([6,15,5], center=false);
        }        


        translate([3,3,0]){
            rotate([0,0,0]){
               cylinder(h=10, r=2, $fn=100, center=false);
            }
        }

        
	}
	union() {


	}
}
