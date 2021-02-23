mirror(v= [0,180,0] ) {
    
difference(){
	union(){
        
        translate([0-4,40,-0]){
           cube([5,14,56], center=false);
        }
        translate([0,45,20]){
           cube([38,5,16], center=false);
        }
        translate([0,45,15]){
           cube([23,5,26], center=false);
        }
      
        translate([57/2+8,45+5,20+8]){
            rotate([90,0,0]){
                cylinder(h=5, r=8, $fn=100, center=false);
            }
        }
        
 
	}
	union() {

        translate([57/2+8,57-9,20+8]){
            rotate([90,0,0]){
                cylinder(h=8, r=4, $fn=100, center=true);
            }
        }

        translate([0,47.5,42+7]){
            rotate([0,90,0]){
                cylinder(h=200, r=3, $fn=100, center=true);
            }
        }
        translate([0,47.5,42-26-7]){
            rotate([0,90,0]){
                cylinder(h=200, r=3, $fn=100, center=true);
            }
        }

	}
}
}