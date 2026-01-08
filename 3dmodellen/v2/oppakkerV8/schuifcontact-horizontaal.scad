

difference(){
	union(){    
        

        translate([-34.5,-5,35]){
              cube([69,10,3], center=false);
        }
        translate([-34.5,-15,35]){
              cube([10,20,3], center=false);
        }
        translate([34.5-10,-15,35]){
              cube([10,20,3], center=false);
        }
        translate([0,5,37.5]){
            rotate([90,0,0]){
                cylinder(h=10, r=2.5, $fn=100, center=false);
            }
        }    


	}
	union() {

        translate([10,0,0]){
            rotate([0,0,90]){
                cylinder(h=200, r=1.5, $fn=100, center=false);
            }
        }    
        translate([20,0,0]){
            rotate([0,0,90]){
                cylinder(h=200, r=1.5, $fn=100, center=false);
            }
        }    
        translate([-10,0,0]){
            rotate([0,0,90]){
                cylinder(h=200, r=1.5, $fn=100, center=false);
            }
        }    
        translate([-20,0,0]){
            rotate([0,0,90]){
                cylinder(h=200, r=1.5, $fn=100, center=false);
            }
        }    

        translate([-29.5,-2,0]){
            rotate([0,0,90]){
                cylinder(h=200, r=1.5, $fn=100, center=false);
            }
        }    
        translate([-29.5,-11,0]){
            rotate([0,0,90]){
                cylinder(h=200, r=1.5, $fn=100, center=false);
            }
        }    
        translate([29.5,-2,0]){
            rotate([0,0,90]){
                cylinder(h=200, r=1.5, $fn=100, center=false);
            }
        }    
        translate([29.5,-11,0]){
            rotate([0,0,90]){
                cylinder(h=200, r=1.5, $fn=100, center=false);
            }
        }    


        
	}
}
