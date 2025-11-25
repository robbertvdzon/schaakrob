
difference(){
	union(){

        translate([0,0,0]){
            cube([8,11,4], center=false);
        }         

        translate([0,0,0]){
            cube([8,2,35], center=false);
        }         
     

	}
	union() {
        translate([4,8,-10]){
            rotate([0,0,0]){
                cylinder(h=100, r=1.5, $fn=100, center=false);
            }
        }
      
	}
}
