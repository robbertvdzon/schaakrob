

difference(){
	union(){
        translate([0,0,0]){
            rotate([0,0,0]){
                cube([14,13,6], center=false);
            }
        }        

        translate([20,0,0]){
            rotate([0,0,0]){
                cube([14,13,1], center=false);
            }
        }        

        translate([20+20,0,0]){
            rotate([0,0,0]){
                cube([23,13,1], center=false);
            }
        }        

      translate([20+15/2,13/2,0]){
            rotate([0,0,90]){
                cylinder(h=4, r=3, $fn=100, center=false);
            }
        }   


      translate([20+20+20,13/2,0]){
            rotate([0,0,90]){
                cylinder(h=4, r=3, $fn=100, center=false);
            }
        }   



        
	}
	union() {
        translate([1,1,1]){
            rotate([0,0,0]){
                cube([13.1,11,5.1], center=false);
            }
        }        

   
	}
}

