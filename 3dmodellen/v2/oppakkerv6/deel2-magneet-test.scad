
difference(){
	union(){



     


        translate([-7.5+54-32,-17+11.5,-4-15]){
            translate([51/2,27/2,0]){
                rotate([0,0,0]){
                    cylinder(h=4, r=18.0, $fn=100, center=false);
                }
            }  

        }     

    


	}
	union() {

        translate([-7.5+54-32,-17+11.5,-15]){
            translate([51/2,27/2,0]){
                rotate([0,0,0]){
                    cylinder(h=40, r=15.0, $fn=100, center=false);
                }
            }  

        }   
  



        translate([-7.5+54-32,-17+11.5,-50]){

            translate([51/2,27/2,-1]){
                rotate([0,0,0-30]){
                    translate([0,0,0]){
                        cylinder(h=500, r=1.2, $fn=100, center=false);
                    }
                }

                rotate([0,0,0-30]){
                    translate([11.5,0,0]){
                        cylinder(h=500, r=1.7, $fn=100, center=false);
                    }
                }
                rotate([0,0,120-30]){
                    translate([11.5,0,0]){
                        cylinder(h=500, r=1.7, $fn=100, center=false);
                    }
                }
                rotate([0,0,240-30]){
                    translate([11.5,0,0]){
                        cylinder(h=500, r=1.7, $fn=100, center=false);
                    }
                }
            }
    }     

      
	}
}
