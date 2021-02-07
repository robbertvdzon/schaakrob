mirror(v= [0,0,0] ) {
    
difference(){
	union(){
        translate([0.25,44.25,65]){
            rotate([180,0,0]){
         //       include <motorvorm.scad>;
            }
        }
        
        
        translate([-4,-13,-23+25]){
           cube([61,45+13,5], center=false);
        }
        translate([0-4,-13,-18-5]){
           cube([4.25,83,80], center=false);
        }

        translate([0,45,-16]){
           cube([48,5,55], center=false);
        }


        translate([47/2+5+8+3+8,57-9-0.5,20+8]){
            rotate([90,0,0]){
                cylinder(h=5, r=10, $fn=100, center=true);
            }
        }

        translate([-4,45,-23]){
           cube([55,25,8], center=false);
        }




        translate([40+8+3,48,-18-1]){
            rotate([0,0,90]){
                cylinder(h=8, r=7, $fn=100, center=true);
            }
        }


      
 
	}
	union() {
        translate([0-5,-3,-18-5+35]){
           cube([6,40,40], center=false);
        }

       translate([50,47.5,-6]){
            rotate([0,90,0]){
                cylinder(h=20, r=1.2, $fn=100, center=true);
            }
        }

        translate([20,48,-6]){
            rotate([90,0,0]){
                cylinder(h=10, r=6, $fn=100, center=true);
            }
        }
        translate([18+8+3+8,58,-16]){
            rotate([0,0,90]){
                cylinder(h=15, r=7, $fn=100, center=true);
            }
        }

        translate([5,44,-12]){
           cube([15,10,12], center=false);
        }

        translate([10,51,-24]){
           cube([28,14,16], center=false);
        }

        translate([40,48,-6]){
            rotate([90,0,0]){
                cylinder(h=10, r=4, $fn=100, center=true);
            }
        }
        translate([51,-5,-19]){
            rotate([90,0,0]){
                cylinder(h=100, r=1.2, $fn=100, center=true);
            }
        }
        translate([40+8+3,48,-20]){
            rotate([0,0,90]){
                cylinder(h=20, r=4, $fn=100, center=true);
            }
        }
        translate([47/2+5+8+3+8,57-9,20+8]){
            rotate([90,0,0]){
                cylinder(h=8, r=7.5, $fn=100, center=true);
            }
        }
        translate([-4,-5,-15]){
            rotate([0,90,0]){
                cylinder(h=200, r=3, $fn=100, center=true);
            }
        }
        translate([0,62,+5]){
            rotate([0,90,0]){
                cylinder(h=200, r=3, $fn=100, center=true);
            }
        }
        translate([0,62,42]){
            rotate([0,90,0]){
                cylinder(h=200, r=3, $fn=100, center=true);
            }
        }

        translate([57/2,32/2,-47+25]){
            rotate([0,0,90]){
                cylinder(h=60, r=22, $fn=100, center=true);
            }
        }
        
//
        translate([57/2-47/2,32/2-47/2,4]){
            rotate([0,0,90]){
                cylinder(h=100, r=2.5, $fn=100, center=true);
            }
        }
        translate([57/2-47/2,32/2+47/2,4]){
            rotate([0,0,90]){
                cylinder(h=100, r=2.5, $fn=100, center=true);
            }
        }
        translate([57/2+47/2,32/2-47/2,4]){
            rotate([0,0,90]){
                cylinder(h=100, r=2.5, $fn=100, center=true);
            }
        }
        translate([57/2+47/2,32/2+47/2,44]){
            rotate([0,0,90]){
                cylinder(h=100, r=2.5, $fn=100, center=true);
            }
        }
        

	}
}
}