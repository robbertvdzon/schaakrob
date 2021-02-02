mirror(v= [0,180,0] ) {
    
difference(){
	union(){
        translate([11,-14,48+8]){
            rotate([-90,0,0]){
       //         include <motorvorm.scad>;
            }
        }
        
        
        translate([-4,0,-23]){
           cube([44,45,5], center=false);
        }
        translate([0-4,0,-18]){
           cube([5,70,75], center=false);
        }
        translate([0-4,10,-18+15+11]){
           cube([15,5,40], center=false);
        }

        translate([-4,0,-33]){
           cube([5,10,10], center=false);
        }

        translate([0,45,-16]){
           cube([45,5,55], center=false);
        }
        translate([0,45,-2]){
           cube([68,5,59], center=false);
        }
        translate([-4,45,-23]){
           cube([55+4,25,8], center=false);
        }




        translate([40+8+3,58,-18]){
            rotate([0,0,90]){
                cylinder(h=8, r=6, $fn=100, center=true);
            }
        }
        translate([40,47.5,-6]){
            rotate([90,0,0]){
                cylinder(h=5, r=6, $fn=100, center=true);
            }
        }
        translate([47/2+5+8+3,47.5,20]){
            rotate([90,0,0]){
                cylinder(h=5, r=12, $fn=100, center=true);
            }
        }
                
 
	}
	union() {

        translate([20,48,-6]){
            rotate([90,0,0]){
                cylinder(h=10, r=6, $fn=100, center=true);
            }
        }
        translate([18+8+3,58,-16]){
            rotate([0,0,90]){
                cylinder(h=15, r=7, $fn=100, center=true);
            }
        }

        translate([5,44,-12]){
           cube([15,10,12], center=false);
        }

        translate([10,51,-24]){
           cube([20,14,16], center=false);
        }

        translate([40,48,-6]){
            rotate([90,0,0]){
                cylinder(h=10, r=4, $fn=100, center=true);
            }
        }
        translate([51,48,-19]){
            rotate([90,0,0]){
                cylinder(h=100, r=1.2, $fn=100, center=true);
            }
        }
        translate([40+8+3,58,-15]){
            rotate([0,0,90]){
                cylinder(h=20, r=4, $fn=100, center=true);
            }
        }
        translate([40,53,-15]){
            rotate([0,0,90]){
                cylinder(h=20, r=1.2, $fn=100, center=true);
            }
        }
        translate([47/2+5+8+3,57-9,20+8]){
            rotate([90,0,0]){
                cylinder(h=8, r=22, $fn=100, center=true);
            }
        }

//-
        translate([39.5-47/2,57,20+8+47/2]){
            rotate([90,0,0]){
                cylinder(h=50, r=2.5, $fn=100, center=true);
            }
        }
        translate([39.5-47/2,57,20+8-47/2]){
            rotate([90,0,0]){
                cylinder(h=50, r=2.5, $fn=100, center=true);
            }
        }
        translate([39.5+47/2,57,20+8+47/2]){
            rotate([90,0,0]){
                cylinder(h=50, r=2.5, $fn=100, center=true);
            }
        }
        translate([39.5+47/2,57,20+8-47/2]){
            rotate([90,0,0]){
                cylinder(h=50, r=2.5, $fn=100, center=true);
            }
        }
        

        translate([-4,5,-33+5]){
            rotate([0,90,0]){
                cylinder(h=200, r=1.5, $fn=100, center=true);
            }
        }
        translate([0,65,-5]){
            rotate([0,90,0]){
                cylinder(h=200, r=1.5, $fn=100, center=true);
            }
        }
        translate([0,5,52]){
            rotate([0,90,0]){
                cylinder(h=200, r=1.5, $fn=100, center=true);
            }
        }
        translate([0,65,52]){
            rotate([0,90,0]){
                cylinder(h=200, r=1.5, $fn=100, center=true);
            }
        }
        translate([55+10,52,20+8]){
            rotate([0,90,0]){
                cylinder(h=20, r=4, $fn=100, center=true);
            }
        }

        translate([47/2+5,42/2,4]){
            rotate([0,0,90]){
                cylinder(h=100, r=7.5, $fn=100, center=true);
            }
        }

	}
}
}