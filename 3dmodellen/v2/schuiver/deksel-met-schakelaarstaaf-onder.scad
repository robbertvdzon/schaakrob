
difference(){
	union(){
        
        translate([-8,4.5,10]){
            cube([15,3,5], center=false);
        }         
        translate([-20,4.5,20]){
            cube([10,3,5], center=false);
        }         
        translate([-10,4.5,10]){
            cube([5,3,15], center=false);
        }         


        translate([-7.5,7.5,12.5]){
            rotate([90,0,0]){
                cylinder(h=3, r=4, $fn=100, center=false);
            }
        }         
        translate([7.5,7.5,12.5]){
            rotate([90,0,0]){
                cylinder(h=3, r=4, $fn=100, center=false);
            }
        }         

	}
	union() {

        translate([-7.5,8.5,12.5]){
            rotate([90,0,0]){
                cylinder(h=25, r=2, $fn=100, center=false);
            }
        }         
        translate([7.5,8.5,12.5]){
            rotate([90,0,0]){
                cylinder(h=25, r=2, $fn=100, center=false);
            }
        }             

     

	}
}
