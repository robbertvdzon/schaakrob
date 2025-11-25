
difference(){
	union(){
        
        translate([-8,3.5,10]){
            cube([15,4,5], center=false);
        }         
        translate([-20,3.5,20]){
            cube([10,4,5], center=false);
        }         
        translate([-10,3.5,10]){
            cube([5,4,15], center=false);
        }         


        translate([-7.5,7.5,12.5]){
            rotate([90,0,0]){
                cylinder(h=4, r=5, $fn=100, center=false);
            }
        }         
        translate([7.5,7.5,12.5]){
            rotate([90,0,0]){
                cylinder(h=4, r=5, $fn=100, center=false);
            }
        }         

	}
	union() {

        translate([-7.5,8.5,12.5]){
            rotate([90,0,0]){
                cylinder(h=25, r=3, $fn=100, center=false);
            }
        }         
        translate([7.5,8.5,12.5]){
            rotate([90,0,0]){
                cylinder(h=25, r=3, $fn=100, center=false);
            }
        }             

     

	}
}
