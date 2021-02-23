

        difference(){
            union(){

                translate([3.5,0,38]){
                    cube([5,4,12+7], center=false);
                }      


                translate([3.5,15,0]){
                    cube([5,4,42+15], center=false);
                }      


                translate([0,0,0]){
                    cube([12,2,45], center=false);
                }      
                translate([0,0,0]){
                    cube([12,4,7], center=false);
                }      


                translate([0,15,0]){
                    cube([12,4,45], center=false);
                }      



                translate([-25,10,0]){
                    cube([20,5,65], center=false);
                }
                translate([-10,0,0]){
                    cube([5,20,15], center=false);
                }
                translate([-25,0,0]){
                    cube([5,20,15], center=false);
                }

                translate([-25,-15,0]){
                    cube([37,50,3], center=false);
                }      

                translate([6,5,38]){
                    rotate([90,0,0]){
                        cylinder(h=5, r1=2, r2=5, $fn=100, center=false);
                    }
                }  

                translate([6,17,38]){
                    rotate([90,0,0]){
                        cylinder(h=5, r2=2, r1=5, $fn=100, center=false);
                    }
                }  


            }
            union() {
                translate([-25+37/2,-10,-1]){
                    rotate([0,0,90]){
                        cylinder(h=10, r=2, $fn=100, center=false);
                    }
                }  
                translate([-25+37/2,30,-1]){
                    rotate([0,0,90]){
                        cylinder(h=10, r=2, $fn=100, center=false);
                    }
                }  

                translate([-15,0,57/2+12]){
                    rotate([90,0,0]){
                        cylinder(h=100, r=4, $fn=100, center=true);
                    }
                }
                translate([-15,0,57/2+12-36.6/2]){
                    rotate([90,0,0]){
                        cylinder(h=100, r=2.5, $fn=100, center=true);
                    }
                }
                translate([-15,0,57/2+12+36.6/2]){
                    rotate([90,0,0]){
                        cylinder(h=100, r=2.5, $fn=100, center=true);
                    }
                }


     


                translate([6,50,38]){
                    rotate([90,0,0]){
                        cylinder(h=100, r=1.2, $fn=100, center=false);
                    }
                }  


            }
        }
