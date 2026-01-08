

difference(){
	union(){    
        
        translate([-71/2-3,-102/2+16,10]){
              cube([3,16,43], center=false);
        }
        translate([-71/2-3,-102/2+10+60,10]){
              cube([3,16,43], center=false);
        }


        translate([-71/2-3,-102/2+16,35]){
              cube([1,70,18], center=false);
        }
        translate([-71/2-3,-102/2+16,10]){
              cube([1,70,10], center=false);
        }


        translate([-71/2-3,-19,20]){
              cube([13,2,18], center=false);
        }
        translate([-71/2-3,17,20]){
              cube([13,2,18], center=false);
        }

        translate([-71/2-3,-19,20]){
              cube([13,38,3], center=false);
        }
        translate([-71/2-3,-10-9,35]){
              cube([13,38,3], center=false);
        }
        
        translate([-71/2+7-1,9,23]){
            rotate([0,0,90]){
                cylinder(h=2, r1=3.5, r2=1.5, $fn=100, center=false);
            }
        }           
        translate([-71/2+7-1,9,33]){
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

        translate([-71/2+7-1,9,0]){
            rotate([0,0,90]){
                cylinder(h=200, r=1.5, $fn=100, center=false);
            }
        }    


        translate([-71/2+7-1,-2,30]){
            rotate([0,0,90]){
                cylinder(h=200, r=1.5, $fn=100, center=false);
            }
        }    
        translate([-71/2+7-1,-11,30]){
            rotate([0,0,90]){
                cylinder(h=200, r=1.5, $fn=100, center=false);
            }
        }    

 
  
       




        
	}
}
