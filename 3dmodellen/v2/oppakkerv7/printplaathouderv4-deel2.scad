

difference(){
	union(){    
        

        translate([-71/2,-82/2,10]){
              cube([71,82,3], center=false);
        }

       translate([0,0,19]){
            rotate([90,45,00]){
          //      cube([8,8,150], center=true);
            }
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

        
        



	}
	union() {


        translate([-25.5,-30,8]){
                cylinder(h=20, r=9, $fn=100, center=false);
        }
        translate([-25.5,30,8]){
                cylinder(h=20, r=9, $fn=100, center=false);
        }
        translate([25.5,30,8]){
                cylinder(h=20, r=9, $fn=100, center=false);
        }
        translate([25.5,-30,8]){
                cylinder(h=20, r=9, $fn=100, center=false);
        }



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
