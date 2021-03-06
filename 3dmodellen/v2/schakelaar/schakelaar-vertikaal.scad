

        difference(){
            union(){

                translate([0,10,0]){
                    cube([12,3,50], center=false);
                }      
                translate([0,10,0]){
                    cube([12,5,7], center=false);
                }      

                translate([0,24,0]){
                    cube([12,3,55-12], center=false);
                }      



                translate([0,0,0]){
                    cube([12,27,3], center=false);
                }      
                translate([0,27,6]){
                    cube([12,10,3], center=false);
                }      

                translate([6,15,50-12]){
                    rotate([90,0,0]){
                        cylinder(h=3, r1=3, r2=5, $fn=100, center=false);
                    }
                }  

                translate([6,25,50-12]){
                    rotate([90,0,0]){
                        cylinder(h=3, r2=3, r1=5, $fn=100, center=false);
                    }
                }  
                

            }
            union() {




                translate([6,50,50-12]){
                    rotate([90,0,0]){
                        cylinder(h=100, r=1.2, $fn=100, center=false);
                    }
                }  
                translate([6,3,-1]){
                    rotate([0,0,90]){
                        cylinder(h=50, r1=1.7, $fn=100, center=false);
                    }
                }  
                translate([6,32,-1]){
                    rotate([0,0,90]){
                       cylinder(h=50, r=1.2, $fn=20, center=false);
                    }
                }
                


            }
        }
