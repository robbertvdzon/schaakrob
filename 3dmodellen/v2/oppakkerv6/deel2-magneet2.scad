
difference(){
	union(){

        
        translate([8,8,0]){
            rotate([0,0,0]){
                cylinder(h=1, r=4, $fn=100, center=false);
            }
        }
        translate([0+38+16+10+8,8,0]){
            rotate([0,0,0]){
                cylinder(h=1, r=4, $fn=100, center=false);
            }
        }
     

        translate([-4+12,-4-3+11,0]){
            cube([38+4+21+25-24,8,1], center=false);
        }         

        translate([-4+12+4,-4-3+11,0]){
            cube([38+4+21+25-24-8,8,3], center=false);
        }         

        translate([-7.5+54-32,-17+11.5,0]){
            translate([51/2,27/2,0]){
                rotate([0,0,0]){
                    cylinder(h=10, r=21.0, $fn=100, center=false);
                }
            }  

        }     

    


	}
	union() {
        translate([0,-6,3]){
            cube([100,28,39], center=false);
        }  

         

        translate([-7.5+54-32,-17+11.5,-15]){
            translate([51/2,27/2,0]){
                rotate([0,0,0]){
                    cylinder(h=40, r=18.0, $fn=100, center=false);
                }
            }  

        }   
  
        translate([8,8,-1]){
            rotate([0,0,0]){
                cylinder(h=52, r=3.7/2, $fn=100, center=false);
            }
        }
        translate([0+38+16+10+8,8,-1]){
            rotate([0,0,0]){
                cylinder(h=52, r=3.7/2, $fn=100, center=false);
            }
        }


      
	}
}
