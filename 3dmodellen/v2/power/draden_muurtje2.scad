
difference(){
	union(){



        translate([0,0,0]){
           cube([50,50,2], center=false);
        }        


             
        translate([0,0,15]){
            rotate([-90,0,0]){
               cylinder(h=50, r=15, $fn=100, center=false);
            }
        }      
        
        
	}
	union() {
        translate([0,-1,15]){
            rotate([-90,0,0]){
               cylinder(h=60, r=13, $fn=100, center=false);
            }
        }      
        translate([0,-10,2]){
           cube([60,70,100], center=false);
        }        
        translate([-20,-10,13]){
           cube([60,70,100], center=false);
        }        



	}
}
