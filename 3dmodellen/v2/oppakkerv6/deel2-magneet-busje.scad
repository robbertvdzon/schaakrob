
difference(){
	union(){


        translate([0,0,0]){
                rotate([0,0,0]){
                    cylinder(h=4, r=4, $fn=100, center=false);
                }

        }     

	}
	union() {

        translate([0,0,-1]){
                rotate([0,0,0]){
                    cylinder(h=40, r=1.5, $fn=100, center=false);
                }
            }  


      
	}
}
