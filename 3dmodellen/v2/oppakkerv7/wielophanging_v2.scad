

difference(){
	union(){    
        
        
        translate([-71/2,-102/2+16,20]){
              cube([3,15,23], center=false);
        }
        translate([-71/2,-102/2+10+61,20]){
              cube([3,15,23], center=false);
        }
        translate([-71/2,-25,20]){
              cube([8,5,18], center=false);
        }

        translate([-71/2,20,20]){
              cube([8,5,18], center=false);
        }

        translate([-71/2,-10-15,20]){
              cube([8,50,3], center=false);
        }
        translate([-71/2,-10-15,35]){
              cube([8,50,3], center=false);
        }
        translate([-71/2,-10-15,33]){
              cube([8,26,5], center=false);
        }
        translate([-71/2,-10-15,20]){
              cube([8,26,5], center=false);
        }
        
        translate([-71/2+4,9,23]){
            rotate([0,0,90]){
                cylinder(h=2, r1=3.5, r2=1.5, $fn=100, center=false);
            }
        }           
        translate([-71/2+4,9,33]){
            rotate([0,0,90]){
                cylinder(h=2, r2=3.5, r1=1.5, $fn=100, center=false);
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

        translate([-71/2+4,9,0]){
            rotate([0,0,90]){
                cylinder(h=200, r=1.5, $fn=100, center=false);
            }
        }           


       translate([0,0,29]){
            rotate([45,0,0]){
            //    cube([150,8,8], center=true);
            }
       }


        
	}
}
