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
        translate([0,-12,0]){
           cube([74,116+12,4], center=false);
        }

        // muren voor horizontale motor
        translate([0,39-13,0]){
           cube([43,4,62-15], center=false);
        }
        translate([43,26,0]){
           cube([4,17,32], center=false);
        }
        translate([0,26,0]){
           cube([2,17,15], center=false);
        }

        // muren voor verticale motor
        translate([39,116-43,0]){
           cube([4,43,73-15], center=false);
        }
        translate([43,116-43,0]){
           cube([10,4,12], center=false);
        }
        translate([30,116-4,0]){
           cube([20,4,12], center=false);
        }

        // muren voor horizontale as
        translate([43,39,0]){
           cube([70-43,4,52], center=false);
        }
        translate([70,39,0]){
           cube([4,26,40], center=false);
        }

        // schroefgat assen
        translate([58,41,45]){
            rotate([0,90,0]){
               cylinder(h=12, r=4, $fn=20, center=false);
            }
        }
        translate([72,51,34]){
            rotate([90,0,0]){
               cylinder(h=12, r=4, $fn=20, center=false);
            }
        }
        
        // aansluitpunt voor schakelaars
        translate([35.5,-22,0]){//
           cube([12,10,4], center=false);
        }
        translate([35.5,-23,0]){//
           cube([12,10,6], center=false);
        }
        translate([74,65.5,0]){
           cube([10,12,6], center=false);
        }
        
      
	}
	union() {

        // gaten voor verticale motor
        translate([35,94,51.5-15]){
            rotate([0,90,0]){
               cylinder(h=10, r=30/2, $fn=20, center=false);
            }
        }
        translate([35,94+31/2,51.5+31/2-15]){
            rotate([0,90,0]){
               cylinder(h=10, r=1.5, $fn=20, center=false);
            }
        }
        translate([35,94-31/2,51.5+31/2-15]){
            rotate([0,90,0]){
               cylinder(h=10, r=1.5, $fn=20, center=false);
            }
        }
        translate([35,94+31/2,51.51-31/2-15]){
            rotate([0,90,0]){
               cylinder(h=10, r=1.5, $fn=20, center=false);
            }
        }
        translate([35,94-31/2,51.5-31/2-15]){
            rotate([0,90,0]){
               cylinder(h=10, r=1.5, $fn=20, center=false);
            }
        }        
        
        // gaten voor horizontale motor
        translate([43/2,39-5-12,40.5-15]){
            rotate([-90,0,0]){
               cylinder(h=10, r=30/2, $fn=20, center=false);
            }
        }
        translate([43/2+31/2,39-5-12,40.5+31/2-15]){
            rotate([-90,0,0]){
               cylinder(h=10, r=1.5, $fn=20, center=false);
            }
        }
        translate([43/2-31/2,39-5-12,40.5+31/2-15]){
            rotate([-90,0,0]){
               cylinder(h=10, r=1.5, $fn=20, center=false);
            }
        }
        translate([43/2+31/2,39-5-12,40.5-31/2-15]){
            rotate([-90,0,0]){
               cylinder(h=10, r=1.5, $fn=20, center=false);
            }
        }
        translate([43/2-31/2,39-5-12,40.5-31/2-15]){
            rotate([-90,0,0]){
               cylinder(h=10, r=1.5, $fn=20, center=false);
            }
        }        

        // horizontale as
        translate([56,0,55-10]){
            rotate([-90,0,0]){
               cylinder(h=50, r=4, $fn=20, center=false);
            }
        }
        translate([64,0,55-42/2-15]){
            rotate([-90,0,0]){
               cylinder(h=50, r=3, $fn=20, center=false);
            }
        }
        translate([57,0,55-42/2-15]){
            rotate([-90,0,0]){
               cylinder(h=50, r=3, $fn=20, center=false);
            }
        }
        translate([50,0,55-42/2-15]){
            rotate([-90,0,0]){
               cylinder(h=50, r=3, $fn=20, center=false);
            }
        }

         // verticale as
        translate([57,53,44-10]){
            rotate([0,90,0]){
               cylinder(h=50, r=4, $fn=20, center=false);
            }
        }
        translate([57,53-7,44-42/2-15]){
            rotate([0,90,0]){
               cylinder(h=50, r=3, $fn=20, center=false);
            }
        }
        translate([57,53,44-42/2-15]){
            rotate([0,90,0]){
               cylinder(h=50, r=3, $fn=20, center=false);
            }
        }
        translate([57,60,44-42/2-15]){
            rotate([0,90,0]){
               cylinder(h=50, r=3, $fn=20, center=false);
            }
        }
        
        // schroefgat assen
        translate([58,41,55-10]){
            rotate([0,90,0]){
               cylinder(h=15, r=1.5, $fn=20, center=false);
            }
        }
        translate([72,50,44-10]){
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
        translate([65,110,-1]){
            rotate([0,0,90]){
               cylinder(h=50, r=3, $fn=20, center=false);
            }
        }
        translate([10,110,-1]){
            rotate([0,0,90]){
               cylinder(h=50, r=3, $fn=20, center=false);
            }
        }  
    
        // schroefgaten voor schakelaars
        translate([41.5,-5-12,-1]){
            rotate([0,0,90]){
               cylinder(h=50, r=1.2, $fn=20, center=false);
            }
        }
        translate([79,71.5,-1]){
            rotate([0,0,90]){
               cylinder(h=50, r=1.2, $fn=20, center=false);
            }
        }

        // gaten in de vloer
        translate([7,-5,-1]){
           cube([45,25,10], center=false);
        }        
        translate([7,50,-1]){
           cube([25,55,10], center=false);
        }        
        translate([40,45,-1]){
           cube([25,25,10], center=false);
        }        
        translate([45,80,-1]){
           cube([15,30,10], center=false);
        }        
        
        

	}
}
}