
difference(){
	union(){
        

        translate([0,0,0]){
            rotate([90,0,0]){
                cylinder(h=10, r=7, $fn=100, center=false);
            }
        }       

        translate([-4,-10,0]){
            cube([2,10,15], center=false);
        }         
        translate([2,-10,0]){
            cube([2,10,15], center=false);
        }         


	}
	union() {
        translate([-10,-5,10]){
            rotate([0,90,0]){
                cylinder(h=25, r=1.2, $fn=100, center=false);
            }
        }       


        translate([-2,-12,0]){
            cube([4,30,30], center=false);
        }         

        translate([0,1,0]){
            rotate([90,0,0]){
                cylinder(h=25, r=5.3, $fn=100, center=false);
            }
        }       

	}
}
