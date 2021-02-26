
difference(){
	union(){

        translate([-2+67,-20,0]){
           cube([14,20,3], center=false);
        }
        translate([4+67,24-52+6.5+2,0]){
            rotate([0,0,90]){
                cylinder(h=3, r=4.2, $fn=100, center=false);
            }
        }        
        translate([4+67,24-52+6.5+2+20,0]){
            rotate([0,0,90]){
                cylinder(h=3, r=4.2, $fn=100, center=false);
            }
        }        

        
	}
	union() {

        translate([4+67,24-52+6.5+2,-1]){
            rotate([0,0,90]){
                cylinder(h=38, r=1.2, $fn=100, center=false);
            }
        }        

        translate([4+67,24-52+6.5+2+11,-1]){
            rotate([0,0,90]){
                cylinder(h=38, r=3, $fn=100, center=false);
            }
        }        


        translate([4+67,24-52+6.5+2+20,-1]){
            rotate([0,0,90]){
                cylinder(h=38, r=1.2, $fn=100, center=false);
            }
        }        
        

	}
}
