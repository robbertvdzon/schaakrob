difference(){
	union(){
        
        translate([-8,4.5,10]){
            cube([15,3,5], center=false);
        }         
        translate([-10,4.5,10]){
            cube([10,3,18], center=false);
        }         
        translate([-25,4.5,0+20]){
            cube([15,3,8], center=false);
        }  

        
        
        


        translate([-6.5,7.5,12.5]){
            rotate([90,0,0]){
                cylinder(h=3, r=4, $fn=100, center=false);
            }
        }         
        translate([6.5,7.5,12.5]){
            rotate([90,0,0]){
                cylinder(h=3, r=4, $fn=100, center=false);
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
