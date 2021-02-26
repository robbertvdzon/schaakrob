mirror(v= [0,0,0] ) {
    
difference(){
	union(){
        translate([43/2,39,40.5]){
            rotate([-90,0,0]){
         //     include <motorvorm.scad>;
            }
        }
        translate([40,94,51.5]){
            rotate([0,90,0]){
           //     include <motorvorm.scad>;
            }
        }
        
        
        // vloer
        translate([0,0,0]){
           cube([74,116,4], center=false);
        }

        // muren voor horizontale motor
        translate([0,39,0]){
           cube([43,4,62], center=false);
        }
        translate([43,0,0]){
           cube([4,43,62], center=false);
        }
        translate([0,0,0]){
           cube([4,43,15], center=false);
        }

        // muren voor verticale motor
        translate([39,116-43,0]){
           cube([4,43,73], center=false);
        }
        translate([0,116-4-43,0]){
           cube([43,4,70], center=false);
        }
        translate([0,116-4,0]){
           cube([40,4,25], center=false);
        }

        // muren voor horizontale as
        translate([43,39,0]){
           cube([70-43,4,62], center=false);
        }
        translate([70,39,0]){
           cube([4,26,50], center=false);
        }

        // schroefgat assen
        translate([58,41,55]){
            rotate([0,90,0]){
               cylinder(h=12, r=4, $fn=20, center=false);
            }
        }
        translate([72,51,44]){
            rotate([90,0,0]){
               cylinder(h=12, r=4, $fn=20, center=false);
            }
        }
      
	}
	union() {

        // gaten voor verticale motor
        translate([35,94,51.5]){
            rotate([0,90,0]){
               cylinder(h=10, r=25/2, $fn=20, center=false);
            }
        }
        translate([35,94+31/2,51.5+31/2]){
            rotate([0,90,0]){
               cylinder(h=10, r=1.5, $fn=20, center=false);
            }
        }
        translate([35,94-31/2,51.5+31/2]){
            rotate([0,90,0]){
               cylinder(h=10, r=1.5, $fn=20, center=false);
            }
        }
        translate([35,94+31/2,51.51-31/2]){
            rotate([0,90,0]){
               cylinder(h=10, r=1.5, $fn=20, center=false);
            }
        }
        translate([35,94-31/2,51.5-31/2]){
            rotate([0,90,0]){
               cylinder(h=10, r=1.5, $fn=20, center=false);
            }
        }        
        
        // gaten voor horizontale motor
        translate([43/2,39-5,40.5]){
            rotate([-90,0,0]){
               cylinder(h=10, r=25/2, $fn=20, center=false);
            }
        }
        translate([43/2+31/2,39-5,40.5+31/2]){
            rotate([-90,0,0]){
               cylinder(h=10, r=1.5, $fn=20, center=false);
            }
        }
        translate([43/2-31/2,39-5,40.5+31/2]){
            rotate([-90,0,0]){
               cylinder(h=10, r=1.5, $fn=20, center=false);
            }
        }
        translate([43/2+31/2,39-5,40.5-31/2]){
            rotate([-90,0,0]){
               cylinder(h=10, r=1.5, $fn=20, center=false);
            }
        }
        translate([43/2-31/2,39-5,40.5-31/2]){
            rotate([-90,0,0]){
               cylinder(h=10, r=1.5, $fn=20, center=false);
            }
        }        

        // horizontale as
        translate([56,0,55]){
            rotate([-90,0,0]){
               cylinder(h=50, r=4, $fn=20, center=false);
            }
        }
        translate([56,0,55-42/2]){
            rotate([-90,0,0]){
               cylinder(h=50, r=9, $fn=20, center=false);
            }
        }

         // verticale as
        translate([57,53,44]){
            rotate([0,90,0]){
               cylinder(h=50, r=4, $fn=20, center=false);
            }
        }
        translate([57,53,44-42/2]){
            rotate([0,90,0]){
               cylinder(h=50, r=9, $fn=20, center=false);
            }
        }
        
        // schroefgat assen
        translate([58,41,55]){
            rotate([0,90,0]){
               cylinder(h=15, r=1.5, $fn=20, center=false);
            }
        }
        translate([72,50,44]){
            rotate([90,0,0]){
               cylinder(h=12, r=1.5, $fn=20, center=false);
            }
        }
        

         // schroefgaten
        translate([65,10,-1]){
            rotate([0,0,90]){
               cylinder(h=50, r=3, $fn=20, center=false);
            }
        }
        translate([65,106,-1]){
            rotate([0,0,90]){
               cylinder(h=50, r=3, $fn=20, center=false);
            }
        }
        translate([10,55,-1]){
            rotate([0,0,90]){
               cylinder(h=50, r=3, $fn=20, center=false);
            }
        }
        

	}
}
}