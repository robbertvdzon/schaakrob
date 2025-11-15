

difference(){
	union(){
        translate([0,0,0]){
            rotate([0,0,0]){
                cube([3,20,6], center=false);
            }
        }        
      


        
	}
	union() {
      translate([-1,4,3]){
            rotate([0,90,0]){
                cylinder(h=5, r=1.5, $fn=100, center=false);
            }
        }   
      translate([-1,16,3]){
            rotate([0,90,0]){
                cylinder(h=5, r=1.5, $fn=100, center=false);
            }
        }   


   
	}
}

