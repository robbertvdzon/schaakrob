

        difference(){
            union(){

                translate([0,0,0]){
                    cube([35,3,23], center=false);
                }      
                translate([0,0,0]){
                    cube([60,10,3], center=false);
                }      
                

            }
            union() {
                translate([5,50,20]){
                    rotate([90,0,0]){
                        cylinder(h=100, r=1.5, $fn=100, center=false);
                    }
                }  
                translate([30,50,20]){
                    rotate([90,0,0]){
                        cylinder(h=100, r=1.5, $fn=100, center=false);
                    }
                }  
                translate([55,5,-1]){
                    rotate([0,0,90]){
                        cylinder(h=50, r1=2, $fn=100, center=false);
                    }
                }  
                translate([42,5,-1]){
                    rotate([0,0,90]){
                        cylinder(h=50, r1=2, $fn=100, center=false);
                    }
                }  
                


            }
        }
