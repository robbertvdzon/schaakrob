translate([0,-7,-7]){


        difference(){
            union(){

                translate([-4.5,7,7]){
                    rotate([0,90,0]){
                        cylinder(h=9, r=4.0, $fn=100, center=false);
                    }
                }  


                translate([0,7,7]){
                    rotate([0,0,90]){
                        cylinder(h=40-7, r=4.5, $fn=100, center=false);
                    }
                }  



                translate([-3.5,1,3]){
                    cube([7,12,37], center=false);
                }

            }
            union() {



            }
        }
}