diameter_binnenste_asgaten=4.1;



	union(){
        translate([0,0,0]){
            rotate([90,0,0]){
                for (hoek =[0:45:360])
                rotate([0,0,hoek]){
                    translate([-1,-5.5,0]){
                        rotate([0,0,0]){
                            cube([2,5,5], center=false);
                        }
                    }
                }
            }
        }

        translate([0,0,0]){
            rotate([90,0,0]){
                cylinder(h=5, r=diameter_binnenste_asgaten, $fn=100, center=false);
            }
        }
        
	}
