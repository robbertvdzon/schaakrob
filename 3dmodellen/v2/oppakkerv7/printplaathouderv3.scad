

difference(){
	union(){    
        
        
        translate([-71/2,-102/2+10,0]){
         //     cube([71,82,3], center=false);
        }
        translate([-71/2,102/2-21-10,0]){
              cube([18,21,53], center=false);
        }
        translate([+71/2-21+3,102/2-21-10,0]){
              cube([18,21,53], center=false);
        }

        translate([-71/2,-102/2+10,0]){
              cube([18,21,53], center=false);
        }
        translate([+71/2-21+3,-102/2+10,0]){
              cube([18,21,53], center=false);
        }





        translate([-71/2,102/2-3-10,0]){
              cube([55,3,26], center=false);
        }
        translate([-71/2,-102/2+10,0]){
              cube([55,3,26], center=false);
        }

        translate([-71/2,-102/2+10,0]){
              cube([3,80,18], center=false);
        }
        translate([71/2-3,-102/2+10,0]){
              cube([3,80,18], center=false);
        }



//        translate([-71/2-4,-99/2+1.5+7.5+10,40]){
//            rotate([0,90,0]){
//                cylinder(h=4, r1=4, r2=7, $fn=100, center=false);
//            }
//        }           
//        translate([-71/2-4,(-99/2+1.5+7.5+10)*-1,40]){
//            rotate([0,90,0]){
//                cylinder(h=4, r1=4, r2=7, $fn=100, center=false);
//            }
//        }           
//        translate([-71/2-4,-99/2+1.5+7.5+10,25]){
//            rotate([0,90,0]){
//                cylinder(h=4, r1=4, r2=7, $fn=100, center=false);
//            }
//        }           
//        translate([-71/2-4,(-99/2+1.5+7.5+10)*-1,25]){
//            rotate([0,90,0]){
//                cylinder(h=4, r1=4, r2=7, $fn=100, center=false);
//            }
//        }           
//
//        translate([71/2,-99/2+1.5+7.5+10,40]){
//            rotate([0,90,0]){
//                cylinder(h=4, r1=7, r2=4, $fn=100, center=false);
//            }
//        }           
//        translate([71/2,(-99/2+1.5+7.5+10)*-1,40]){
//            rotate([0,90,0]){
//                cylinder(h=4, r1=7, r2=4, $fn=100, center=false);
//            }
//        }           
//        translate([71/2,-99/2+1.5+7.5+10,25]){
//            rotate([0,90,0]){
//                cylinder(h=4, r1=7, r2=4, $fn=100, center=false);
//            }
//        }           
//        translate([71/2,(-99/2+1.5+7.5+10)*-1,25]){
//            rotate([0,90,0]){
//                cylinder(h=4, r1=7, r2=4, $fn=100, center=false);
//            }
//        }           

// 4 gaten voor printplaat
        translate([-71/2+6,-102/2+10+24,0]){
            rotate([0,0,0]){
                cylinder(h=3, r=5, $fn=100, center=false);
            }
        }           
        translate([-71/2+6,102/2-10-24,0]){
            rotate([0,0,0]){
                cylinder(h=3, r=5, $fn=100, center=false);
            }
        }           
        translate([71/2-6,102/2-10-24,0]){
            rotate([0,0,0]){
                cylinder(h=3, r=5, $fn=100, center=false);
            }
        }           
        translate([71/2-6,-102/2+10+24,0]){
            rotate([0,0,0]){
                cylinder(h=3, r=5, $fn=100, center=false);
            }
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


        

       translate([0,0,19]){
            rotate([90,45,00]){
                cube([8,8,150], center=true);
            }
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
