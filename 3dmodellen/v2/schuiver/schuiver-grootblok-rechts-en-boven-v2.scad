
difference(){
	union(){


        // omrand voor bout midden
        translate([0,-19,-0.5]){
            rotate([90,0,0]){
                cylinder(h=7, r=6, $fn=100, center=false);
            }
        }         

        translate([-9.5,-14,12]){
            cube([19,21.5,3], center=false);
        }         
        translate([-9.5,-14,-16]){
            cube([19,21.5,3], center=false);
        }         
        
        // 4 assen
        translate([-6.5,7.5,-14.5]){
            rotate([90,0,0]){
                cylinder(h=21.5, r=4, $fn=100, center=false);
            }
        }         
        translate([6.5,7.5,-14.5]){
            rotate([90,0,0]){
                cylinder(h=21.5, r=4, $fn=100, center=false);
            }
        }         
        translate([-6.5,7.5,13.5]){
            rotate([90,0,0]){
                cylinder(h=21.5, r=4, $fn=100, center=false);
            }
        }         
        translate([6.5,7.5,13.5]){
            rotate([90,0,0]){
                cylinder(h=21.5, r=4, $fn=100, center=false);
            }
        }         

        // gat voor stroomkabel
        translate([4.5,-11,5.5]){
            rotate([90,0,0]){
                cylinder(h=8, r=2, $fn=100, center=false);
            }
        }        



        translate([-7.5,-10-3,-0.5]){
            rotate([0,90,0]){
                cylinder(h=15, r=7.5, $fn=100, center=false);
            }
        }

        translate([0,-10,-0.5]){
            rotate([90,0,0]){
                cylinder(h=10, r=3, $fn=100, center=false);
            }
        }
      
        translate([0,0,-33/2-2]){
            rotate([0,0,90]){
                cylinder(h=53-17, r=7.5, $fn=100, center=false);
            }
        }
        

        


	}
	union() {
        // plek voor bout midden
        translate([0,0,-0.5]){
            rotate([90,0,0]){
                cylinder(h=100, r=5.7/2, $fn=100, center=false);
            }
        }         
        

        



        for (hoek =[0:45:360])
        rotate([0,0,hoek]){

            translate([-1,-5,-25]){
                rotate([0,0,0]){
                    cube([2,10,65], center=false);
                }
            }
        }
        translate([0,0,-33/2+2]){
            rotate([0,0,90]){
                cylinder(h=28, r=5, $fn=100, center=false);
            }
        }
        translate([0,0,-35]){
            rotate([0,0,90]){
                cylinder(h=100, r=4, $fn=100, center=false);
            }
        }

        translate([-60,-10-3,-0.5]){
            rotate([0,90,0]){
                cylinder(h=200, r=4, $fn=100, center=false);
            }
        }
        
        // gaten voor riemhouders
        translate([-6.5,8.5,-14.5]){
            rotate([90,0,0]){
                cylinder(h=125, r=4.7/2, $fn=100, center=false);
            }
        }         
        translate([6.5,8.5,-14.5]){
            rotate([90,0,0]){
                cylinder(h=125, r=4.7/2, $fn=100, center=false);
            }
        }         

        translate([-6.5,8.5,13.5]){
            rotate([90,0,0]){
                cylinder(h=125, r=4.7/2, $fn=100, center=false);
            }
        }         
        translate([6.5,8.5,13.5]){
            rotate([90,0,0]){
                cylinder(h=125, r=4.7/2, $fn=100, center=false);
            }
        }             
        // gat voor stroomkabel
        translate([4.5,-7,5.5]){
            rotate([90,0,0]){
                cylinder(h=125, r=1.5, $fn=100, center=false);
            }
        }             

        
        // onderin een stuk er af
        translate([-20,7.5-1.5,-30]){
            cube([100,10,100], center=false);
        }         



	}
}
