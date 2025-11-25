
difference(){
	union(){

     

        translate([-3,-1.5,-18.5]){
            cube([6,1.5,36], center=false);
        }         

        translate([-3,-1,-18.5-25]){
            cube([6,1,36+25*2], center=false);
        }         
     


        translate([-3,-1,-37.6]){
            rotate([0,90,0]){
                cylinder(h=6, r=6, $fn=100, center=false);
            }
        }         
        translate([-3,-1,36.6]){
            rotate([0,90,0]){
                cylinder(h=6, r=6, $fn=100, center=false);
            }
        }         

        
        // 4 assen
        translate([-6.5,0,-14.5]){
            rotate([90,0,0]){
                cylinder(h=1.5, r=4, $fn=100, center=false);
            }
        }         
        translate([6.5,0,-14.5]){
            rotate([90,0,0]){
                cylinder(h=1.5, r=4, $fn=100, center=false);
            }
        }         
        translate([-6.5,0,13.5]){
            rotate([90,0,0]){
                cylinder(h=1.5, r=4, $fn=100, center=false);
            }
        }         
        translate([6.5,0,13.5]){
            rotate([90,0,0]){
                cylinder(h=1.5, r=4, $fn=100, center=false);
            }
        }         




	}
	union() {

        
        // gaten voor riemhouders
        translate([-6.5,8.5,-14.5]){
            rotate([90,0,0]){
                cylinder(h=125, r=4.7/2, $fn=100, center=false);
            }
        }         
        translate([6.5,8.5,-14.5]){
            rotate([90,0,0]){
                cylinder(h=125, r=4.7/2, $fn=100, center=false);
            }
        }         

        translate([-6.5,8.5,13.5]){
            rotate([90,0,0]){
                cylinder(h=125, r=4.7/2, $fn=100, center=false);
            }
        }         
        translate([6.5,8.5,13.5]){
            rotate([90,0,0]){
                cylinder(h=125, r=4.7/2, $fn=100, center=false);
            }
        }       
  
          translate([-4,0,-18.5-25-1]){
            cube([8,10,36+25*2+2], center=false);
        }         
      
	}
}
