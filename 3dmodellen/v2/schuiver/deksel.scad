
difference(){
	union(){
        
        translate([-8,3.5,10]){
            cube([15,4,5], center=false);
        }         
        translate([-6.5,7.5,12.5]){
            rotate([90,0,0]){
                cylinder(h=4, r=4, $fn=100, center=false);
            }
        }         
        translate([6.5,7.5,12.5]){
            rotate([90,0,0]){
                cylinder(h=4, r=4, $fn=100, center=false);
            }
        }         


	}
	union() {

        translate([-6.5,8.5,12.5]){
            rotate([90,0,0]){
                cylinder(h=25, r=2, $fn=100, center=false);
            }
        }         
        translate([6.5,8.5,12.5]){
            rotate([90,0,0]){
                cylinder(h=25, r=2, $fn=100, center=false);
            }
        }             


	}
}
