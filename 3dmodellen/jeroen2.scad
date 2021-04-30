




difference(){
	union(){

        translate([0,0,0]){
            rotate([90,0,0]){
                cylinder(h=28, r1=21.3/2,  r2=21/2, $fn=100, center=false);
            }
        }       


            }
            union() {

                translate([7,1,0]){
                    rotate([90,0,0]){
                        cylinder(h=30, r=16/2, $fn=100, center=false);
                    }
                }       

           

            }
        }
