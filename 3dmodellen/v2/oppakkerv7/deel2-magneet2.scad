
difference(){
	union(){


        translate([-36/2,-42/2,0]){
              cube([36,42,3], center=false);
        }
        
        
     

        translate([-71/2,-10,0]){
            cube([71,20,1], center=false);
        }         

        translate([-40/2,-10,0]){
            cube([40,20,3], center=false);
        }         

    


	}
	union() {
        translate([-30/2,-36/2,-1]){
            cube([30,36,50], center=false);
       }

  
        translate([15+10,0,-1]){
            rotate([0,0,0]){
                cylinder(h=52, r=3.7/2, $fn=100, center=false);
            }
        }
        translate([-15-10,0,-1]){
            rotate([0,0,0]){
                cylinder(h=52, r=3.7/2, $fn=100, center=false);
            }
        }


      
	}
}
