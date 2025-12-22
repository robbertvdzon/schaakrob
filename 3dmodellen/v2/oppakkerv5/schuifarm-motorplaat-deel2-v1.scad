
difference(){
	union(){
        translate([0,0,0]){
            rotate([0,0,0]){
                cylinder(h=5, r=6, $fn=100, center=false);
            }
        }
    }        
	union() {
          translate([-10,-10,-10]){
            cube([20,10,20], center=false);
        }         
	}
}
