

        difference(){
            union(){


                for (hoek =[360/(16*2):360/16:360])
                rotate([0,0,hoek]){
                    translate([32/2,-0.75,0]){
                        rotate([0,0,0]){
                            cylinder(h=2, r=0.8, $fn=20, center=false);
                        }
                    }

                    translate([34/2,-0.75,0]){
                        rotate([0,0,0]){
                    //      cylinder(h=2, r=0.7, $fn=10, center=false);
                        }
                    }
                }


                translate([0,0,0]){
                    rotate([0,0,0]){
                        cylinder(h=2, r=32.34/2, $fn=30, center=false);
                    }
                }  
                translate([0,0,0]){
                    rotate([0,0,0]){
                  //    cylinder(h=1, r=35/2, $fn=10, center=false);
                    }
                }  

                // houder
                translate([0,0,0]){
                    rotate([0,0,0]){
                        cylinder(h=14-5.5, r=9, $fn=100, center=false);
                    }
                }  
                
                // houder
                translate([-30,0,0]){
                    rotate([0,0,0]){
                        cylinder(h=5.5, r=9, $fn=100, center=false);
                    }
                }  
                


            }
            union() {

                for (hoek =[0:360/16:360])
                rotate([0,0,hoek]){
                    translate([32.34/2,-0.75,-1]){
                        rotate([0,0,0]){
                            cylinder(h=4, r=1.5875, $fn=20, center=false);
                        }
                    }
                }
                // extra grote hook
                for (hoek =[0:360/16:360])
                rotate([0,0,hoek]){
                    translate([32.34/2+0.1,-0.75,-1]){
                        rotate([0,0,0]){
                           cylinder(h=4, r=1.5875+0.1, $fn=20, center=false);
                        }
                    }
                }


                translate([0,0,-7]){
                    rotate([0,0,0]){
                        cylinder(h=20, r=4, $fn=30, center=false);
                    }
                }  
                translate([-30,0,-7]){
                    rotate([0,0,0]){
                        cylinder(h=20, r=4, $fn=30, center=false);
                    }
                }  

                translate([-10,0,5]){
                    rotate([0,90,0]){
                        cylinder(h=90, r=1.8, $fn=30, center=false);
                    }
                }  



            }
        }
