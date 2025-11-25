
difference(){
	union(){
        

        translate([-7,0,4]){
            cube([3,8,16], center=false);
        }         

        translate([0,0,0]){
            cube([8,8,3], center=false);
        }         
        translate([8,0,0]){
            rotate([0,-30,0]){
                cube([8,8,3], center=false);
            }
        }         
        rotate([0,0,180]){
            translate([0,-8,0]){
                rotate([0,-30,0]){
                    cube([8,8,3], center=false);
                }
            }         
        }       
       

          
      

	}
	union() {
        translate([1,-1,2]){
            cube([6,10,3], center=false);
        }  

	}
}
