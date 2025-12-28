

difference(){
	union(){    
        
        translate([-10,-10,0]){
              cube([20,20,3], center=false);
        }
        translate([-20,0,0]){
              cube([40,25,3], center=false);
        }

        translate([-10,-10,0]){
              cube([4,20,24], center=false);
        }
        translate([6,-10,0]){
              cube([4,20,24], center=false);
        }
       translate([-6,0,19]){
            rotate([0,90,0]){
                cylinder(h=2, r1=3.5, r2=1.5, $fn=100, center=false);
            }            
        }
       translate([4,0,19]){
            rotate([0,90,0]){
                cylinder(h=2, r1=1.5, r2=3.5, $fn=100, center=false);
            }            
        }

        

	}
	union() {

       translate([-20,0,19]){
            rotate([0,90,0]){
                cylinder(h=500, r=1.5, $fn=100, center=false);
            }            
        }
       translate([0,9,-10]){
            rotate([0,0,0]){
                cylinder(h=500, r=5.5, $fn=100, center=false);
            }            
        }
        
        
	}
}
