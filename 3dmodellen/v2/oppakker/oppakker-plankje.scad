
difference(){
	union(){




        translate([3+8+2,-23+5,25]){
             cube([69-8-4,21,2], center=false);
        }
        translate([18+15,-23+5,25]){
             cube([9,47-22,2], center=false);
        }


        translate([18+15,-23+5+23,25]){
           cube([39-30,2,20], center=false);
        }
        
        
	}
	union() {

        // schroefgat printje
        translate([68,-20+5,-1]){
            rotate([0,0,90]){
                cylinder(h=30, r=1.2, $fn=100, center=false);
            }
        }        
        translate([68,-20+5+15,-1]){
            rotate([0,0,90]){
                cylinder(h=30, r=1.2, $fn=100, center=false);
            }
        }        
        translate([15,-20+5+15-5,-1]){
            rotate([0,0,90]){
                cylinder(h=30, r=1.2, $fn=100, center=false);
            }
        }        
        // boor gaten in staaf        
        translate([75/2,130,40]){
            rotate([90,0,0]){
                cylinder(h=200, r=1.5, $fn=100, center=false);
            }
        }    


        // gaten voor draden
        translate([75/2,-17,-1]){
            rotate([0,0,90]){
                cylinder(h=30, r=3, $fn=100, center=false);
            }
        }        
        translate([15,-17,-1]){
            rotate([0,0,90]){
                cylinder(h=30, r=3, $fn=100, center=false);
            }
        }        

        // gaten voor kroonsteen
        translate([75/2-9,-8,-1]){
            rotate([0,0,90]){
                cylinder(h=30, r=1.2, $fn=100, center=false);
            }
        }        
        translate([75/2+9,-8,-1]){
            rotate([0,0,90]){
                cylinder(h=30, r=1.2, $fn=100, center=false);
            }
        }        
        


	}
}
