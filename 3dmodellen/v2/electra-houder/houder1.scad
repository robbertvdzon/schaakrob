

        difference(){
            union(){

                translate([0,0,0]){
                    cube([12,3,12], center=false);
                }      
                translate([0,0,0]){
                    cube([12,20,3], center=false);
                }      
                translate([0,20-5,0]){
                    cube([12,5,6], center=false);
                }      
                

            }
            union() {
                translate([6,50,8]){
                    rotate([90,0,0]){
                        cylinder(h=100, r=2, $fn=100, center=false);
                    }
                }  
                translate([6,9,-1]){
                    rotate([0,0,90]){
                        cylinder(h=50, r1=2, $fn=100, center=false);
                    }
                }  
                translate([6,17.5,-1]){
                    rotate([0,0,90]){
                        cylinder(h=50, r1=1.2, $fn=100, center=false);
                    }
                }  
                


            }
        }
