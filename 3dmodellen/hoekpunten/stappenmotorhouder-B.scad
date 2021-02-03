mirror(v= [0,0,0] ) {
    
difference(){
	union(){
        
        translate([-4,0,-23]){
           cube([44,45,5], center=false);
        }
        translate([0-4,0,-18]){
           cube([5,70,63], center=false);
        }
        translate([-4,0,-37]){
           cube([5,14,14], center=false);
        }

        translate([0,45,-16]){
           cube([45+5,5,50.5], center=false);
        }
        translate([-4,45,-23]){
           cube([55+4,25,8], center=false);
        }

        translate([47.5,47.5,22]){
            rotate([90,0,0]){
                cylinder(h=5, r=12.5, $fn=100, center=true);
            }
        }



        translate([0,45,-16+25]){
           cube([20,25,5], center=false);
        }
        translate([0,45,-16+25+20.5]){
           cube([20,25,5], center=false);
        }

        translate([0,45+20,-16+25]){
           cube([45+5,5,50.5-25], center=false);
        }
        translate([47.5,47.5+20,22]){
            rotate([90,0,0]){
                cylinder(h=5, r=12.5, $fn=100, center=true);
            }
        }

        translate([40+8+3,58,-18-1]){
            rotate([0,0,90]){
                cylinder(h=8, r=6, $fn=100, center=true);
            }
        }

 
	}
	union() {
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
        translate([47/2+5+8+3+8,57-9,20+8-6]){
            rotate([90,0,0]){
                cylinder(h=8, r=7.5, $fn=100, center=true);
            }
        }
        translate([47/2+5+8+3+8,57-9+20,20+8-6]){
            rotate([90,0,0]){
                cylinder(h=8, r=7.5, $fn=100, center=true);
            }
        }
        

        translate([-4,5+2,-33+5-2]){
            rotate([0,90,0]){
                cylinder(h=200, r=3, $fn=100, center=true);
            }
        }
        translate([0,62,40]){
            rotate([0,90,0]){
                cylinder(h=200, r=3, $fn=100, center=true);
            }
        }
        translate([0,7,40]){
            rotate([0,90,0]){
                cylinder(h=200, r=3, $fn=100, center=true);
            }
        }
        translate([0,62,42]){
            rotate([0,90,0]){
         //       cylinder(h=200, r=3, $fn=100, center=true);
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