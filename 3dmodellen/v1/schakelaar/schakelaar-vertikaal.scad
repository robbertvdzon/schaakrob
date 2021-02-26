

        difference(){
            union(){

                translate([3.5,0,30]){
                    cube([5,4,12], center=false);
                }      


                translate([3.5,15,0]){
                    cube([5,4,42], center=false);
                }      


                translate([0,0,0]){
                    cube([12,2,35], center=false);
                }      
                translate([0,0,0]){
                    cube([12,4,7], center=false);
                }      


                translate([0,15,0]){
                    cube([12,4,35], center=false);
                }      



                translate([0,-15,0]){
                    cube([12,45,3], center=false);
                }      

                translate([6,5,30]){
                    rotate([90,0,0]){
                        cylinder(h=5, r1=2, r2=5, $fn=100, center=false);
                    }
                }  

                translate([6,17,30]){
                    rotate([90,0,0]){
                        cylinder(h=5, r2=2, r1=5, $fn=100, center=false);
                    }
                }  



            }
            union() {

                translate([4.5,-15,-1]){
                    cube([3,15,5], center=false);
                }      
                translate([4.5,19,-1]){
                    cube([3,15,5], center=false);
                }      


                translate([6,50,30]){
                    rotate([90,0,0]){
                        cylinder(h=100, r=1.2, $fn=100, center=false);
                    }
                }  


            }
        }
