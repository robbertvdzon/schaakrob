

difference(){
	union(){    
        
        
        translate([-71/2,-102/2+10,-2]){
              cube([71,82,4], center=false);
        }
      
        

	}
	union() {
        

       translate([-50,0,8]){
            rotate([0,90,0]){
                cylinder(h=200, r2=1.2, $fn=100, center=false);
            }
        }
       translate([14,100,19]){
            rotate([90,0,0]){
                cylinder(h=200, r2=1.2, $fn=100, center=false);
            }
        }
       translate([-14,100,19]){
            rotate([90,0,0]){
                cylinder(h=200, r2=1.2, $fn=100, center=false);
            }
        }


        translate([-71/2+23,102/2-21-18-5,5]){
            rotate([0,0,20]){
             // cube([1.5,20,30], center=false);
            }
        }
        translate([-71/2+25,102/2-21-18-24,15]){
            rotate([0,0,110]){
           //   cube([1.5,20,30], center=false);
            }
        }
        
        

       translate([0,0,19]){
            rotate([90,45,00]){
                cube([8,8,150], center=true);
            }
        }
       translate([0,0,29]){
            rotate([45,0,0]){
                cube([150,8,8], center=true);
            }
        }

       translate([20,3,29]){
            rotate([0,0,0]){
                cube([40,8,9], center=true);
            }
        }


        translate([-71/2+3,-102/2+3-4,40]){
              cube([15.1,150,100], center=false);
        }
        translate([+71/2-21+2.9,-102/2+3-4,40]){
              cube([15.1,150,100], center=false);
        }


        translate([0,-34,-3]){
            rotate([0,0,0]){
                cylinder(h=200, r=1.5, $fn=100, center=false);
            }
        }           
        translate([0,34,-3]){
            rotate([0,0,0]){
                cylinder(h=200, r=1.5, $fn=100, center=false);
            }
        }           


        translate([-100,-99/2+1.5+7.5+10,40]){
            rotate([0,90,0]){
                cylinder(h=200, r=1.5, $fn=100, center=false);
            }
        }           
        translate([-100,(-99/2+1.5+7.5+10)*-1,40]){
            rotate([0,90,0]){
                cylinder(h=200, r=1.5, $fn=100, center=false);
            }
        }           
        translate([-100,-99/2+1.5+7.5+10,25]){
            rotate([0,90,0]){
                cylinder(h=200, r=1.5, $fn=100, center=false);
            }
        }           
        translate([-100,(-99/2+1.5+7.5+10)*-1,25]){
            rotate([0,90,0]){
                cylinder(h=200, r=1.5, $fn=100, center=false);
            }
        }           



        
        translate([-71/2+3,102/2-21+3-10,-1]){
              cube([15.1,15,100], center=false);
        }
        translate([+71/2-21+2.9,102/2-21+3-10,-1]){
              cube([15.1,15,100], center=false);
        }

        translate([-71/2+3,-102/2+3+10,-1]){
              cube([15.1,15,100], center=false);
        }
        translate([+71/2-21+2.9,-102/2+3+10,-1]){
              cube([15.1,15,100], center=false);
        }
        
        
	}
}
