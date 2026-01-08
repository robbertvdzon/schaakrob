

difference(){
	union(){    
        
       translate([0,0,29]){
            rotate([45,0,0]){
//                cube([150,1,1], center=true);
            }
       }


        translate([-71/2,102/2-21-10,13]){
              cube([14,21,53-13], center=false);
        }
        translate([+71/2-21+3+4,102/2-21-10,13]){
              cube([14,21,53-13], center=false);
        }

        translate([-71/2,-102/2+10,13]){
              cube([14,21,53-13], center=false);
        }
        translate([+71/2-21+3+4,-102/2+10,13]){
              cube([14,21,53-13], center=false);
        }





        translate([-71/2,102/2-3-10,13]){
              cube([65,3,26-13], center=false);
        }
        translate([-71/2,-102/2+10,13]){
              cube([60,3,26-13], center=false);
        }

        translate([-71/2,-102/2+10,13]){
              cube([3,80,18-13], center=false);
        }
        translate([71/2-3,-102/2+10,13]){
              cube([3,80,18-13], center=false);
        }


// 4 gaten voor printplaat
        translate([-71/2+6,-102/2+10+24,13]){
            rotate([0,0,0]){
                cylinder(h=5, r=6, $fn=100, center=false);
            }
        }           
        translate([-71/2+6,102/2-10-24,13]){
            rotate([0,0,0]){
                cylinder(h=5, r=6, $fn=100, center=false);
            }
        }           
        translate([71/2-6,102/2-10-24,13]){
            rotate([0,0,0]){
                cylinder(h=5, r=6, $fn=100, center=false);
            }
        }           
        translate([71/2-6,-102/2+10+24,13]){
            rotate([0,0,0]){
                cylinder(h=5, r=6, $fn=100, center=false);
            }
        }           
        
        // plaat er onder
         translate([-71/2,-82/2,10]){
              cube([71,82,3], center=false);
        }
        translate([9,-30+2.5+7,13]){
            rotate([0,0,90]){
                cylinder(h=2, r1=3.5, r2=1.5, $fn=100, center=false);
            }
        }           
        translate([-9,-30+2.5+7,13]){
            rotate([0,0,90]){
                cylinder(h=2, r1=3.5, r2=1.5, $fn=100, center=false);
            }
        }           


        translate([-16.5,-30,10]){
              cube([10,2.5,15], center=false);
        }
        translate([16.5-10,-30,10]){
              cube([10, 2.5,15], center=false);
        }
        translate([-16.5,-30+2.5+14,10]){
              cube([10,2.5,15], center=false);
        }
        translate([16.5-10,-30+2.5+14,10]){
              cube([10, 2.5,15], center=false);
        }        
        
        translate([-16.5,15,10]){
              cube([10,2.5,15], center=false);
        }
        translate([16.5-10,15,10]){
              cube([10, 2.5,15], center=false);
        }


	}
	union() {
        

        

       translate([0,0,19]){
            rotate([90,45,00]){
                cube([1,1,150], center=true);
            }
        }
       translate([0,0,19]){
                cube([12,150,8.2], center=true);
        }

        translate([-71/2+3,-102/2+3-4,40]){
              cube([15.1,150,100], center=false);
        }
        translate([+71/2-21+2.9,-102/2+3-4,40]){
              cube([15.1,150,100], center=false);
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
        translate([-40,-99/2+1.5+7.5+10,25]){
            rotate([0,90,0]){
                cylinder(h=10, r=1.5, $fn=100, center=false);
            }
        }           
        translate([20,-99/2+1.5+7.5+10,25]){
            rotate([0,90,0]){
                cylinder(h=20, r=1.5, $fn=100, center=false);
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
        

// 4 gaten voor printplaat
        translate([-29.5,-17,-2]){
            rotate([0,0,0]){
                cylinder(h=200, r=1.3, $fn=100, center=false);
            }
        }           
        translate([-29.5,17,-2]){
            rotate([0,0,0]){
                cylinder(h=200, r=1.3, $fn=100, center=false);
            }
        }           
        translate([29.5,17,-1]){
            rotate([0,0,0]){
                cylinder(h=200, r=1.3, $fn=100, center=false);
            }
        }           
        translate([29.5,-17,-1]){
            rotate([0,0,0]){
                cylinder(h=200, r=1.3, $fn=100, center=false);
            }
        }    
 
// onderkant
        // gaten
        





        translate([-25,-35,8]){
              cube([50,5,8], center=false);
        }
        translate([-25,35-5,8]){
              cube([50,5,8], center=false);
        }
        translate([-71/2+2,-10,8]){
              cube([5,20,8], center=false);
        }



        translate([9,-30+2.5+7,-2]){
            rotate([0,0,0]){
                cylinder(h=200, r=1.5, $fn=100, center=false);
            }
        }           
        translate([-9,-30+2.5+7,-2]){
            rotate([0,0,0]){
                cylinder(h=200, r=1.5, $fn=100, center=false);
            }
        }           


// 4 gaten voor printplaat
        translate([-71/2+6,-102/2+10+24,-2]){
            rotate([0,0,0]){
                cylinder(h=200, r=1.5, $fn=100, center=false);
            }
        }           
        translate([-71/2+6,102/2-10-24,-2]){
            rotate([0,0,0]){
                cylinder(h=200, r=1.5, $fn=100, center=false);
            }
        }           
        translate([71/2-6,102/2-10-24,-1]){
            rotate([0,0,0]){
                cylinder(h=200, r=1.5, $fn=100, center=false);
            }
        }           
        translate([71/2-6,-102/2+10+24,-1]){
            rotate([0,0,0]){
                cylinder(h=200, r=1.5, $fn=100, center=false);
            }
        }               
                
        
        
	}
}
