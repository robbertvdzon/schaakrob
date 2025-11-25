difference(){
	union(){ 

        difference(){
            union(){ 

                translate([-65/2, -12,0]){
                       cube([65,65,4], center=false);
                }                              

            }
            union() {
                translate([0+10,0,1]){
                    rotate([0,0,0]){
                        cylinder(h=500, r=2.7, $fn=100, center=false);
                    }   
                }
                translate([0-10,0,1]){
                    rotate([0,0,0]){
                        cylinder(h=500, r=2.7, $fn=100, center=false);
                    }   
                }
                translate([0,10,1]){
                    rotate([0,0,0]){
                        cylinder(h=500, r=2.7, $fn=100, center=false);
                    }   
                }
            }
         }

	}
	union() {
    }
}


 