

difference(){
	union(){    
        
      translate([0,0,0]){
            rotate([0,0,0]){
                cylinder(h=5, r=6, $fn=100, center=false);
            }
        }
        

	}
	union() {
        
      translate([0,0,-1]){
            rotate([0,0,0]){
                cylinder(h=10, r=3.2, $fn=100, center=false);
            }
        }
        
        
	}
}
