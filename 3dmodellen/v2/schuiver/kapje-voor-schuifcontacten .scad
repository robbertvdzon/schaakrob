
difference(){
	union(){
        
        translate([-25,4.5,0]){
            cube([15,3,30], center=false);
        }         


	}
	union() {

        translate([-17.5,8.5,5]){
            rotate([90,0,0]){
                cylinder(h=25, r=1.5, $fn=100, center=false);
            }
        }       
        translate([-17.5,8.5,15]){
            rotate([90,0,0]){
                cylinder(h=25, r=1.5, $fn=100, center=false);
            }
        }       
        translate([-17.5,8.5,25]){
            rotate([90,0,0]){
                cylinder(h=25, r=1.5, $fn=100, center=false);
            }
        }     
	}
}
