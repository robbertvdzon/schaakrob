difference(){
	union(){ 

        difference(){
            union(){ 

                translate([0, 0,0]){
                   //    cylinder(h=2.2, r=14 , $fn=100, center=false);
                }                              


                translate([0+10,0,0]){
                    rotate([0,0,0]){
                        cylinder(h=2.2, r=4, $fn=100, center=false);
                    }   
                }
                translate([0-10,0,0]){
                    rotate([0,0,0]){
                        cylinder(h=2.2, r=4, $fn=100, center=false);
                    }   
                }
                translate([0,10,0]){
                    rotate([0,0,0]){
                        cylinder(h=2.2, r=4, $fn=100, center=false);
                    }   
                }
               translate([-10,-4,0]){
                    cube([20,8,2.2], center=false);
                }        
               translate([-4,2,0]){
                    cube([8,8,2.2], center=false);
                }        


            }
            union() {
                translate([0+10,0,-0.1]){
                    rotate([0,0,0]){
                        cylinder(h=500, r=2.7, $fn=100, center=false);
                    }   
                }
                translate([0-10,0,-0.1]){
                    rotate([0,0,0]){
                        cylinder(h=500, r=2.7, $fn=100, center=false);
                    }   
                }
                translate([0,10,-0.1]){
                    rotate([0,0,0]){
                        cylinder(h=500, r=2.7, $fn=100, center=false);
                    }   
                }
               translate([-15,-20,-1]){
                    cube([30,16,6], center=false);
                }        
            }
         }

	}
	union() {
    }
}


 