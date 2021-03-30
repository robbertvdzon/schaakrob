
difference(){
	union(){
        
        translate([-8,4.5,10]){
            cube([15,3,5], center=false);
        }         
        translate([-25,4.5,0]){
            cube([15,3,15], center=false);
        }         

        translate([-25,4.5,10]){
            cube([15,3,21], center=false);
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

        translate([0,9.5+12,11.5]){
            rotate([0,0,90]){
             //   cylinder(h=25, r=4, $fn=100, center=false);
            }
        }         
        translate([-23+4,9.5-6,25]){
            rotate([0,90,45]){
                cylinder(h=21, r=6, $fn=100, center=false);
            }
        }         

	}
	union() {
        translate([-30,-10,10]){
            cube([30,14.5,21], center=false);
        }   

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

        translate([-33+5,9.5-16,25]){
            rotate([0,90,45]){
                cylinder(h=99, r=4, $fn=100, center=false);
            }
        }         


        translate([-17.5,8.5,5]){
            rotate([90,0,0]){
                cylinder(h=25, r=1.2, $fn=100, center=false);
            }
        }       
        translate([-17.5,8.5,15]){
            rotate([90,0,0]){
                cylinder(h=25, r=1.2, $fn=100, center=false);
            }
        }       

	}
}
