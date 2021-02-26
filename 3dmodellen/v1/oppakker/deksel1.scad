
difference(){
	union(){


        
        // verdikking voor schuifcontacten

        translate([18,24-52+6.5,0]){
            rotate([0,0,90]){
                cylinder(h=3, r=4.2, $fn=100, center=false);
            }
        }        
        translate([18+39,24-52+6.5,0]){
            rotate([0,0,90]){
                cylinder(h=3, r=4.2, $fn=100, center=false);
            }
        }        
        translate([18,24-52,0]){
           cube([39,14,3], center=false);
        }


        
	}
	union() {

        // schroefgaten deksel verticale assen
        translate([18,24-52+6.5,-1]){
            rotate([0,0,90]){
                cylinder(h=48, r=1.2, $fn=100, center=false);
            }
        }        
        translate([18+39,24-52+6.5,-1]){
            rotate([0,0,90]){
                cylinder(h=48, r=1.2, $fn=100, center=false);
            }
        }        

        translate([18+39-13,24-52+6.5,-1]){
            rotate([0,0,90]){
                cylinder(h=48, r=3, $fn=100, center=false);
            }
        }        
        translate([18+13,24-52+6.5,-1]){
            rotate([0,0,90]){
                cylinder(h=48, r=3, $fn=100, center=false);
            }
        }        
        

	}
}
