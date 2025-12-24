
difference(){
	union(){

        
        translate([3-5-9,-26,-24+0]){
            cube([9,3,10], center=false);
        }         
        


        


	}
	union() {
       
        translate([-6.5,8.5,-19]){
            rotate([90,0,0]){
                cylinder(h=125, r=2, $fn=100, center=false);
            }
        }         
         



	}
}
