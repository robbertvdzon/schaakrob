
difference(){
	union(){

        translate([3,-26,-24]){
            rotate([0,-90,0]){
                linear_extrude(height = 20)
                    polygon(points=[
                        [0,0],
                        [48,0],
                        [24,15]
                    ]);
            }            
        }


        


	}
	union() {
       
        translate([-6.5,8.5,-19]){
            rotate([90,0,0]){
                cylinder(h=125, r=3.2/2, $fn=100, center=false);
            }
        }         
        translate([-6.5,8.5,19]){
            rotate([90,0,0]){
                cylinder(h=125, r=3.2/2, $fn=100, center=false);
            }
        }         



	}
}
