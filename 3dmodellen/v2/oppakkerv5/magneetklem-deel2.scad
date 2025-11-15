
difference(){
	union(){

        translate([0,0,0]){
            rotate([0,0,0]){
                cylinder(h=12, r=6.5, $fn=100, center=false);
            }
        }            

	}
	union() {

        translate([0,0,-10]){
            rotate([0,0,0]){
                cylinder(h=200, r=2, $fn=100, center=false);
            }
        }            
        translate([0,0,3]){
            rotate([0,0,0]){
                cylinder(h=200, r=3, $fn=100, center=false);
            }
        }            


	}
}
