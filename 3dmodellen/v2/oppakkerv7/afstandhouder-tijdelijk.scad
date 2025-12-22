

difference(){
	union(){    
        
        
       translate([0,0,0]){
            rotate([0,0,0]){
                cylinder(h=2, r=7, $fn=100, center=false);
            }
        }
       translate([0,0,0]){
            rotate([0,0,0]){
                cylinder(h=10, r=3.5, $fn=100, center=false);
            }
        }
        

	}
	union() {
       translate([0,0,-1]){
            rotate([0,0,0]){
                cylinder(h=20, r=1.3, $fn=100, center=false);
            }
        }
        
        
	}
}
