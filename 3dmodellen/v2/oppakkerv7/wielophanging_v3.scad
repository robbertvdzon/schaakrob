

difference(){
	union(){    
        
       translate([0,0,29]){
            rotate([45,0,0]){
//                cube([150,1,1], center=true);
            }
       }

        translate([-71/2+3,-5,35]){
//              cube([71/2,10,3], center=false);
        }

        translate([71/2+3,-102/2+16,20]){
            //  cube([3,15,23], center=false);
        }
        translate([71/2+3,-102/2+10+61,20]){
              //cube([3,15,23], center=false);
        }       
        translate([-71/2,-102/2+16,20]){
              cube([3,16,23], center=false);
        }
        translate([-71/2,-102/2+10+60,20]){
              cube([3,16,23], center=false);
        }

        translate([-71/2,-19,20]){
              cube([11,2,18], center=false);
        }
        translate([-71/2,17,20]){
              cube([11,2,18], center=false);
        }

        translate([-71/2,-19,20]){
              cube([11,38,3], center=false);
        }
        translate([-71/2,-10-9,35]){
              cube([11,38,3], center=false);
        }
        
        translate([-71/2+7,9,23]){
            rotate([0,0,90]){
                cylinder(h=2, r1=3.5, r2=1.5, $fn=100, center=false);
            }
        }           
        translate([-71/2+7,9,33]){
            rotate([0,0,90]){
                cylinder(h=2, r2=3.5, r1=1.5, $fn=100, center=false);
            }
        }       
    
       translate([1,5,35]){
            rotate([90,0,0]){
            //    cylinder(h=10, r=2, $fn=100, center=false);
            }
        }      


	}
	union() {
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

        translate([-71/2+7,9,0]){
            rotate([0,0,90]){
                cylinder(h=200, r=1.5, $fn=100, center=false);
            }
        }    


        translate([-71/2+7,0,0]){
            rotate([0,0,90]){
                cylinder(h=200, r=1.5, $fn=100, center=false);
            }
        }    
        translate([-71/2+7,-6,0]){
            rotate([0,0,90]){
                cylinder(h=200, r=1.5, $fn=100, center=false);
            }
        }    

 
        translate([-5,0,0]){
            rotate([0,0,0]){
                cylinder(h=100, r=1.5, $fn=100, center=false);
            }
        }      
       translate([-10,0,0]){
            rotate([0,0,0]){
                cylinder(h=100, r=1.5, $fn=100, center=false);
            }
        }      
       translate([-15,0,0]){
            rotate([0,0,0]){
                cylinder(h=100, r=1.5, $fn=100, center=false);
            }
        }      
       translate([-20,0,0]){
            rotate([0,0,0]){
                cylinder(h=100, r=1.5, $fn=100, center=false);
            }
        }      
       




        
	}
}
