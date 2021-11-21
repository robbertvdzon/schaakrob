
difference(){
	union(){



        translate([0,0,0]){
           cube([8,77,5], center=false);
        }        
        translate([0,0,0]){
           cube([8,4,15], center=false);
        }        
             
        
	}
	union() {

        translate([4,20,11]){
            rotate([90,0,0]){
               cylinder(h=999, r=2, $fn=100, center=false);
            }
        }
     


	}
}
